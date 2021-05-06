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

public class DBCSVService {

    private static DBCSVService singleInstance = null;
    private static DBCSVService secondSingleInstance = null;
    private static DBCSVService firstSingleInstance = null;
    private static DBCSVService thirdSingleInstance = null;
    private static DBCSVService fourthSingleInstance = null;

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
        if (singleInstance == null)
            singleInstance = new DBCSVService(4);
        return singleInstance;
    }

    public static DBCSVService getInstance(int nrInstance) {
        if(firstSingleInstance == null) {
            firstSingleInstance = new DBCSVService(1);
        }

        if(nrInstance == 1)
            return firstSingleInstance;

        if(secondSingleInstance == null){
            secondSingleInstance = new DBCSVService(2);
        }

        if(nrInstance == 2)
            return secondSingleInstance;

        if(thirdSingleInstance == null){
            thirdSingleInstance = new DBCSVService(3);
        }

        if(nrInstance == 3)
            return thirdSingleInstance;

        if(fourthSingleInstance == null){
            fourthSingleInstance = new DBCSVService(4);
        }

        if(nrInstance == 4)
            return fourthSingleInstance;

        if (singleInstance == null)
            singleInstance = new DBCSVService(5);
        return singleInstance;
    }

    private DBCSVService(int stage) {
        if(stage == 1) {
            fileList = Arrays.asList(   "customers", "couriers", "establishments", "menuItems", "orders", "deliveries",
                    "establishment_menuItem", "order_menuItem", "courier_delivery", "customer_order", "logger");

            initiateFiles();

            menuItemList = readAllMenuItemsFromCSV();

        }
        else if(stage == 2) {
            menuItemList = firstSingleInstance.menuItemList;

            establishmentList = readAllEstablishmentsFromCSV();
        }
        else if(stage == 3) {

            menuItemList = secondSingleInstance.menuItemList;
            establishmentList = secondSingleInstance.establishmentList;
            orderList = readAllOrdersFromCSV();

        }
        else if(stage == 4){
            menuItemList = thirdSingleInstance.menuItemList;
            establishmentList = thirdSingleInstance.establishmentList;
            orderList = thirdSingleInstance.orderList;

            deliveryList = readAllDeliveriesFromCSV();
        }
        else {
            menuItemList = thirdSingleInstance.menuItemList;
            orderList = thirdSingleInstance.orderList;
            establishmentList = thirdSingleInstance.establishmentList;
            deliveryList = thirdSingleInstance.deliveryList;

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

    public void insert(DBEntity o) {
        try {

            String record = o.toCSV();

            if(o instanceof Customer) {

                FileWriter fw = new FileWriter("data/customers.csv", true);
                fw.write(record + '\n');
                fw.close();

                addLog("Customer " + o.getId() + " was added");

                return;
            }

            if(o instanceof Courier) {
                FileWriter fw = new FileWriter("data/couriers.csv", true);
                fw.write(record + '\n');
                fw.close();
                addLog("Courier " + o.getId() + " was added");
                return;
            }

            if(o instanceof Establishment) {

                FileWriter fw = new FileWriter("data/establishments.csv", true);
                fw.write(record + '\n');
                fw.close();

                fw = new FileWriter("data/establishment_menuItem.csv", true);
                SortedMap<MenuItem, Integer> menu = ((Establishment)o).getMenu();
                for(MenuItem menuItem: menu.keySet()) {
                    fw.write(o.getId() + ',' + menuItem.getId() + ',' + menu.get(menuItem) + '\n');
                }
                fw.close();

                addLog("Establishment " + o.getId() + " was added");

                return;
            }

            if(o instanceof MenuItem) {

                FileWriter fw = new FileWriter("data/menuItems.csv", true);
                fw.write(record + '\n');
                fw.close();
                addLog("MenuItem " + o.getId() + " was added");
                return;

            }

            if(o instanceof Order) {

                FileWriter fw = new FileWriter("data/orders.csv", true);
                fw.write(record + '\n');
                fw.close();

                fw = new FileWriter("data/order_menuItem.csv", true);
                SortedMap<MenuItem, Integer> menuItemList = ((Order)o).getMenuItemList();
                for(MenuItem menuItem: menuItemList.keySet()) {
                    fw.write(o.getId() + ',' + menuItem.getId() + ',' + menuItemList.get(menuItem) + '\n');
                }
                fw.close();
                addLog("Order " + o.getId() + " was added");
                return;

            }

            if(o instanceof Delivery) {

                FileWriter fw = new FileWriter("data/deliveries.csv", true);
                fw.write(record + '\n');
                fw.close();

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
        FileWriter fw = new FileWriter(temp, true);

        while(sc.hasNextLine()) {
            String record = sc.nextLine();
            String[] data = record.split(",");
            if(data[pos].equals(id)) {
                continue;
            }
            fw.write(record + '\n');
        }
        sc.close();
        fw.close();

        old.delete();
        temp.renameTo(old);
    }

    public void delete(DBEntity o) {
        try {
            if(o instanceof Customer) {

                deleteById("data/customers.csv", o.getId(), 0);

                return;
            }

            if(o instanceof Courier) {
                deleteById("data/couriers.csv", o.getId(), 0);
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

                return;

            }

            if(o instanceof Order) {
                deleteById("data/menuItem.csv", o.getId(), 0);

                return;

            }

            if(o instanceof Delivery) {
                deleteById("data/deliveries.csv", o.getId(), 0);

                return;

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertAsoc(DBEntity o1, DBEntity o2) {
        try {

            if(o1 instanceof Establishment && o2 instanceof MenuItem) {
                String record = o1.getId() + ',' + o2.getId() + ',' + ((Establishment)o1).getMenu().get((MenuItem)o2);

                FileWriter fw = new FileWriter("data/establishment_menuItem.csv", true);
                fw.write(record + '\n');
                fw.close();

                addLog("Establishment " + o1.getId() +
                        " added MenuItem " + o2.getId() +
                        " in quantity of " + ((Establishment)o1).getMenu().get((MenuItem)o2));

                return;
            }

            if(o1 instanceof Order && o2 instanceof MenuItem) {
                String record = o1.getId() + ',' + o2.getId() + ',' + ((Order)o1).getMenuItemList().get((MenuItem)o2);

                FileWriter fw = new FileWriter("data/order_menuItem.csv", true);
                fw.write(record + '\n');
                fw.close();

                addLog("Order + " + o1.getId() +
                        " contains MenuItem " + o2.getId() +
                        " in quantity of " + ((Order)o1).getMenuItemList().get((MenuItem)o2));

                return;
            }

            if(o1 instanceof Courier && o2 instanceof Delivery) {
                String record = (o1.getId() + ',' + o2.getId());

                FileWriter fw = new FileWriter("data/courier_delivery.csv", true);
                fw.write(record + '\n');
                fw.close();
                addLog("Courier " + o1.getId() +
                        " took Delivery " + o2.getId());
                return;
            }

            if(o1 instanceof Customer && o2 instanceof Order) {
                String record = (o1.getId() + ',' + o2.getId());

                FileWriter fw = new FileWriter("data/customer_order.csv", true);
                fw.write(record + '\n');
                fw.close();
                addLog("Customer " + o1.getId() +
                        " made Delivery " + o2.getId());
                return;
            }

        } catch(IOException e) {
            System.out.println(e.getMessage());
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
        try {
            FileWriter fw = new FileWriter("data/logger.csv", true);
            fw.write(log + ',' + (new Date()) + '\n');
            fw.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return;
    }

}
