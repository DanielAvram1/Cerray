package Service;

import MenuItem.MenuItem;

public class MenuItemService {
    private MenuItem menuItem;

    public void changePrice(int newPrice) {
        menuItem.setPrice(newPrice);
    }

}
