package Database;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the operator!");
        System.out.println("1 for administrator. 2 for merchant. 3 for users.(EXIT for exit)");
        while(true){
            String operator = sc.nextLine();
            switch(operator){
                case "1":
                    Administrator admin = new Administrator();
                    admin.run();
                    break;
                case "2":
                    Merchant merchant = new Merchant();
                    merchant.run();
                    break;
                case "3":
                    User user = new User();
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
