package Service;

import Order.Order;
import Account.Customer;

import java.util.List;
public class CustomerService implements Service{

    private Customer customer;

    public CustomerService(Customer customer) {
        this.customer = customer;
    }



    public void addOrder(Order order) {
        this.customer.addOrder(order);
    }

    public void displayOrders(int nr) {
        List<Order> orders = this.customer.getOrders();
        for(Order order : orders) {
            System.out.println(order);
        }
    }

    public void changePassword(String newPassword) {
        this.customer.setPassword(newPassword);
    }

    public void changeFirstName(String newFirstName) {
        this.customer.setFirstName(newFirstName);
    }

}
