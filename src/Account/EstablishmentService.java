package Account;

import MenuItem.MenuItem;
import MenuItem.MenuItemService;
import Order.Order;
import db.DB;
import db.DBCSVService;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.management.MemoryNotificationInfo;
import java.util.*;
import java.util.List;

public class EstablishmentService {

    static MenuItem getMenuItemByName(Establishment establishment, String name) {
        MenuItem chosenMenuItem = null;
        for(MenuItem menuItem: establishment.menu.keySet()){
            if(menuItem.getName().equals(name)){
                chosenMenuItem = menuItem;
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

    static void displayMenu(Establishment establishment) {
        SortedMap<MenuItem, Integer> menu = establishment.menu;
        for(MenuItem menuItem: menu.keySet()) {
            System.out.printf("\n%s\t%f\t%x%n", menuItem.getName(), menuItem.getPrice(), menu.get(menuItem));
        }
    }

    public static Order makeOrder( Customer customer) throws Exception{
        Establishment establishment = chooseEstablishment();
        if(establishment == null)
            return null;
        displayMenu(establishment);

        SortedMap<MenuItem, Integer> menu = establishment.menu;
        SortedMap<MenuItem, Integer> newMenu = new TreeMap<>(menu);

        SortedMap<MenuItem, Integer> orderItems = new TreeMap<>();
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

                orderItems.put(chosenMenuItem, quan);

                establishment.addQuantity(chosenMenuItem, -quan);

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

        DBCSVService.getInstance().delete(establishment);
        DBCSVService.getInstance().insert(establishment);

        Order order = new Order(new Date(), address, establishment, orderItems);
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
            DBCSVService.getInstance().addLog(establishment.getId() + " registered as an Establishment");
            return establishment;
        }
        return null;
    }

}
