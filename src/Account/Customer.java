package Account;

import Order.Order;

import java.util.ArrayList;
import java.util.List;

public class Customer extends Person {

    private static final int nrCustomers = 0;

    private List<Order> orders;

    public Customer(Account account, String firstName, String lastName) {
        super(account, firstName, lastName);
        this.orders = new ArrayList<Order>();
    }

    public Customer(String firstName, String lastName, String email, String phoneNumber, String password) {
        super(email, phoneNumber, password, firstName, lastName);
        this.orders = new ArrayList<Order>();
    }

    public Customer(String email, String phoneNumber, String password) {
        super(email, phoneNumber, password, "user", "user");
        this.orders = new ArrayList<Order>();
    }

    public List<Order> getOrders() {
        List<Order> orders = new ArrayList<Order>(this.orders);
        return orders;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
