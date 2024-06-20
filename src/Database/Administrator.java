package Database;

import java.util.Scanner;
import static java.lang.Integer.parseInt;

import Data.Database;

public class Administrator {
    int u_id;
    int s_id;

    private Database db;
    public Administrator(Database db){
        this.db = db;
    }

    public void run(){
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("Please enter the password.");
            String password = sc.nextLine();
            if(password.equals("root")){
                System.out.println("Login successfully.");
                break;
            }
            else{
                System.out.println("Wrong password.");
            }
        }
        while(true){
            System.out.println("What do you want to do?(Enter Help to get all operation)");
            String option = sc.nextLine();
            switch(option){
                case "Help":
                    System.out.println("ChangeUser: change the user's account");
                    System.out.println("ChangeMerchant: change the merchant's account");
                    System.out.println("Home: return to the main interface.");
                    break;
                case "ChangeUser":
                    changeUser();
                    break;
                case "ChangeMerchant":
                    changeMerchant();
                    break;
                case "Home":
                    return;
                default:
                    System.out.println("Input error!");
                    break;
            }
        }
    }

    public void changeUser(){
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("Do you want to change?(EXIT for exit)");
            System.out.println("ID or userInformation");
            String option = sc.nextLine();
            switch (option) {
                case "ID" -> {
                    String id = sc.nextLine();
                }
                case "EXIT" -> {
                    //数据库操作，更新数据

                    return;
                }
                case "UserInformation" -> {}
                default -> System.out.println("Input error!");
            }
        }
    }

    public void changeMerchant(){
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("Do you want to change?(EXIT for exit)");
            System.out.println("ID, MerchantInformation or MerchantMenu.");
            String option = sc.nextLine();
            switch (option) {
                case "ID" -> {
                    String id = sc.nextLine();
                }
                case "EXIT" -> {
                    //数据库操作，更新数据

                    return;
                }
                case "MerchantInformation" -> {}
                case "MerchantMenu" -> {}
                default -> System.out.println("Input error!");
            }
        }
    }
}
