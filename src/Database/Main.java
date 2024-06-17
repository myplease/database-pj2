package Database;

import java.util.Scanner;

import Data.Database;

public class Main {
    public static void main(String[] args) throws Exception {
        Database db = new Database("root", "lw456456");

        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("Please enter the operator!");
            System.out.println("1 for administrator. 2 for merchant. 3 for users.(EXIT for exit)");
            String operator = sc.nextLine();
            switch(operator){
                case "1":
//                    Administrator admin = new Administrator(db);
//                    admin.run();
                    break;
                case "2":
                    Merchant merchant = new Merchant(db);
                    merchant.run();
                    break;
                case "3":
                    User user = new User(db);
                    user.run();
                    break;
                case "EXIT":
                    return;
                default:
                    System.out.println("Invalid operator!");
                    break;
            }
        }
    }
}
