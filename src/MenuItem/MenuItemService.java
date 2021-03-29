package MenuItem;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MenuItemService {
    MenuItem menuItem;

    public static void editMenuItem(MenuItem menuItem) throws Exception{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Introduceti campul pe care doriti sa-l modificati:");

        boolean cont = true;
        while(cont) {
            System.out.println("name\tprice\tquantity\tcancel");
            String input = in.readLine();
            switch (input) {
                case "name" : {
                    System.out.print("New name: ");
                    String name = in.readLine();
                    menuItem.setName(name);
                    cont = false;
                    break;
                }
                case "price" : {
                    System.out.print("New Price: ");
                    try {
                        String priceInput = in.readLine();
                        double price = Double.parseDouble(priceInput);
                        if(price < 0) {
                            System.out.println("Pretul nu poate fi negativ!");
                            break;
                        }
                        menuItem.setPrice(price);
                        cont = false;

                    } catch(Exception e) {
                        System.out.println("Ati introdus un pret gresit!");
                    }
                    break;
                }
                case "quantity" : {
                    System.out.print("New Quantity: ");
                    try {
                        String quanInput = in.readLine();
                        int quan = Integer.parseInt(quanInput);
                        if(quan < 0) {
                            System.out.println("Cantitatea nu poate fi negativa!");
                            break;
                        }
                        menuItem.setQuantity(quan);
                        cont = false;

                    } catch(Exception e) {
                        System.out.println("Ati introdus o cantitate gresita!");
                    }
                    break;
                }
                case "cancel" : {
                    return;
                }
                default: {
                    System.out.println("Ati introdus un camp gresit. Pentru a iesi, tastati cancel");
                }

            }
        }
        return;
    }
}
