package Account;

import Delivery.Delivery;
import Delivery.DeliveryService;
import Order.Order;
import Order.OrderService;
import db.DB;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
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

    private boolean displayMyDeliveries() {
        if (courier.deliveryList.size() == 0) {
            System.out.println("No deliveries to display.");
            return false;
        }
        for(int i = 0; i<  courier.deliveryList.size(); i++){
            System.out.println((i + 1) + ". " + courier.deliveryList.get(i));
        }
        return true;
    }

    private List<Delivery> displayMyUndeliveredDeliveries() {

        List<Delivery> undeliveredDeliveryList = new ArrayList<>();

        for(Delivery delivery: courier.deliveryList){
            if(delivery.getDeliveryDate() == null)
                undeliveredDeliveryList.add(delivery);
        }

        if (undeliveredDeliveryList.size() == 0) {
            System.out.println("No deliveries to display.");
            return null;
        }
        for(int i = 0; i<  undeliveredDeliveryList.size(); i++){
            System.out.println((i + 1) + ". " + undeliveredDeliveryList.get(i));
        }
        return undeliveredDeliveryList;
    }

    private void getAdditionalInformation() throws Exception{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        if(!displayMyDeliveries()){
            return;
        }

        String input;
        while(true) {
            System.out.println("Introduceti indexul livrarii pentru care doriti informatii aditionale, sau cancel");
            input = in.readLine();
            if(input.equals("cancel"))
                return;

            try {
                int idx = Integer.parseInt(input);
                if(idx < 1 || idx > courier.deliveryList.size())
                    throw new Exception();

                Order order = courier.deliveryList.get(idx - 1).getOrder();
                OrderService.getAdditionalInformation(order);

            } catch (NumberFormatException e) {
                System.out.println("Ati introdus un index gresit! Pentru a anula, tapati cancel");
            } catch(Exception e){
                System.out.println("Indexul este prea mare sau prea mic! Pentru a anula, tapati cancel");
            }
        }

    }

    private void confirmDelivery() throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        List<Delivery> undeliveredDeliveryList = displayMyUndeliveredDeliveries();

        String input;
        while(true){
            System.out.println("Introduceti numarul livrarii pe care doriti sa o confirmati, sau cancel");
            input = in.readLine();
            if(input.equals("cancel"))
                return;

            try {
              int idx = Integer.parseInt(input);
              if(idx < 1 || idx > undeliveredDeliveryList.size())
                  throw new Exception();
              Delivery delivery = undeliveredDeliveryList.get(idx - 1);
              delivery.setDeliveryDate(new Date());
              delivery.getOrder().setDelivered(true);
              System.out.println("Ati confirmat livrarea " + idx + "!");

              return;
            } catch(NumberFormatException e) {
                System.out.println("Ati introdus indexul gresit. Pentru a iesi, tapati cancel");
            } catch (Exception e) {
                System.out.println("Ati introdus un index prea mic sau prea mare. Pentru a iesi, tapati cancel");
            }
        }
    }

    public void session() throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Bun venit, Courier!  Ce doriti sa faceti mai departe?:");

        while(true) {
            System.out.println("display_orders\tmake_delivery\tdisplay_my_deliveries\tget_add_info\tconfirm_delivery\tback");
            String input = in.readLine();
            switch (input) {
                case "display_orders" -> {
                    OrderService.displayNotDeliveredOrders();
                }
                case "make_delivery" -> {
                    Delivery delivery = DeliveryService.makeDelivery(courier);
                    courier.deliveryList.add(delivery);
                }
                case "display_my_deliveries" -> {
                    displayMyDeliveries();
                }
                case "get_add_info" -> {
                    getAdditionalInformation();
                }
                case "confirm_delivery" -> {
                    confirmDelivery();
                }
                case "back" -> {
                    return;
                }
                default -> {
                    System.out.println("Ati introdus comanda gresit!");
                }
            }
        }
    }

}
