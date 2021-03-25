package Order;

import MenuItem.MenuItem;

import java.util.List;

public class OrderService {
    private Order order;

    public OrderService(Order order) {
        this.order = order;
    }

    public void addMenuItem(MenuItem menuItem) {
        this.order.menuItemList.add(menuItem);
    }

    public void setDelivered() {
        this.order.delivered = !this.order.delivered;
    }

}
