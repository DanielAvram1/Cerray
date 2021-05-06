package Delivery;

import Account.Customer;
import Account.Establishment;
import Order.Order;
import Account.Courier;
import db.DB;
import db.DBCSVService;
import db.DBEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Delivery extends DBEntity {

    Order order;
    Date pickedDate;
    Date deliveryDate;

    public Delivery(Order order) {
        super();
        this.order = order;
        this.pickedDate = null;
        this.deliveryDate = null;
        DBCSVService.getInstance().insert(this);
    }

    public Delivery(Order order, Date pickedDate, Date deliveryDate) {
        super();
        this.order = order;
        this.pickedDate = pickedDate;
        this.deliveryDate = deliveryDate;
        DBCSVService.getInstance().insert(this);

    }

    public Delivery(String id, Order order, Date pickedDate, Date deliveryDate) {
        this.id = id;
        this.order = order;
        this.pickedDate = pickedDate;
        this.deliveryDate = deliveryDate;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Override
    public String toString() {
        return "From: " + order.getEstablishment().getAddress() +
                "\tTo: " + order.getAddress() +
                "\tPicked: " + this.pickedDate +
                "\tDelivered: " + (deliveryDate == null ? "Not yet delivered" : deliveryDate);
    }

    @Override
    protected String toCSV() {
        return  id + ',' +
                order.getId() + ',' +
                pickedDate.toString() + ',' +
                deliveryDate.toString();
    }

    public static Delivery readFromCSV(String csv) {
        try {
            String[] data = csv.split(",");
            String id = data[0];
            Order order = DBCSVService.getInstance(3).getOrderList().stream()
                    .reduce(null, (pred, curr) -> {
                        if(curr.getId().equals(data[1]))
                            pred = curr;
                        return pred;
                    });
            Date pickedDate = (new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy")).parse(data[2]);
            Date deliveryDate = (new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy")).parse(data[3]);

            return new Delivery(id, order, pickedDate, deliveryDate);
        } catch(ParseException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
