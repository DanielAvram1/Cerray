package db;

import Account.Account;
import Account.Courier;
import Account.Customer;
import Account.Establishment;
import Delivery.Delivery;
import Order.Order;
import MenuItem.MenuItem;
import com.sun.source.tree.Tree;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static java.lang.Math.min;

public class DBCSVService {

    private static DBCSVService singleInstance = null;
    private static DBCSVService secondSingleInstance = null;
    private static DBCSVService firstSingleInstance = null;
    private static DBCSVService thirdSingleInstance = null;
    private static DBCSVService fourthSingleInstance = null;

    private static final DBCSVService[] instances = {null, null, null, null, null};

    private List<String> fileList;



    List<Account> accountList;

    public List<Order> getOrderList() {
        return orderList;
    }

    List<Order> orderList;
    List<Delivery> deliveryList;
    List<MenuItem> menuItemList;

    public List<Account> getEstablishmentList() {
        return establishmentList;
    }

    List<Account> establishmentList;


    public static DBCSVService getInstance() {

        if (instances[instances.length - 1] == null)
            instances[instances.length - 1] = new DBCSVService(5);
        return instances[instances.length - 1];
    }

    public static DBCSVService getInstance(int nrInstance) {

        int i = 0;
        while(i < min(nrInstance, instances.length)){
            if(instances[i] == null){
                instances[i] = new DBCSVService(i + 1);
            }
            i++;
        }
        return instances[i - 1];
    }

    private DBCSVService(int stage) {
        if(stage == 1) {
            fileList = Arrays.asList(   "customers", "couriers", "establishments", "menuItems", "orders", "deliveries",
                    "establishment_menuItem", "order_menuItem", "courier_delivery", "customer_order", "logger");

            initiateFiles();

            menuItemList = readAllMenuItemsFromCSV();

        }
        else if(stage == 2) {
            menuItemList = instances[0].menuItemList;

            establishmentList = readAllEstablishmentsFromCSV();
        }
        else if(stage == 3) {

            menuItemList = instances[1].menuItemList;
            establishmentList = instances[1].establishmentList;
            orderList = readAllOrdersFromCSV();

        }
        else if(stage == 4){
            menuItemList = instances[2].menuItemList;
            establishmentList = instances[2].establishmentList;
            orderList = instances[2].orderList;

            deliveryList = readAllDeliveriesFromCSV();
        }
        else {
            menuItemList = instances[3].menuItemList;
            orderList = instances[3].orderList;
            establishmentList = instances[3].establishmentList;
            deliveryList = instances[3].deliveryList;

            List<Account> customerList = readAllCustomersFromCSV();
            List<Account> courierList = readAllCouriersFromCSV();
            accountList = new ArrayList<>();
            accountList.addAll(establishmentList);
            accountList.addAll(courierList);
            accountList.addAll(customerList);
        }

    }

    private void initiateFile(String title) throws IOException {
        File fileCSV = new File(String.format("data/%s.csv", title));
        fileCSV.createNewFile();
    }

    private void initiateFiles() {
        try {
            for(String file: fileList) {
                initiateFile(file);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());;
        }
    }

    private void addLine(String pathname, String data) {
        try {
            FileWriter fw = new FileWriter(pathname, true);
            fw.write(data + '\n');
            fw.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insert(DBEntity o) {
        try {

            String record = o.toCSV();

            if(o instanceof Customer) {

                addLine("data/customers.csv", record);
                addLog("Customer " + o.getId() + " was added");

                return;
            }

            if(o instanceof Courier) {
                addLine("data/couriers.csv", record);
                addLog("Courier " + o.getId() + " was added");
                return;
            }

            if(o instanceof Establishment) {

                addLine("data/establishments.csv", record);
                SortedMap<MenuItem, Integer> menu = ((Establishment)o).getMenu();
                for(MenuItem menuItem: menu.keySet()) {
                    addLine("data/establishment_menuItem.csv",
                            o.getId() + ',' + menuItem.getId() + ',' + menu.get(menuItem) + '\n');
                }

                addLog("Establishment " + o.getId() + " was added");

                return;
            }

            if(o instanceof MenuItem) {

                addLine("data/menuItems.csv", record);
                addLog("MenuItem " + o.getId() + " was added");
                return;

            }

            if(o instanceof Order) {

                addLine("data/orders.csv",record);

                SortedMap<MenuItem, Integer> menuItemList = ((Order)o).getMenuItemList();
                for(MenuItem menuItem: menuItemList.keySet()) {
                    addLine("data/order_menuItem.csv",
                            o.getId() + ',' + menuItem.getId() + ',' + menuItemList.get(menuItem) + '\n');
                }
                addLog("Order " + o.getId() + " was added");
                return;

            }

            if(o instanceof Delivery) {

                addLine("data/deliveries.csv", record);
                addLog("Delivery " + o.getId() + " was added");
                return;

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteById(String pathname, String id, int pos) throws IOException{
        File old = new File(pathname);
        Scanner sc = new Scanner(old);
        File temp = new File("data/temp.csv");

        temp.createNewFile();

        while(sc.hasNextLine()) {
            String record = sc.nextLine();
            String[] data = record.split(",");
            if(data[pos].equals(id)) {
                continue;
            }
            addLine("data/temp.csv",record + '\n');
        }
        sc.close();

        old.delete();
        temp.renameTo(old);
    }

    public void delete(DBEntity o) {
        try {
            if(o instanceof Customer) {
                deleteById("data/customers.csv", o.getId(), 0);
                deleteById("data/customer_order.csv",o.getId(), 0);
                return;
            }
            if(o instanceof Courier) {
                deleteById("data/couriers.csv", o.getId(), 0);
                deleteById("data/couriers_deliveries.csv", o.getId(), 0);
                return;
            }
            if(o instanceof Establishment) {
                deleteById("data/establishment.csv", o.getId(), 0);
                deleteById("data/establishment_menuItem.csv", o.getId(), 0);
                return;
            }
            if(o instanceof MenuItem) {
                deleteById("data/menuItem.csv", o.getId(), 0);
                deleteById("data/establishment_menuItem.csv", o.getId(), 1);
                deleteById("data/order_menuItem.csv", o.getId(), 1);
                return;
            }
            if(o instanceof Order) {
                deleteById("data/menuItem.csv", o.getId(), 0);
                deleteById("data/order_menuItem.csv", o.getId(), 0);
                deleteById("data/delivery_order.csv", o.getId(), 1);
                return;
            }
            if(o instanceof Delivery) {
                deleteById("data/deliveries.csv", o.getId(), 0);
                deleteById("data/couriers_deliveries.csv", o.getId(), 1);
                deleteById("data/delivery_order.csv", o.getId(), 0);
                return;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertAsoc(DBEntity o1, DBEntity o2) {
        if(o1 instanceof Establishment && o2 instanceof MenuItem) {
            String record = o1.getId() + ',' + o2.getId() + ',' + ((Establishment)o1).getMenu().get((MenuItem)o2);
            addLine("data/establishment_menuItem.csv", record);
            addLog("Establishment " + o1.getId() +
                    " added MenuItem " + o2.getId() +
                    " in quantity of " + ((Establishment)o1).getMenu().get((MenuItem)o2));
            return;
        }
        if(o1 instanceof Order && o2 instanceof MenuItem) {
            String record = o1.getId() + ',' + o2.getId() + ',' + ((Order)o1).getMenuItemList().get((MenuItem)o2);
            addLine("data/order_menuItem.csv", record);
            addLog("Order + " + o1.getId() +
                    " contains MenuItem " + o2.getId() +
                    " in quantity of " + ((Order)o1).getMenuItemList().get((MenuItem)o2));
            return;
        }
        if(o1 instanceof Courier && o2 instanceof Delivery) {
            String record = (o1.getId() + ',' + o2.getId());
            addLine("data/courier_delivery.csv", record);
            addLog("Courier " + o1.getId() +
                    " took Delivery " + o2.getId());
            return;
        }

        if(o1 instanceof Customer && o2 instanceof Order) {
            String record = (o1.getId() + ',' + o2.getId());
            addLine("data/customer_order.csv", record);
            addLog("Customer " + o1.getId() +
                    " made Delivery " + o2.getId());
            return;
        }
    }

    // -------------------------ASOC FUNCTIONS---------------------------

    public SortedMap<MenuItem, Integer> readMenuFromCSV(String id, String pathname) {
        SortedMap<MenuItem, Integer> menu = new TreeMap<>();
        try {

            Scanner sc = new Scanner(new File(String.format("data/%s.csv", pathname)));

            while(sc.hasNextLine()) {
                String record = sc.nextLine();
                String[] data = record.split(",");
                if(data[0].equals(id)) {
                    List<MenuItem> menuItemList = this.menuItemList;
                    MenuItem menuItem =
                            menuItemList.stream()
                                .reduce(null, (pred, curr) -> {
                                    if(curr.getId().equals(data[1]))
                                        pred = curr;
                                    return pred;
                                });
                    menu.put(menuItem, Integer.parseInt(data[2]));
                }
            }
            sc.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return menu;
    }
    // ---------------------- READERS FOR LIST COMPOSING ---------------------
    public List<Delivery> readDeliveriesFromCSV(String id) {
        List<Delivery> deliveryList = new ArrayList<>();
        try {

            Scanner sc = new Scanner(new File("data/courier_delivery.csv"));

            while(sc.hasNextLine()) {
                String record = sc.nextLine();
                String[] data = record.split(",");
                if(data[0].equals(id)) {
                    Delivery delivery =
                            this.deliveryList.stream()
                                    .reduce(null, (pred, curr) -> {
                                        if(curr.getId().equals(data[1]))
                                            pred = curr;
                                        return pred;
                                    });
                    deliveryList.add(delivery);
                }
            }
            sc.close();



        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return deliveryList;
    }

    public List<Order> readOrdersFromCSV(String id) {
        List<Order> orderList = new ArrayList<>();
        try {

            Scanner sc = new Scanner(new File("data/customer_order.csv"));

            while(sc.hasNextLine()) {
                String record = sc.nextLine();
                String[] data = record.split(",");
                if(data[0].equals(id)) {
                    Order order =
                            this.orderList.stream()
                                    .reduce(null, (pred, curr) -> {
                                        if(curr.getId().equals(data[1]))
                                            pred = curr;
                                        return pred;
                                    });
                    orderList.add(order);
                }
            }
            sc.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return orderList;
    }
    // --------------- GENERIC READERS ----------------------------
    public List<Order> readAllOrdersFromCSV() {
        List<Order> orderList = new ArrayList<>();
        try {

            Scanner sc = new Scanner(new File("data/orders.csv"));

            while(sc.hasNextLine()) {
                String record = sc.nextLine();
                orderList.add(Order.readFromCSV(record));
            }
            sc.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return orderList;
    }

    public List<Account> readAllCustomersFromCSV() {
        List<Account> customerList= new ArrayList<>();
        try {

            Scanner sc = new Scanner(new File("data/customers.csv"));

            while(sc.hasNextLine()) {
                String record = sc.nextLine();
                customerList.add(Customer.readFromCSV(record));
            }
            sc.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return customerList;
    }

    public List<Account> readAllEstablishmentsFromCSV() {
        List<Account> establishmentList= new ArrayList<>();
        try {

            Scanner sc = new Scanner(new File("data/establishments.csv"));

            while(sc.hasNextLine()) {
                String record = sc.nextLine();
                establishmentList.add(Establishment.readFromCSV(record));
            }
            sc.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return establishmentList;
    }

    public List<Account> readAllCouriersFromCSV() {
        List<Account> couriersList= new ArrayList<>();
        try {

            Scanner sc = new Scanner(new File("data/couriers.csv"));

            while(sc.hasNextLine()) {
                String record = sc.nextLine();
                couriersList.add(Courier.readFromCSV(record));
            }
            sc.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return couriersList;
    }

    public List<Account> readAllAccountsFromCSV() {
        List<Account> accountList = new ArrayList<>();
        accountList.addAll(readAllCustomersFromCSV());
        accountList.addAll(readAllCouriersFromCSV());
        accountList.addAll(readAllEstablishmentsFromCSV());
        return accountList;
    }

    public  List<Delivery> readAllDeliveriesFromCSV() {
        List<Delivery> deliveryList = new ArrayList<>();
        try {

            Scanner sc = new Scanner(new File("data/deliveries.csv"));

            while(sc.hasNextLine()) {
                String record = sc.nextLine();
                deliveryList.add(Delivery.readFromCSV(record));
            }
            sc.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return deliveryList;
    }

    public List<MenuItem> readAllMenuItemsFromCSV() {
        List<MenuItem> menuItemList = new ArrayList<>();
        try {

            Scanner sc = new Scanner(new File("data/menuItems.csv"));

            while(sc.hasNextLine()) {
                String record = sc.nextLine();
                menuItemList.add(MenuItem.readFromCSV(record));
            }
            sc.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return menuItemList;
    }
    public void addLog(String log) {
        addLine("data/logger.csv", log + ',' + (new Date()));
    }

}
