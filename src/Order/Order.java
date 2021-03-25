package Order;

import Account.Establishment;
import MenuItem.MenuItem;
import MenuItem.MenuItemQuantity;
import Account.Customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    Date date;
    List<MenuItemQuantity> menuItemQuantityList;
    boolean delivered;

    public Order(Date date, List<MenuItemQuantity> menuItemQuantityList) {
        this.date = date;
        this.menuItemQuantityList = menuItemQuantityList;
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
        return "Order{" +
                ", date=" + date +
                ", menuItemList=" + menuItemQuantityList +
                '}';
    }
}
