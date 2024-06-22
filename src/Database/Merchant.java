package Database;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
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
        while(true){
            int flag = 0;
            System.out.println("Please choose to login or register(1 for login, 2 for register, else for exit).");
            String choose = sc.nextLine();
            switch(choose){
                case "1":
                case "2":
                    System.out.println("Please enter your phone_number: ");
                    String username = sc.nextLine();
                    System.out.println("Please enter your password: ");
                    String password = sc.nextLine();
                    flag = LoginOrRegister(choose, username, password);
                    break;
                default:
                    break;
            }
            if(flag == 0){
                break;
            }
        }
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
            String[] VIS = {"skip", "name", "price", "picture", "sort"};
            ArrayList<String[]> mealLIST = db.showDishOfMerchant(s_id);
            while(true){
                int whichOne = 0;
                System.out.println("Here's the meals.");
                System.out.println("I'll show results in Pagination query.");
                int endPage = (mealLIST.size() % 10 == 0) ?
                        (Math.max(1, mealLIST.size() / 10)) : (mealLIST.size() / 10 + 1);
                System.out.println("There are " + endPage + " pages.");
                while(true){
                    System.out.println("Please enter the page you want to see.(Exit to exit)");
                    String page = sc.nextLine();
                    if(page.equals("Exit")){
                        return;
                    }
                    if(Integer.parseInt(page) > endPage || Integer.parseInt(page) <= 0){
                        System.out.println("Invalid page number, please try again.");
                        continue;
                    }
                    int topF = Math.min(Integer.parseInt(page) * 10, mealLIST.size());
                    ArrayList<String[]> dataPa = dealMethod.copyArrayList(mealLIST,
                            Integer.parseInt(page) * 10 - 10,
                            topF
                    );
                    System.out.printf("%-5s%-15s%-10s%-10s%-10s%n", "raw", "name", "price", "picture", "sort");
                    int rawT = 0;
                    for(String[] args : dataPa){
                        System.out.printf("%-5s", rawT++);
                        dealMethod.printStr(args, VIS);
                    }
                    System.out.println("You can choose a meal.");
                    System.out.println("1 for query ahead. 2 for choose a meal. Else will exit.");
                    String read = sc.nextLine();
                    if(read.equals("1")){
                        continue;
                    }
                    else if(read.equals("2")){
                        System.out.println("Please enter the raw of the meal to see detailed information.(Exit for exit)");
                        String raw = "";
                        String id = "";
                        while(true){
                            raw = sc.nextLine();
                            if(raw.equals("Exit")){
                                return;
                            }
                            if(Integer.parseInt(raw) >= topF - Integer.parseInt(page) * 10 + 10
                                    || Integer.parseInt(raw) < 0){
                                System.out.println("Invalid page number, please try again.");
                                continue;
                            }
                            else{
                                break;
                            }
                        }
                        String[] VisD = {"skip", "skip", "name", "price", "picture", "sort", "nutrition", "allergen",
                                "score", "total_score", "score_count"};
                        System.out.printf("%-15s%-10s%-10s%-10s%-10s%-10s%-10s%-20s%-20s%n", "name", "price", "picture", "sort",
                                "nutrition", "allergen", "score", "total_score", "score_count");
                        String idMeal = dataPa.get(Integer.parseInt(raw))[0];
                        ArrayList<String[]> idsDealTemp = db.showDetailedInformationOfDish(Integer.parseInt(idMeal));
                        for(String[] d : idsDealTemp){
                            dealMethod.printStr(d, VisD);
                        }
                    }
                    else{
                        return;
                    }
                }
            }
        }
        catch (SQLException e){
            System.out.println("An error occurred!");
            e.printStackTrace();
        }
    }

    public int LoginOrRegister(String operate, String username, String password){
        Scanner sc = new Scanner(System.in);
        switch(operate){
            case "1":
                try{
                    s_id = Integer.parseInt(db.getMerchantIdByPhone_Number(username));
                    ArrayList<String[]> informationTemp = db.getMerchantInformation(s_id);
                    for(String[] information : informationTemp){
                        if(password.equals(information[5])){
                            System.out.println("Login successfully.");
                        }
                        else{
                            System.out.println("Login failed.");
                            return 1;
                        }
                    }
                }
                catch(SQLException e){
                    System.out.println("Login failed.");
                    return 1;
                }
                return 0;
            case "2":
                System.out.println("Please enter your name: ");
                String name = sc.nextLine();
                System.out.println("Please enter your address: ");
                String address = sc.nextLine();
                System.out.println("Please enter your main_dish: ");
                String mainDish = sc.nextLine();
                try{
                    db.merchantRegister(name, address, username, mainDish, password);
                    s_id = Integer.parseInt(db.getMerchantIdByPhone_Number(username));
                    System.out.println("Register successfully.");
                }
                catch(SQLException e){
                    System.out.println("Register failed.");
                }
                return 0;
            default:
                return 1;
        }
    }

    public void showInf(){
        try {
            ArrayList<String[]> merchantInf = db.showDetailedInformationOfMerchant(s_id);
            System.out.printf("%-5s%-15s%-20s%-15s%-15s%-10s%n", "id", "name", "address", "phone_number", "main_dish", "score");
            String[] VIS = {"id", "name", "address", "phone_number", "main_dish", "password", "score"};
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
        try {
            String[] VIS = {"skip", "name", "price", "picture", "sort"};
            ArrayList<String[]> mealLIST = db.showDishOfMerchant(s_id);
            while(true){
                int whichOne = 0;
                System.out.println("Here's the meals.");
                System.out.println("I'll show results in Pagination query.");
                int endPage = (mealLIST.size() % 10 == 0) ?
                        (Math.max(1, mealLIST.size() / 10)) : (mealLIST.size() / 10 + 1);
                System.out.println("There are " + endPage + " pages.");
                while(true){
                    System.out.println("Please enter the page you want to see.(Exit to exit)");
                    String page = sc.nextLine();
                    if(page.equals("Exit")){
                        return;
                    }
                    if(Integer.parseInt(page) > endPage || Integer.parseInt(page) <= 0){
                        System.out.println("Invalid page number, please try again.");
                        continue;
                    }
                    int topF = Math.min(Integer.parseInt(page) * 10, mealLIST.size());
                    ArrayList<String[]> dataPa = dealMethod.copyArrayList(mealLIST,
                            Integer.parseInt(page) * 10 - 10,
                            topF
                    );
                    System.out.printf("%-5s%-15s%-10s%-10s%-10s%n", "raw", "name", "price", "picture", "sort");
                    int rawT = 0;
                    for(String[] args : dataPa){
                        System.out.printf("%-5s", rawT++);
                        dealMethod.printStr(args, VIS);
                    }
                    System.out.println("You can choose a meal.");
                    System.out.println("1 for query ahead. 2 for choose a meal. Else will exit.");
                    String read = sc.nextLine();
                    if(read.equals("1")){
                        continue;
                    }
                    else if(read.equals("2")){
                        System.out.println("Please enter the raw of the meal to see detailed information.(Exit for exit)");
                        String raw = "";
                        while(true){
                            raw = sc.nextLine();
                            if(raw.equals("Exit")){
                                return;
                            }
                            if(Integer.parseInt(raw) >= topF - Integer.parseInt(page) * 10 + 10
                                    || Integer.parseInt(raw) < 0){
                                System.out.println("Invalid page number, please try again.");
                                continue;
                            }
                            else{
                                break;
                            }
                        }
                        String[] VisD = {"skip", "skip", "name", "price", "picture", "sort", "nutrition", "allergen",
                                "score", "total_score", "score_count"};
                        System.out.printf("%-15s%-10s%-10s%-10s%-10s%-10s%-10s%-20s%-20s%n", "name", "price", "picture", "sort",
                                "nutrition", "allergen", "score", "total_score", "score_count");
                        String idMeal = dataPa.get(Integer.parseInt(raw))[0];
                        ArrayList<String[]> idsDealTemp = db.showDetailedInformationOfDish(Integer.parseInt(idMeal));
                        for(String[] d : idsDealTemp){
                            dealMethod.printStr(d, VisD);
                        }
                        while(true){
                            System.out.println("Here's the information about the meal.");
                            System.out.println("Which attribute do you want to change?(Capitalizes the first letter)");
                            System.out.println("PS:enter EXIT to exit.");
                            String attribute = sc.nextLine();
                            switch(attribute){
                                case "Name":
                                    System.out.println("Please enter the new name.");
                                    String name = sc.nextLine();
                                    db.changeData("dish", new String[]{idMeal}, "name", name);
                                    return;
                                case "Price":
                                    System.out.println("Please enter the new price.");
                                    String price = sc.nextLine();
                                    db.changeData("dish", new String[]{idMeal}, "price", price);
                                    return;
                                case "Picture":
                                    System.out.println("Please enter the new picture.");
                                    String picture = sc.nextLine();
                                    db.changeData("dish", new String[]{idMeal}, "picture", picture);
                                    return;
                                case "Classification":
                                    System.out.println("Please enter the new classification.");
                                    String classification = sc.nextLine();
                                    db.changeData("dish", new String[]{idMeal}, "sort", classification);
                                    return;
                                case "Nutrition":
                                    System.out.println("Please enter the new nutrition.");
                                    String nutrition = sc.nextLine();
                                    db.changeData("dish", new String[]{idMeal}, "nutrition", nutrition);
                                    return;
                                case "Allergen":
                                    System.out.println("Please enter the new allergen.");
                                    String allergen = sc.nextLine();
                                    db.changeData("dish", new String[]{idMeal}, "allergen", allergen);
                                    return;
                                case "EXIT":
                                    return;
                                default:
                                    System.out.println("Input error!");
                                    break;
                            }
                        }
                    }
                    else{
                        return;
                    }
                }
            }
        }
        catch (SQLException e){
            System.out.println("An error occurred!");
            e.printStackTrace();
        }
    }

    public void addMeal(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please input the attribute.");
        System.out.println("Please input the name.");
        String name = sc.nextLine();
        System.out.println("Please input the price");
        String price = sc.nextLine();
        System.out.println("Please input the picture");
        String picture = sc.nextLine();
        System.out.println("Please input the sort");
        String sort = sc.nextLine();
        System.out.println("Please input the nutrition");
        String nutrition = sc.nextLine();
        System.out.println("Please input the allergen");
        String allergen = sc.nextLine();
        try {
            db.merchantAddDish(s_id, name, price, picture, sort, nutrition, allergen);
        }
        catch (SQLException e){
            System.out.println("An error occurred.");
        }
    }
}
