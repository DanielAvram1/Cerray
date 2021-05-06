package Order;

import Account.Courier;
import Account.Customer;
import Account.Establishment;
import Delivery.Delivery;
import MenuItem.MenuItem;
import db.DB;
import db.DBCSVService;
import db.DBEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Order extends DBEntity {

    String id;
    Date date;
    String address;

    SortedMap<MenuItem, Integer> menuItemList;
    Establishment establishment;
    boolean delivered;

    public Order(Date date, String address, Establishment establishment, SortedMap<MenuItem, Integer> menuItemList) {
        this.id = UUID.randomUUID().toString();
        this.date = date;
        this.menuItemList = menuItemList;
        this.address = address;
        this.establishment = establishment;
        this.delivered = false;
        DBCSVService.getInstance().insert(this);
    }
    public Order(String id, Date date, String address, Establishment establishment, SortedMap<MenuItem, Integer> menuItemList, Boolean delivered) {
        this.id = id;
        this.date = date;
        this.address = address;
        this.establishment = establishment;
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


    public String getAddress() {
        return address;
    }


    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public SortedMap<MenuItem, Integer> getMenuItemList() {
        return new TreeMap<>(menuItemList);
    }


    @Override
    public String toString() {
        return "From:" + establishment.getName() + " \tTo: " + address + "\tDate: " + date + "\t" + (delivered ? "Delivered" : "Not Delivered");
    }

    @Override
    protected String toCSV() {
        return  id + ',' +
                date.toString() + ',' +
                address + ',' +
                establishment.getId() + ',' +
                delivered;
    }

    static public Order readFromCSV(String csv) {
        try {
            String[] data = csv.split(",");
            String id = data[0];

            Date date = (new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy")).parse(data[1]);
            System.out.println(date);
            String address = data[2];
            Establishment establishment = (Establishment)DBCSVService.getInstance(2).getEstablishmentList().stream()
                    .reduce(null, (pred, curr) -> {
                        if(curr.getId().equals(data[3]))
                            pred = curr;
                        return pred;
                    });
            Boolean delivered = Boolean.parseBoolean(data[4]);

            SortedMap<MenuItem, Integer> menuItemList = DBCSVService.getInstance(1).readMenuFromCSV(id, "order_menuItem");

            return new Order(id, date, address, establishment, menuItemList, delivered);

        } catch(ParseException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

}
