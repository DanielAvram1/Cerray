package Order;

import MenuItem.MenuItem;

import java.util.Date;
import java.util.List;

public class Order {
    Date date;
    List<MenuItem> menuItemList;
    boolean delivered;

    public Order(Date date, List<MenuItem> menuItemList) {
        this.date = date;
        this.menuItemList = menuItemList;
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
                ", menuItemList=" + menuItemList +
                '}';
    }
}
