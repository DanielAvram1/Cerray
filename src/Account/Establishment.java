package Account;

import MenuItem.MenuItem;
import com.sun.source.tree.Tree;
import db.DBService;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class Establishment extends Account{
    String name;
    String address;
    String type;
    String description;
    SortedMap<String, Integer> menu;
    double income;

    public Establishment(Account account, String name, String address, String type, String description , SortedMap<String, Integer> menu) {
        super(account);
        this.name = name;
        this.address = address;
        this.type = type;
        this.description = description;
        this.menu = new TreeMap<>(menu);
        income = 0;
    }

    public Establishment(String email, String phoneNumber, String password, String name, String address, String type, String description, SortedMap<String, Integer> menu) {
        super(email, phoneNumber, password);
        this.name = name;
        this.address = address;
        this.type = type;
        this.description = description;
        this.menu = new TreeMap<>(menu);
        income = 0;
    }

    public Establishment(ResultSet rs) throws SQLException {
        super(  rs.getString("ID"), rs.getString("EMAIL"),
                rs.getString("PHONENUMBER"), rs.getString("PASSWORD"));
        this.name = rs.getString("NAME");
        this.description = rs.getString("DESCRITPION");
        this.address = rs.getString("ADDRESS");
        this.type = rs.getString("TYPE");
        this.income = rs.getDouble("INCOME");

        this.menu = new TreeMap<>();

        String query = "SELECT MENU_ITEM_ID, QUANTITY " +
                "FROM ESTABLISHMENT_ASOC_MENU_ITEM" +
                "WHERE ESTABLISHMENT_ID = " + this.getId();

        ResultSet menuItemRs = DBService.getInstance().select(query);

        while(menuItemRs.next()) {
            try {
                this.menu.put(menuItemRs.getString("MENU_ITEM_ID"), menuItemRs.getInt("QUANTITY"));
            } catch (SQLException ignored) {}
        }

    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public SortedMap<String, Integer> getMenu() {
        return menu;
    }

    public void setMenu(SortedMap<String, Integer> menu) {
        this.menu = menu;
    }

    public void addMenuItem(String menuItemId) {
        String query = null;
        if(menu.containsKey(menuItemId)){
            Integer quan = menu.get(menuItemId);
            menu.put(menuItemId, quan + 1);

            query = "UPDATE ESTABLISHMENT_ASOC_MENU_ITEM SET QUANTITY = " + quan.toString() +
                    "WHERE ESTABLISHMENT_ID = " + this.getId() +
                    "AND MENU_ITEM_ID = " + menuItemId;

        }
        else
            query = "INSERT INTO ESTABLISHMENT_ASOC_MENU_ITEM(ESTABLISHMENT_ID, MENU_ITEM_ID, QUANTITY)" +
                    "VALUES" +
                    "(" + this.getId() + ", " + menuItemId + ", " + 0 +")";
            DBService.getInstance().execute(query);
            menu.put(menuItemId, 0);
    }

    public void addQuantity(String menuItemId, Integer quantity){
        String query = null;
        if(menu.containsKey(menuItemId)){
            Integer quan = menu.get(menuItemId);
            menu.put(menuItemId, quan + 1);

            query = "UPDATE ESTABLISHMENT_ASOC_MENU_ITEM SET QUANTITY = " + quan.toString() +
                    "WHERE ESTABLISHMENT_ID = " + this.getId() +
                    "AND MENU_ITEM_ID = " + menuItemId;
        }
        else
            query = "INSERT INTO ESTABLISHMENT_ASOC_MENU_ITEM(ESTABLISHMENT_ID, MENU_ITEM_ID, QUANTITY)" +
                    "VALUES" +
                    "(" + this.getId() + ", " + menuItemId + ", " + quantity +")";
        DBService.getInstance().execute(query);
        menu.put(menuItemId, quantity);
    }


    @Override
    public String toString() {
        return "Establishment{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
