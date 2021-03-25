package Delivery;

import Account.Customer;
import Account.Establishment;
import Order.Order;
import Account.Courier;

public class Delivery {
    Order order;
    Customer customer;
    Establishment establishment;
    Courier courier;


    public Delivery(Order order, Courier courier) {
        this.order = order;
        this.courier = courier;
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


    @Override
    public String toString() {
        return "Delivery{" +
                "order=" + order +
                ", customer=" + customer +
                ", establishment=" + establishment +
                ", courier=" + courier +
                '}';
    }
}
