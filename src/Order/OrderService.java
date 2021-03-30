package Order;

import MenuItem.MenuItem;
import db.DB;

import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private Order order;

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

    public void setDelivered() {
        this.order.delivered = !this.order.delivered;
    }

}
