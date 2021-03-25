package Order;

import MenuItem.MenuItem;

import java.util.List;

public class OrderService {
    private Order order;

    public OrderService(Order order) {
        this.order = order;
    }

    public void setDelivered() {
        this.order.delivered = !this.order.delivered;
    }

}
