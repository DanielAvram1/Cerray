package MenuItem;

public class MenuItemQuantity {
    private MenuItem menuItem;
    private int quantity;

    public MenuItemQuantity(MenuItem menuItem, int quantity) {
        this.menuItem = menuItem;
        this.quantity = quantity;
    }


    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "MenuItemQuantity{" +
                "menuItem=" + menuItem +
                ", quantity=" + quantity +
                '}';
    }
}
