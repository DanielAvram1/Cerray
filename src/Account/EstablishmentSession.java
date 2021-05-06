package Account;

import MenuItem.MenuItem;
import db.DBCSVService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.SortedMap;

public class EstablishmentSession {

    private Establishment establishment;
    public EstablishmentSession(Establishment establishment) {
        this.establishment = establishment;
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

        establishment.addMenuItem(menuItem);
        establishment.addQuantity(menuItem, quan);
        System.out.println("Produsul " + name + " a fost introdus in meniul localului!");

    }

    public void deleteMenuItem() throws Exception{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        EstablishmentService.displayMenu(establishment);
        SortedMap<MenuItem, Integer> menu = establishment.menu;
        System.out.println("Alegeti numele produsului pe care doriti sa il stergeti:");

        String name;
        MenuItem chosenMenuItem = null;

        while(true) {
            try{
                name = in.readLine();
                chosenMenuItem = EstablishmentService.getMenuItemByName(establishment, name);
                if(chosenMenuItem == null)
                    throw new Exception();
                break;
            } catch (Exception e) {
                System.out.println("Ati introdus numele gresit!");
            }
        }

        menu.remove(chosenMenuItem);
        DBCSVService.getInstance().deleteById("data/establishment_menuItem.csv", establishment.getId(), 0);
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
                        establishment.menu.put(menuItem, quan);
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
        DBCSVService.getInstance().delete(menuItem);
        DBCSVService.getInstance().insert(menuItem);
    }

    public void editMenu() throws Exception{

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Alegeti numele produsului pe care doriti sa il modificati:");
        EstablishmentService.displayMenu(establishment);

        SortedMap<MenuItem, Integer> menu = establishment.menu;
        String name;
        MenuItem chosenMenuItem = null;
        while(true) {
            try{
                name = in.readLine();

                chosenMenuItem = EstablishmentService.getMenuItemByName(establishment, name);

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

                    EstablishmentService.displayMenu(establishment);

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
