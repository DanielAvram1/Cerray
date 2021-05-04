package Delivery;

import Account.Courier;
import Order.Order;
import Order.OrderService;
import db.DB;
import db.DBCSVService;

import java.util.Date;

public class DeliveryService {

    Delivery delivery;

    static public Delivery makeDelivery() throws Exception{
        Order order = OrderService.chooseOrder();

        Delivery delivery = new Delivery(order, new Date(), null);

        DB.getInstance().deliveryList.add(delivery);

        DBCSVService.getInstance().insert(delivery);


        return delivery;
    }

}
