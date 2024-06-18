package Database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import Data.Database;

public class Merchant {
    private int s_id;
    private Database db;
    public Merchant(Database db){
        this.db = db;
    }

    public void run(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please choose to login or register(1 for login, 2 for register, else for exit).");
        String choose = sc.nextLine();
        switch(choose){
            case "1":
            case "2":
                System.out.println("Please enter your phone: ");
                String username = sc.nextLine();
                System.out.println("Please enter your password: ");
                String password = sc.nextLine();
                LoginOrRegister(choose, username, password);
                break;
            default:
                break;
        }
        //TEST
            s_id = 1;
        //end
        while(true){
            System.out.println("Please enter your operation.(Enter Help to get all operation)");
            String operation = sc.nextLine();
            switch(operation){
                case "Help":
                    System.out.println("Show: show your information.");
                    System.out.println("ChangeInformation: change your account.");
                    System.out.println("Home: return to the main interface.");
                    System.out.println("ChangeMeal: change the meal.");
                    System.out.println("AddMeal: add your meal.");
                    System.out.println("Meal: show your meals.");
                    break;
                case "Show":
                    showInf();
                    break;
                case "ChangeInformation":
                    changeInformation();
                    break;
                case "Home":
                    return;
                case "ChangeMeal":
                    changeMeal();
                    break;
                case "AddMeal":
                    addMeal();
                    break;
                case "Meal":
                    showMeal();
                    break;
                default:
                    break;
            }
        }
    }

    public void showMeal(){
        Scanner sc = new Scanner(System.in);
        try {
            ArrayList<String[]> dealTemp = db.showDishOfMerchant(s_id);
            System.out.printf("%-5s%-20s%-10s%-10s%-10s%n", "id", "name", "price", "picture", "sort");
            String[] VIS = {"id", "name", "price", "picture", "sort"};
            for(String[] d : dealTemp){
                dealMethod.printStr(d, VIS);
            }
            System.out.println("Please input the deal_id to choose one meal to check the detail.(Exit for exit)");
            String command = sc.nextLine();
            if(command.equals("Exit")){
                return;
            }
            else{
                String[] VisD = {"id", "sid", "name", "price", "picture", "sort", "nutrition", "allergen",
                    "score", "total_score", "score_count"};
                System.out.printf("%-5s%-5s%-20s%-10s%-10s%-10s%-10s%-10s%-10s%-20s%-20s%n","id", "sid", "name", "price", "picture", "sort",
                        "nutrition", "allergen", "score", "total_score", "score_count");
                ArrayList<String[]> idsDealTemp = db.showDetailedInformationOfDish(Integer.parseInt(command));
                for(String[] d : idsDealTemp){
                    dealMethod.printStr(d, VisD);
                }
            }
        } catch (SQLException e) {
            System.out.println("An error occurred!");
        }
    }

    public void LoginOrRegister(String operate, String username, String password){
        switch(operate){
            case "1":
                //数据库操作，把商户信息传出来。
                //登录操作
                break;
            case "2":
                //数据库操作，把商户信息存入。
                //注册操作
                break;
            default:
                break;
        }
    }

    public void showInf(){
        try {
            ArrayList<String[]> merchantInf = db.showDetailedInformationOfMerchant(s_id);
            System.out.printf("%-5s%-20s%-20s%-15s%-10s%n", "id", "name", "address", "phone_number", "main_dish");
            String[] VIS = {"id", "name", "address", "phone_number", "main_dish"};
            for (String[] strings : merchantInf) {
                dealMethod.printStr(strings, VIS);
            }
        }
        catch(Exception e){
            System.out.println("An error occurred.");
        }
    }

    public void changeInformation(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please choose the attribute to modify.");
        System.out.println("name, address, phone or mealMain (Enter exit to exit)");
        try {
            while(true){
                int flag = 0;
                String attribute = sc.nextLine();
                switch(attribute){
                    case "name":
                        flag = 1;
                        System.out.println("Please enter the new name.");
                        String name = sc.nextLine();
                        db.changeData("merchant", new String[]{Integer.toString(s_id)}, "name", name);
                        break;
                    case "address":
                        flag = 1;
                        System.out.println("Please enter the new address.");
                        String address = sc.nextLine();
                        db.changeData("merchant", new String[]{Integer.toString(s_id)}, "address", address);
                        break;
                    case "phone":
                        flag = 1;
                        System.out.println("Please enter the new phone.");
                        String phone = sc.nextLine();
                        db.changeData("merchant", new String[]{Integer.toString(s_id)}, "phone_number", phone);
                        break;
                    case "mealMain":
                        flag = 1;
                        System.out.println("Please enter the new mealMain.");
                        String mealMain = sc.nextLine();
                        db.changeData("merchant", new String[]{Integer.toString(s_id)}, "main_dish", mealMain);
                        break;
                    case "exit":
                        return;
                    default:
                        System.out.println("Input error!");
                        break;
                }
                if(flag == 1){
                    break;
                }
            }
        }
        catch (SQLException e){
            System.out.println("An error occurred.");
        }
    }

    public void changeMeal(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please choose a meal.(Input a id)");
        String meal_id = sc.nextLine();
        try {
            System.out.printf("%-5s%-5s%-20s%-10s%-10s%-10s%-10s%-10s%-10s%-20s%-20s%n","id", "sid", "name", "price", "picture", "sort",
                    "nutrition", "allergen", "score", "total_score", "score_count");
            String[] VisD = {"id", "sid", "name", "price", "picture", "sort", "nutrition", "allergen",
                    "score", "total_score", "score_count"};
            ArrayList<String[]> mealInf = db.showDetailedInformationOfDish(Integer.parseInt(meal_id));
            dealMethod.printStr(mealInf.getFirst(), VisD);
        }
        catch (SQLException e){
            System.out.println("An error occurred.");
        }
        try {
            while(true){
                System.out.println("Here's the information about the meal.");
                System.out.println("Which attribute do you want to change?(Capitalizes the first letter)");
                System.out.println("PS:enter EXIT to exit.");
                String attribute = sc.nextLine();
                switch(attribute){
                    case "Name":
                        System.out.println("Please enter the new name.");
                        String name = sc.nextLine();
                        db.changeData("dish", new String[]{meal_id}, "name", name);
                        break;
                    case "Price":
                        System.out.println("Please enter the new price.");
                        String price = sc.nextLine();
                        db.changeData("dish", new String[]{meal_id}, "price", price);
                        break;
                    case "Picture":
                        System.out.println("Please enter the new picture.");
                        String picture = sc.nextLine();
                        db.changeData("dish", new String[]{meal_id}, "picture", picture);
                        break;
                    case "Classification":
                        System.out.println("Please enter the new classification.");
                        String classification = sc.nextLine();
                        db.changeData("dish", new String[]{meal_id}, "sort", classification);
                        break;
                    case "Nutrition":
                        System.out.println("Please enter the new nutrition.");
                        String nutrition = sc.nextLine();
                        db.changeData("dish", new String[]{meal_id}, "nutrition", nutrition);
                        break;
                    case "Allergen":
                        System.out.println("Please enter the new allergen.");
                        String allergen = sc.nextLine();
                        db.changeData("dish", new String[]{meal_id}, "allergen", allergen);
                        break;
                    case "EXIT":
                        return;
                    default:
                        System.out.println("Input error!");
                        break;
                }
            }
        }
        catch (SQLException e){
            System.out.println("An error occurred.");
        }
    }

    public void addMeal(){
        Scanner sc = new Scanner(System.in);
        ArrayList<String> tempMeal = new ArrayList<>();
        //test
        tempMeal.add("1");
        //test
        System.out.println("Please input the attribute.");
        System.out.println("Please input the name.");
        String name = sc.nextLine();
        tempMeal.add(name);
        System.out.println("Please input the price");
        String price = sc.nextLine();
        tempMeal.add(price);
        System.out.println("Please input the picture");
        String picture = sc.nextLine();
        tempMeal.add(picture);
        System.out.println("Please input the sort");
        String sort = sc.nextLine();
        tempMeal.add(sort);
        System.out.println("Please input the nutrition");
        String nutrition = sc.nextLine();
        tempMeal.add(nutrition);
        System.out.println("Please input the allergen");
        String allergen = sc.nextLine();
        tempMeal.add(allergen);
        //test
        tempMeal.add("2");
        tempMeal.add("3");
        tempMeal.add("4");
        //
        try {
            db.addData("dish", tempMeal.toArray(new String[0]));
        }
        catch (SQLException e){
            System.out.println("An error occurred.");
        }
    }
}
