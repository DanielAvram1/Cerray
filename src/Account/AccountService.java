package Account;

import db.DB;
import db.DBCSVService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class AccountService {
    Account account;

    private static Account findAccount(String email, String password) {
        List<Account> accountList = DB.getInstance().accountList;
        for(Account account: accountList) {

            if(account.getEmail().equals(email) && account.getPassword().equals(password))
                return account;
        }
        return null;
    }

    static boolean isEmailLoggedIn( String email) {
        List<Account> accountList = DB.getInstance().accountList;
        for(Account account : accountList) {
            if(account.email.equals(email))
                return true;
        }
        return false;
    }

    public static Account register() throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Introduceti mai intai datele de baza:");


        String email = "";
        while(true) {
            System.out.print("Email: ");
            email = in.readLine();

            if(AccountService.isEmailLoggedIn(email)){
                System.out.println("Acest email e deja logat! Doriti sa continuati sesiunea de inregistrare? Y/N");
                String input = in.readLine();
                if(input.equals("N")) {
                    return null;
                }
            }
            else
                break;
        }

        System.out.print("Numarul de telefon: ");
        String phoneNumber = in.readLine();
        System.out.print("Parola: ");
        String password = in.readLine();

        Account account = new Account(email, phoneNumber, password);

        while(true) {
            System.out.println("Ce entitate doriti sa reprezentati?");
            System.out.println("customer\testablishment\tcourier\tcancel");
            String input = in.readLine();
            switch (input) {
                case "customer":{
                    return CustomerService.register(account);
                }
                case "establishment":{
                    return EstablishmentService.register(account);
                }
                case "courier":{
                    return CourierService.register(account);
                }
                case "cancel":{
                    return null;
                }
                default: {
                    System.out.println("Ati introdus comanda gresit! Pentru a iesi, tapati cancel");
                }
            }
        }
    }

    public static Account login() throws Exception {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        List<Account> loginAccountList = DB.getInstance().accountList;

        while(true) {
            System.out.print("Email: ");
            String email = in.readLine();

            System.out.print("Parola: ");
            String password = in.readLine();
            Account findAccount = findAccount(email, password);

            if(findAccount != null) {
                System.out.println("Account Logat!");
                DBCSVService.getInstance().addLog(findAccount.getId() + " logged in");
                return findAccount;
            }
            else {
                System.out.println("Ati introdus emailul sau parola gresit. Doriti sa mai incercati? Y/N");
                String input = in.readLine();
                if (input.equals("N")) {
                    return null;
                }
            }
        }
    }




}
