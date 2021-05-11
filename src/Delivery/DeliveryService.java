package Delivery;

import Account.Courier;
import Order.Order;
import Order.OrderService;
import db.DB;

import java.util.Date;

public class DeliveryService {

    Delivery delivery;

    static public Delivery makeDelivery(Courier courier) throws Exception{
        Order order = OrderService.chooseOrder();
        Delivery delivery = new Delivery(order.getId(), courier.getId(), new Date(), null);
        return delivery;
    }

}
