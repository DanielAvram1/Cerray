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

        String query = String.format("INSERT INTO ESTABLISHMENTS" +
                        " VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s','%s', %f)",
                this.getId(), this.email, this.phoneNumber, this.password, this.name,
                this.address, this.type, this.description, this.income);
        DBService.getInstance().execute(query);

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
        this.description = rs.getString("DESCRIPTION");
        this.address = rs.getString("ADDRESS");
        this.type = rs.getString("TYPE");
        this.income = rs.getDouble("INCOME");

        this.menu = new TreeMap<>();

        String query = "SELECT * " +
                "FROM ESTABLISHMENT_ASOC_MENU_ITEM " +
                "WHERE ESTABLISHMENT_ID = ?";

        ResultSet menuItemRs = DBService.getInstance().select(query, this.getId());

        while(menuItemRs.next()) {
            try {
                this.menu.put(menuItemRs.getString("MENU_ITEM_ID"), menuItemRs.getInt("QUANTITY"));
            } catch (SQLException ignored) {}
        }

    }

    public void addIncome(double toAdd) {
        income += toAdd;
        String query = "UPDATE ESTABLISHMENTS SET INCOME = INCOME + ? WHERE ID = ?";
        DBService.getInstance().execute(query, toAdd, getId());
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
        String query;
        Integer quan = 0;
        if(menu.containsKey(menuItemId)){
            quan = menu.get(menuItemId);
            menu.put(menuItemId, quan + 1);
            quan++;
            query = "UPDATE ESTABLISHMENT_ASOC_MENU_ITEM SET QUANTITY = ? " +
                    "WHERE ESTABLISHMENT_ID = ? " +
                    "AND MENU_ITEM_ID = ?";

        }
        else
            query = "INSERT INTO ESTABLISHMENT_ASOC_MENU_ITEM(QUANTITY, ESTABLISHMENT_ID, MENU_ITEM_ID)" +
                    "VALUES" +
                    "(?, ?, ?)";
            DBService.getInstance().execute(query, quan, getId(), menuItemId);
            menu.put(menuItemId, quan);
    }

    public void addQuantity(String menuItemId, Integer quantity){
        String query = null;
        if(menu.containsKey(menuItemId)){
            Integer quan = menu.get(menuItemId);
            menu.put(menuItemId, quan + quantity);
            quantity += quan;
            query = "UPDATE ESTABLISHMENT_ASOC_MENU_ITEM SET QUANTITY = QUANTITY + ? " +
                    "WHERE ESTABLISHMENT_ID = ? " +
                    "AND MENU_ITEM_ID = ?";
        }
        else
            query = "INSERT INTO ESTABLISHMENT_ASOC_MENU_ITEM(QUANTITY, ESTABLISHMENT_ID, MENU_ITEM_ID)" +
                    "VALUES" +
                    "(?, ?, ?)";
        DBService.getInstance().execute(query, quantity, this.getId(), menuItemId);
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
