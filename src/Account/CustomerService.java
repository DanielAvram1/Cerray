package Account;

import Order.Order;
import db.DB;
import db.DBCSVService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class CustomerService {
    private Customer customer;

    public CustomerService(Customer customer) {
        this.customer = customer;
    }


    public static Customer register(Account account) throws Exception{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        List<Account> accountList = DB.getInstance().accountList;

        while(true) {


            System.out.print("Prenume: ");
            String firstName = in.readLine();
            System.out.print("Nume: ");
            String lastName = in.readLine();

            System.out.print("Adresa: ");
            String defaultAddress = in.readLine();

            System.out.println("Ati introdus toate informatiile necesare. Confirmati inregistrarea? Y/N");

            String input = in.readLine();
            if(input.equals("N")) break;

            Customer customer = new Customer(account, firstName, lastName, defaultAddress);
            accountList.add(customer);

            System.out.println("Bun venit in Cerray! Sunteti un Customer inregistrat!");
            DBCSVService.getInstance().addLog(customer.getId() + " registered as a Customer");
            return customer;
        }
        return null;
    }

    private void displayOrders() {
        List<Order> orderList = customer.getOrders();
        if(orderList.size() == 0) {
            System.out.println("There is nothing to display");
            return;
        }
        for(int i = 0;i < orderList.size(); i++) {
            System.out.println((i + 1) + ". " + orderList.get(i));
        }
    }

    public void session() throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Bun venit, Customer!  Ce doriti sa faceti mai departe?:");

        boolean contCustomerSession = true;

        while(contCustomerSession) {
            System.out.println("order\tdisplay_orders\tback");
            String input = in.readLine();
            switch (input) {
                case "order" -> {

                    Order order = EstablishmentService.makeOrder(this.customer);

                    if (order != null) {
                        this.customer.addOrder(order);
                        System.out.println("Ati facut comanda!");
                    }

                }
                case "display_orders" -> {
                    displayOrders();
                }
                case "back" -> {
                    contCustomerSession = false;
                }
                default -> {
                    System.out.println("Ati introdus comanda gresit!");
                }
            }
        }

    }
}
