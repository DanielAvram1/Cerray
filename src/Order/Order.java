package Order;

import Account.Establishment;
import MenuItem.MenuItem;
import Account.Customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    Customer customer;
    Date date;
    List<MenuItem> menuItemList;
    Establishment establishment;
    boolean delivered;

    public Order(Customer customer, Date date, List<MenuItem> menuItemList, Establishment establishment) {
        this.customer = customer;
        this.date = date;
        this.menuItemList = menuItemList;
        this.establishment = establishment;
        this.delivered = false;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<MenuItem> getMenuItemList() {
        return new ArrayList<MenuItem>(this.menuItemList);
    }

    public void setMenuItemList(List<MenuItem> menuItemList) {
        this.menuItemList = menuItemList;
    }

    public Establishment getEstablishment() {
        return establishment;
    }

    public void setEstablishment(Establishment establishment) {
        this.establishment = establishment;
    }

    @Override
    public String toString() {
        return "Order{" +
                "customer=" + customer +
                ", date=" + date +
                ", menuItemList=" + menuItemList +
                ", establishment=" + establishment +
                '}';
    }
}
