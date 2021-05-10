package MenuItem;

import db.DBService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuItemService {
    MenuItem menuItem;

    public static MenuItem getMenuItemById(String id) {
        String query = "SELECT * FROM MENU_ITEMS WHERE ID = " + id;
        ResultSet rs = DBService.getInstance().select(query);
        try {
            MenuItem menuItem = new MenuItem(rs);
            return menuItem;
        } catch (SQLException ignored) {
            return null;
        }
    }

}
