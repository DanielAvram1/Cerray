package Account;

import MenuItem.MenuItem;
import MenuItem.MenuItemService;
import Order.Order;
import db.DB;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EstablishmentService {

    private Establishment establishment;

    public EstablishmentService(Establishment establishment) {
        this.establishment = establishment;
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
        List<MenuItem> menu = establishment.menu;
        for(int i = 0; i < menu.size(); i++) {
            System.out.println(String.format("%x:\t%s\t%f\t%x", i + 1, menu.get(i).getName(), menu.get(i).getPrice(), menu.get(i).getQuantity()));
        }
    }

    public static Order makeOrder( Customer customer) throws Exception{
        Establishment establishment = chooseEstablishment();
        if(establishment == null)
            return null;
        displayMenu(establishment);

        List<MenuItem> menu = establishment.menu;
        List<MenuItem> newMenu = new ArrayList<MenuItem>(menu);

        List<MenuItem> orderItems = new ArrayList<>();
        double cost = 0;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        boolean cont = true;
        while(cont) {
            input = in.readLine();
            if(input.equals("done")) {
                cont = false;
                break;
            }
            String[] args = input.split(" ");
            try {
                int idx, quan;

                idx = Integer.parseInt(args[0]);
                if(idx < 1 || idx > menu.size()) throw new Exception("prea mic sau mare indexul!");

                quan = 1;

                if(args.length == 2) {
                    quan = Integer.parseInt(args[1]);
                    if(quan < 1 || quan > newMenu.get(idx - 1).getQuantity()) throw new Exception("prea mica sau mare cantitatea!");
                }

                cost += menu.get(idx - 1).getPrice() * quan;

                MenuItem orderItem = new MenuItem(menu.get(idx - 1).getName(), menu.get(idx - 1).getPrice(), quan);
                orderItems.add(orderItem);

                newMenu.get(idx - 1).addQuantity(-quan);

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
        Order order = new Order(new Date(), address, customer, establishment, orderItems);
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

            Establishment establishment = new Establishment(account, name, address, type, description, new ArrayList<MenuItem>());
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
                break;

            } catch (Exception e) {
                System.out.println("Nu ati introdus cantitatea corect!");
            }
        }

        MenuItem menuItem = new MenuItem(name, price, quan);

        this.establishment.addMenuItem(menuItem);
        System.out.println("Produsul " + name + " a fost introdus in meniul localului!");

    }

    public void deleteMenuItem() throws Exception{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        displayMenu(establishment);
        List<MenuItem> menu = establishment.menu;

        int idx = 0;

        while(true) {
            try{
                String input = in.readLine();
                idx = Integer.parseInt(input);
                if(idx < 1 || idx > menu.size()) {
                    System.out.println("Indexul e prea mic sau prea mare!");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Ati introdus indexul gresit!");
            }
        }

        menu.remove(idx - 1);


    }

    public void editMenu() throws Exception{

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Alegeti indexul produsului pe care doriti sa il modificati:");
        displayMenu(establishment);

        List<MenuItem> menu = establishment.menu;

        int idx = 0;

        while(true) {
            try{
                String input = in.readLine();
                idx = Integer.parseInt(input);
                if(idx < 1 || idx > menu.size()) {
                    System.out.println("Indexul e prea mic sau prea mare!");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Ati introdus indexul gresit!");
            }
        }
        MenuItemService.editMenuItem(menu.get(idx - 1));

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
