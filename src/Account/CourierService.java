package Account;

import Order.Order;
import Order.OrderService;
import db.DB;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class CourierService {
    Courier courier;

    public CourierService(Courier courier) {
        this.courier = courier;
    }

    static public Courier register(Account account) throws Exception{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        List<Account> accountList = DB.getInstance().accountList;

        while(true) {


            System.out.print("Prenume: ");
            String firstName = in.readLine();
            System.out.print("Nume: ");
            String lastName = in.readLine();

            System.out.print("Transport: ");
            String meanOfTransport = in.readLine();

            System.out.println("Ati introdus toate informatiile necesare. Confirmati inregistrarea? Y/N");

            String input = in.readLine();
            if(input.equals("N")) break;

            Courier courier = new Courier(account, firstName, lastName, meanOfTransport);
            accountList.add(courier);

            System.out.println("Bun venit in Cerray! Sunteti un Courier inregistrat!");
            return courier;
        }
        return null;
    }

    public void session() throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Bun venit, Courier!  Ce doriti sa faceti mai departe?:");

        while(true) {
            System.out.println("display_orders\tchoose_order\tback");
            String input = in.readLine();
            switch (input) {
                case "display_orders": {
                    OrderService.displayNotDeliveredOrders();
                    break;
                }
                case "choose_order" : {
                    break;
                }
                case "back": {
                    return;
                }

                default: {
                    System.out.println("Ati introdus comanda gresit!");
                }
            }
        }
    }

}
