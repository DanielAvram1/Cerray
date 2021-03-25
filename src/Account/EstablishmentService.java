package Account;

import MenuItem.MenuItem;
import MenuItem.MenuItemQuantity;
import Order.Order;
import db.DB;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EstablishmentService {

    private Establishment establishment;

    public void chooseEstablishment() throws Exception{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        List<Establishment> establishmentList = DB.getInstance().establishmentList;

        System.out.println("Alegeti indexul localului din care doriti sa faceti comanda:");

        DB.getInstance().displayEstablishments();

        int idx = Integer.parseInt(in.readLine());

        if(idx > establishmentList.size())
            throw new Exception("Index prea mare!");

        establishment = establishmentList.get(idx - 1);

        System.out.println(establishment.getName());

    }

    public void displayMenu() {
        List<MenuItem> menu = establishment.menu;
        for(int i = 0; i < menu.size(); i++) {
            System.out.println(String.format("%x:\t%s", i + 1, menu.get(i).getName()));
        }
    }

    public Order makeOrder() throws Exception{
        displayMenu();

        List<MenuItem> menu = establishment.menu;

        List<MenuItemQuantity> orderItems = new ArrayList<>();

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
                    idx = Integer.parseInt(args[0]);
                    quan = Integer.parseInt(args[1]);
                    if(quan < 1 || quan > 5000) throw new Exception("prea mic sau mare cantitatea!");
                }

                MenuItemQuantity orderItem = new MenuItemQuantity(menu.get(idx - 1), quan);
                orderItems.add(orderItem);


            } catch(Exception e) {
                System.out.println(e.getMessage() + "; pentru a termina, scrieti done");
            }
        }

        return new Order(new Date(), orderItems);

    }

}
