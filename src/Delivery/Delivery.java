package Delivery;

import Account.Customer;
import Account.Establishment;
import Order.Order;
import Account.Courier;

import java.util.Date;

public class Delivery {
    Order order;
    Courier courier;
    Date pickedDate;
    Date deliveryDate;

    public Delivery(Order order) {
        this.order = order;
        this.courier = null;
        this.pickedDate = null;
        this.deliveryDate = null;
    }

    public Delivery(Order order, Courier courier, Date pickedDate, Date deliveryDate) {
        this.order = order;
        this.courier = courier;
        this.pickedDate = pickedDate;
        this.deliveryDate = deliveryDate;

    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
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
}
