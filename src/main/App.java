package main;

import Account.CustomerService;
import Account.Establishment;
import Account.EstablishmentService;
import MenuItem.MenuItem;
import Order.Order;
import Order.OrderService;
import Account.Customer;
import db.DB;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class App {
    public static void main(String[] args) {

        CustomerService customerService = new CustomerService();
        boolean cont = true;
        while(cont) {

            System.out.println("Ce doriti sa faceti?");
            System.out.println("login\tregister\texit");

            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                boolean contCustomerSession = false;
                String input = in.readLine();
                switch (input) {
                    case "login" : {
                        customerService.login();
                        contCustomerSession = true;
                        break;
                    }
                    case "register" : {
                        customerService.register();
                        contCustomerSession = true;
                        break;
                    }
                    case "exit" :{
                        cont = false;
                        break;
                    }

                    default: {
                        System.out.println("Ati introdus comanda gresit!");
                    }
                }

                while(contCustomerSession) {
                    EstablishmentService establishmentService = null;
                    boolean contOrderSession = false;

                    System.out.println("Ce doriti sa faceti?");
                    System.out.println("comanda\tcomenzile_mele\tdelogare\texit");

                    try {
                        input = in.readLine();
                        switch (input) {
                            case "comanda" : {
                                establishmentService = new EstablishmentService();
                                establishmentService.chooseEstablishment();
                                contOrderSession = true;

                                break;
                            }
                            case "comenzile_mele" : {
                                customerService.register();
                                break;
                            }
                            case "delogare" : {
                                contCustomerSession = false;
                            }
                            case "exit" : {
                                contCustomerSession = false;
                                cont = false;
                                break;
                            }

                            default: {
                                System.out.println("Ati introdus comanda gresit!");
                            }
                        }

                        while(contOrderSession) {
                            OrderService orderService = null;
                            System.out.println("Alegeti indexul bucatelor dorite si cantitatea comandata.\nCand finisati, scrieti done");
                            Order order = establishmentService.makeOrder();
                            System.out.println("Comanda voastra:");
                            System.out.println(order);
                            try {

                                input = in.readLine();


                            } catch (Exception e){
                                System.out.println(e.getMessage());
                            }
                        }


                    } catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }
}
