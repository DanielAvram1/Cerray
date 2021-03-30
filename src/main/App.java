package main;

import Account.*;
import MenuItem.MenuItem;
import Order.Order;
import Order.OrderService;
import db.DB;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class App {
    public static void main(String[] args) {

        System.out.println("Bun venit in Cerray! Introduceti entitatea pe care o reprezentati:");

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            boolean contMainSession = true;
            while(contMainSession) {
                System.out.println("login\tregister\texit");
                String input = in.readLine();
                switch (input) {
                    case "login" :
                        case "register" : {
                        Account account;
                        if(input.equals("login"))
                            account = AccountService.login();
                        else
                            account = AccountService.register();

                        if(account == null)
                            break;

                        if(account instanceof Customer){
                            CustomerService customerService = new CustomerService((Customer)account);
                            customerService.session();
                            break;
                        }
                        if(account instanceof Establishment){
                            EstablishmentService establishmentService = new EstablishmentService((Establishment)account);
                            establishmentService.session();
                            break;
                        }
                        if(account instanceof Courier){
                            CourierService courierService = new CourierService((Courier)account);
                            courierService.session();
                            break;
                        }

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
