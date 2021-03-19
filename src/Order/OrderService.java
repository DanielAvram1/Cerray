package Order;

import MenuItem.MenuItem;

import java.util.List;

public class OrderService {
    private Order order;

    public OrderService(Order order) {
        this.order = order;
    }

    // NOTE: pare o metoda prea intortocheata pentru a adauga un element nou in lista
    // ar trebui sa fie un mod de accesare directa a unei clase componenta
    public void addMenuItem(MenuItem menuItem) {
        List<MenuItem> newMenuItemList = this.order.getMenuItemList();
        newMenuItemList.add(menuItem);
        order.setMenuItemList(newMenuItemList);
    }
}
