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

        System.out.println("Bun venit in Cerray! Introduceti entitatea pe care o reprezentati:");

        boolean cont = true;
        while(cont) {

            System.out.println("customer\tcourier\testablishment\texit");
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                boolean contMainSession = true;
                while(contMainSession) {
                    String input = in.readLine();
                    switch (input) {
                        case "customer" : {
                            CustomerService customerService = new CustomerService();
                            customerService.session();
                            break;
                        }
                        case "courier" : {
                            break;
                        }
                        case "establishment" :{
                            EstablishmentService establishmentService = new EstablishmentService();
                            establishmentService.session();
                            break;
                        }
                        case "exit" :{
                            return;
                        }

                        default: {
                            System.out.println("Ati introdus comanda gresit!");
                        }
                    }
                }




            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }
}
