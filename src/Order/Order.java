package Order;

import Account.Customer;
import MenuItem.MenuItem;

import java.util.Date;
import java.util.List;

public class Order {
    Date date;
    String address;
    List<MenuItem> menuItemList;
    Customer receiver;
    boolean delivered;

    public Order(Date date, String address, Customer customer, List<MenuItem> menuItemList) {
        this.date = date;
        this.menuItemList = menuItemList;
        this.address = address;
        this.receiver = customer;
        this.delivered = false;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "Address:" + address + "\tDate: " + date + "\t" + (delivered ? "Delivered" : "Not Delivered");
    }
}
