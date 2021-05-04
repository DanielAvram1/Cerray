package Order;

import Account.Courier;
import Account.Customer;
import Account.Establishment;
import Delivery.Delivery;
import MenuItem.MenuItem;
import db.DB;
import db.DBCSVService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Order {

    String id;
    Date date;
    String address;

    SortedMap<MenuItem, Integer> menuItemList;
    Establishment establishment;
    Customer receiver;
    boolean delivered;

    public Order(Date date, String address, Customer customer, Establishment establishment, SortedMap<MenuItem, Integer> menuItemList) {
        this.id = UUID.randomUUID().toString();
        this.date = date;
        this.menuItemList = menuItemList;
        this.address = address;
        this.receiver = customer;
        this.establishment = establishment;
        this.delivered = false;
        DBCSVService.getInstance().insert(this);
    }
    public Order(String id, Date date, String address, Establishment establishment, Customer receiver, SortedMap<MenuItem, Integer> menuItemList, Boolean delivered) {
        this.id = id;
        this.date = date;
        this.address = address;
        this.establishment = establishment;
        this.receiver = receiver;
        this.menuItemList = menuItemList;
        this.delivered = delivered;
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

    public String getId() {
        return this.id;
    }

    public SortedMap<MenuItem, Integer> getMenuItemList() {
        return new TreeMap<>(menuItemList);
    }


    @Override
    public String toString() {
        return "From:" + establishment.getName() + " \tTo: " + address + "\tDate: " + date + "\t" + (delivered ? "Delivered" : "Not Delivered");
    }

    public String toCSV() {
        return  id + ',' +
                date.toString() + ',' +
                address + ',' +
                establishment.getId() + ',' +
                receiver.getId() + ',' +
                delivered;
    }

    static public Order readFromCSV(String csv) {
        try {
            String[] data = csv.split(",");
            String id = data[0];

            Date date = (new SimpleDateFormat()).parse(data[1]);
            String address = data[2];
            Establishment establishment = (Establishment)(DB.getInstance().getAccountById(data[3]));
            Customer customer = (Customer)(DB.getInstance().getAccountById(data[4]));
            Boolean delivered = Boolean.parseBoolean(data[5]);

            SortedMap<MenuItem, Integer> menuItemList = DBCSVService.getInstance(1).readMenuFromCSV(id, "data/order_menuItem");

            return new Order(id, date, address, establishment, customer, menuItemList, delivered);

        } catch(ParseException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

}
