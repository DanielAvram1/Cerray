package Account;

import Order.Order;

import java.util.ArrayList;
import java.util.List;

public class Customer extends Person {

    String defaultAddress;

    private static final int nrCustomers = 0;

    private List<Order> orderList;

    public Customer(Account account, String firstName, String lastName, String defaultAddress) {
        super(account, firstName, lastName);
        this.defaultAddress = defaultAddress;
        this.orderList = new ArrayList<Order>();
    }

    public Customer(String firstName, String lastName, String email, String phoneNumber, String password, String defaultAddress) {
        super(email, phoneNumber, password, firstName, lastName);
        this.defaultAddress = defaultAddress;
        this.orderList = new ArrayList<Order>();
    }

    public Customer(String email, String phoneNumber, String password) {
        super(email, phoneNumber, password, "user", "user");
        this.defaultAddress = "defaultAdress";
        this.orderList = new ArrayList<Order>();
    }

    public List<Order> getOrders() {
        List<Order> orders = new ArrayList<Order>(this.orderList);
        return orders;
    }

    public void addOrder(Order order) {
        this.orderList.add(order);
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
