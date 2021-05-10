package Order;

import Account.Courier;
import Delivery.DeliveryService;
import Delivery.Delivery;
import MenuItem.MenuItem;
import db.DB;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderService {
    private Order order;


    public static void getAdditionalInformation(Order order) {
        String out = order.getEstablishment().toString();
        System.out.println(out);
    }

    public static List<Order> displayNotDeliveredOrders() {
        List<Order> orderList = DB.getInstance().orderList;
        List<Order> notDeliveredOrderList = new ArrayList<>();

        for(Order order: orderList) {
            if(!order.delivered) {
                notDeliveredOrderList.add(order);
            }
        }

        for(int i = 0; i < notDeliveredOrderList.size(); i++) {
            Order order = notDeliveredOrderList.get(i);
            System.out.println((i + 1) + ". " + order);
        }
        return notDeliveredOrderList;
    }

    public static Order chooseOrder() throws Exception{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        List<Order> notDeliveredOrders = displayNotDeliveredOrders();

        String input;
        int idx;
        System.out.println("Introduceti numarul comenzii pe care doriti sa o livrati, sau cancel:");
        while(true) {
            input = in.readLine();
            if(input.equals("cancel")) {
                return null;
            }

            try {
                idx = Integer.parseInt(input);
                if(idx < 1 || idx > notDeliveredOrders.size())
                    throw new Exception();
                break;
            } catch (NumberFormatException e) {
                System.out.println("Ati introdus un index gresit! Pentru a anula, tapati cancel");
            } catch(Exception e){
                System.out.println("Indexul este prea mare sau prea mic! Pentru a anula, tapati cancel");
            }

        }


        System.out.println("Ati ales comanda " + idx + "! Toate informatiile ce tin de livrare pot fi accesate in lista voastra de livrari.");
        return notDeliveredOrders.get(idx - 1);

    }

    public void setDelivered() {
        this.order.delivered = !this.order.delivered;
    }

}
