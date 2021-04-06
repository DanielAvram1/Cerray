package Order;

import Account.Customer;
import Account.Establishment;
import MenuItem.MenuItem;

import java.util.Date;
import java.util.List;

public class Order {
    Date date;
    String address;
    List<MenuItem> menuItemList;
    Establishment establishment;
    Customer receiver;
    boolean delivered;

    public Order(Date date, String address, Customer customer, Establishment establishment, List<MenuItem> menuItemList) {
        this.date = date;
        this.menuItemList = menuItemList;
        this.address = address;
        this.receiver = customer;
        this.establishment = establishment;
        this.delivered = false;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Establishment getEstablishment() {
        return establishment;
    }

    public Customer getReceiver() {
        return receiver;
    }

    public String getAddress() {
        return address;
    }


    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }


    @Override
    public String toString() {
        return "From:" + establishment.getName() + " \tTo: " + address + "\tDate: " + date + "\t" + (delivered ? "Delivered" : "Not Delivered");
    }
}
