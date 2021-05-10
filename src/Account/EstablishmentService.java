package Account;

import MenuItem.MenuItem;
import MenuItem.MenuItemService;
import Order.Order;
import db.DB;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.management.MemoryNotificationInfo;
import java.util.*;
import java.util.List;

public class EstablishmentService {

    private Establishment establishment;

    public EstablishmentService(Establishment establishment) {
        this.establishment = establishment;
    }

    private static MenuItem getMenuItemByName(Establishment establishment, String name) {
        MenuItem chosenMenuItem = null;
        for(String menuItemId: establishment.menu.keySet()){
            MenuItem currMenuItem = MenuItemService.getMenuItemById(menuItemId);
            if(currMenuItem != null && currMenuItem.getName().equals(name)){
                chosenMenuItem = currMenuItem;
                break;
            }
        }
        return chosenMenuItem;
    }

    private static Establishment chooseEstablishment() throws Exception{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        List<Establishment> establishmentList = DB.getInstance().getEstablishmentList();

        System.out.println("Alegeti indexul localului din care doriti sa faceti comanda, sau comanda back:");

        DB.getInstance().displayEstablishments();

        boolean contChooseEstablishmentSession = true;

        while(contChooseEstablishmentSession) {
            String input = in.readLine();
            if(input.equals("back"))
                contChooseEstablishmentSession = false;
            else {
                int idx = 0;
                try {
                    idx = Integer.parseInt(input);
                } catch (Exception e){
                    System.out.println("Nu ati introdus indexul sau comanda corect!");
                    continue;
                }

                if(idx > establishmentList.size()){
                    System.out.println("Index prea mare!");
                    continue;
                }

                Establishment establishment = establishmentList.get(idx - 1);

                System.out.println(establishment.getName());
                return establishment;
            }
        }
        return null;

    }

    private static void displayMenu(Establishment establishment) {
        SortedMap<String, Integer> menu = establishment.menu;
        for(String menuItemId: menu.keySet()) {
            MenuItem currMenuItem = MenuItemService.getMenuItemById(menuItemId);
            if(currMenuItem == null)
                continue;
            System.out.printf("%s\t%f\t%x%n", currMenuItem.getName(), currMenuItem.getPrice(), menu.get(currMenuItem));
        }
    }

    public static Order makeOrder( Customer customer) throws Exception{
        Establishment establishment = chooseEstablishment();
        if(establishment == null)
            return null;
        displayMenu(establishment);

        SortedMap<String, Integer> menu = establishment.menu;
        SortedMap<String, Integer> newMenu = new TreeMap<>(menu);

        SortedMap<String, Integer> orderItems = new TreeMap<>();
        double cost = 0;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        while(true) {
            input = in.readLine();
            if(input.equals("done")) {
                break;
            }
            String[] args = input.split(" ");
            try {
                String name = args[0];
                int  quan;
                MenuItem chosenMenuItem = getMenuItemByName(establishment, name);

                if(chosenMenuItem == null) throw new Exception("Nu ati introdus un nume de produs corect!");

                quan = 1;

                if(args.length == 2) {
                    quan = Integer.parseInt(args[1]);
                    if(quan < 1 || quan > newMenu.get(chosenMenuItem)) throw new Exception("prea mica sau mare cantitatea!");
                }

                cost += chosenMenuItem.getPrice() * quan;

                orderItems.put(chosenMenuItem.getId(), quan);

                establishment.addQuantity(chosenMenuItem.getId(), -quan);

            } catch(Exception e) {
                System.out.println( "Ati introdus numerele gresit; pentru a termina, scrieti done");
            }
        }

        String address = customer.defaultAddress;

        System.out.println("Doriti sa primiti comanda la " + address + " ? Y/N");
        input = in.readLine();

        if(input.equals("N")){
            System.out.print("Adresa livrarii: ");
            address = in.readLine();
        }

        System.out.println("Costul comenzii: " + cost);
        System.out.println("Doriti sa confirmati comanda? Y/N");
        input = in.readLine();
        if(input.equals("N"))
            return null;
        establishment.menu = newMenu;
        establishment.income += cost;
        Order order = new Order(new Date(), address, customer.getId(), establishment.getId(), orderItems);
        DB.getInstance().orderList.add(order);

        return order;

    }



    public static Establishment register(Account account) throws Exception{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        List<Establishment> loginEstablishmentList = DB.getInstance().getEstablishmentList();

        while(true) {


            System.out.print("Denumire: ");
            String name = in.readLine();

            System.out.print("Adresa: ");
            String address = in.readLine();

            System.out.println("Tipul localului: ");
            String type = in.readLine();

            System.out.println("Descriere: ");
            String description = in.readLine();

            System.out.println("Ati introdus toate informatiile necesare. Confirmati inregistrarea? Y/N");

            String input = in.readLine();
            if(input.equals("N")) break;

            Establishment establishment = new Establishment(account, name, address, type, description, new TreeMap<>());
            loginEstablishmentList.add(establishment);

            System.out.println("Bun venit in Cerray! Sunteti un Establishment inregistrat!");
            return establishment;
        }
        return null;
    }

    public void addMenuItem() throws Exception{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Introduceti un produs nou: ");

        System.out.println("Denumire: ");
        String name = in.readLine();

        double price = 0;
        while(true) {
            System.out.println("Pretul: ");
            String input = in.readLine();
            try {
                price = Double.parseDouble(input);
                break;

            } catch (Exception e) {
                System.out.println("Nu ati introdus pretul corect!");
            }
        }

        int quan = 0;
        while(true) {
            System.out.println("Cantitatea: ");
            String input = in.readLine();
            try {
                quan = Integer.parseInt(input);
                if(quan < 0)
                    throw new Exception();
                break;

            } catch (Exception e) {
                System.out.println("Nu ati introdus cantitatea corect!");
            }
        }

        MenuItem menuItem = new MenuItem(name, price);

        establishment.addMenuItem(menuItem.getId());
        establishment.addQuantity(menuItem.getId(), quan);
        System.out.println("Produsul " + name + " a fost introdus in meniul localului!");

    }

    public void deleteMenuItem() throws Exception{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        displayMenu(establishment);
        SortedMap<String, Integer> menu = establishment.menu;
        System.out.println("Alegeti numele produsului pe care doriti sa il stergeti:");

        String name;
        MenuItem chosenMenuItem = null;

        while(true) {
            try{
                name = in.readLine();
                chosenMenuItem = getMenuItemByName(establishment, name);
                if(chosenMenuItem == null)
                    throw new Exception();
                break;
            } catch (Exception e) {
                System.out.println("Ati introdus numele gresit!");
            }
        }

        menu.remove(chosenMenuItem);
        System.out.printf("Produsul %s a fost sters din meniu!%n", name);


    }

    private void editMenuItem(MenuItem menuItem) throws Exception{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Introduceti campul pe care doriti sa-l modificati:");

        boolean cont = true;
        while(cont) {
            System.out.println("name\tprice\tquantity\tcancel");
            String input = in.readLine();
            switch (input) {
                case "name" -> {
                    System.out.print("New name: ");
                    String name = in.readLine();
                    menuItem.setName(name);
                    cont = false;
                }
                case "price" -> {
                    System.out.print("New Price: ");
                    try {
                        String priceInput = in.readLine();
                        double price = Double.parseDouble(priceInput);
                        if (price < 0) {
                            System.out.println("Pretul nu poate fi negativ!");
                            break;
                        }
                        menuItem.setPrice(price);
                        cont = false;

                    } catch (Exception e) {
                        System.out.println("Ati introdus un pret gresit!");
                    }
                }
                case "quantity" -> {
                    System.out.print("New Quantity: ");
                    try {
                        String quanInput = in.readLine();
                        int quan = Integer.parseInt(quanInput);
                        if (quan < 0) {
                            System.out.println("Cantitatea nu poate fi negativa!");
                            break;
                        }
                        establishment.menu.put(menuItem.getId(), quan);
                        cont = false;

                    } catch (Exception e) {
                        System.out.println("Ati introdus o cantitate gresita!");
                    }
                }
                case "cancel" -> {
                    return;
                }
                default -> {
                    System.out.println("Ati introdus un camp gresit. Pentru a iesi, tastati cancel");
                }
            }
        }
    }

    public void editMenu() throws Exception{

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Alegeti numele produsului pe care doriti sa il modificati:");
        displayMenu(establishment);

        SortedMap<String , Integer> menu = establishment.menu;
        String name;
        MenuItem chosenMenuItem = null;
        while(true) {
            try{
                name = in.readLine();

                chosenMenuItem = getMenuItemByName(establishment, name);

                if(chosenMenuItem == null)
                    throw new Exception("Nu ati introdus un nume existent!");

                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        editMenuItem(chosenMenuItem);
    }


    public void session() throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Bun venit, Establishment! Ce doriti sa faceti mai departe?:");
        boolean contSession = true;

        while(contSession) {
            System.out.println("display_menu\tadd_item\tdelete_item\tedit_menu\tincome\tback");
            String input = in.readLine();
            switch (input) {
                case "display_menu" : {

                    displayMenu(establishment);

                    break;
                }
                case "add_item" : {

                    addMenuItem();
                    break;
                }
                case "delete_item" : {

                    deleteMenuItem();
                    break;
                }
                case "edit_menu" : {

                    editMenu();
                    break;
                }
                case "income" :{
                    System.out.println("Current Income: " + establishment.income);
                    break;
                }
                case "back" :{
                    return;
                }

                default: {
                    System.out.println("Ati introdus comanda gresit!");
                }
            }
        }
    }
}
