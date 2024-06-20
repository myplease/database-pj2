package Database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import Data.Database;

public class User {
    private int u_id;
    private int s_id;

    private Database db;
    public User(Database db){
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
                    System.out.println("Please enter your student_id: ");
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
                    System.out.println("View: view your own account.");
                    System.out.println("Modify: modify your account.");
                    System.out.println("Search: search the merchant.");
                    System.out.println("Home: return to the main interface.");
                    System.out.println("Order: view your orders' history.");
                    break;
                case "View":
                    view();
                    break;
                case "Modify":
                    modify();
                    break;
                case "Search":
                    search();
                    break;
                case "Home":
                    return;
                case "Order":
                    order();
                    break;
                default:
                    break;
            }
        }
    }

    public int LoginOrRegister(String operate, String username, String password){
        Scanner sc = new Scanner(System.in);
        switch(operate){
            case "1":
                try{
                    u_id = Integer.parseInt(db.getUserIdByStudent_id(username));
                    ArrayList<String[]> informationTemp = db.getUserInformation(u_id);
                    for(String[] information : informationTemp){
                        if(password.equals(information[4])){
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
                System.out.println("Please enter your gender: ");
                String gender = sc.nextLine();
                try{
                    db.userRegister(name, gender, username, password);
                    u_id = Integer.parseInt(db.getUserIdByStudent_id(username));
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

    public void view(){
        try {
            ArrayList<String[]> temp = db.getUserInformation(u_id);
            System.out.printf("%-5s%-20s%-10s%-20s%n","id", "name", "gender", "student_id");
            String[] VIS = {"id", "name", "gender", "student_id", "password"};
            for (String[] strings : temp) {
                dealMethod.printStr(strings, VIS);
            }
        } catch (SQLException e) {
            System.out.println("An error occurred!");
        }
    }

    public void modify(){
        System.out.println("Please choose the attribute to modify.");
        System.out.println("name, gender or studentID (Enter exit to exit)");
        Scanner sc = new Scanner(System.in);
        try {
            while(true){
                int flag = 0;
                String attribute = sc.nextLine();
                switch(attribute){
                    case "name":
                        flag = 1;
                        System.out.println("Please enter the new name.");
                        String name = sc.nextLine();
                        db.changeData("user", new String[]{Integer.toString(u_id)}, "name", name);
                        break;
                    case "gender":
                        flag = 1;
                        System.out.println("Please enter the new gender.");
                        String gender = sc.nextLine();
                        db.changeData("user", new String[]{Integer.toString(u_id)}, "gender", gender);
                        break;
                    case "studentID":
                        flag = 1;
                        System.out.println("Please enter the new studentID.");
                        String studentID = sc.nextLine();
                        db.changeData("user", new String[]{Integer.toString(u_id)}, "student_id", studentID);
                        break;
                    case "exit":
                        return;
                    default:
                        System.out.println("Input error! Please enter again.");
                        break;
                }
                if(flag == 1){
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println("An error occurred!");
        }
    }

    public void search(){
        System.out.println("Please enter the name or id of the merchant.");
        Scanner sc = new Scanner(System.in);
        ArrayList<String[]> temp = new ArrayList<>();
        try {
            while(true){
                String search = sc.nextLine();
                temp = db.userSearchForMerchant(search);
                if(temp.isEmpty()){
                    System.out.println("Can't find any merchant.(Please try again)");
                }
                else{
                    break;
                }
            }
            System.out.printf("%-5s%-20s%-10s%n", "id", "name", "main_dish");
            String[] VIS = {"id", "name", "main_dish"};
            for(String[] strings : temp){
                dealMethod.printStr(strings, VIS);
            }
            System.out.println("Please choose a merchant.(Input id)");
            String[] idTemp = dealMethod.getID(temp);
            while(true){
                int flag = 0;
                String idInput = sc.nextLine();
                for(String str : idTemp){
                    if(str.equals(idInput)){
                        flag = 1;
                        s_id = Integer.parseInt(str);
                        break;
                    }
                }
                if(flag == 1){
                    System.out.println("You have entered the merchant.");
                    break;
                }
                else{
                    System.out.println("Input error! Please enter again.");
                }
            }
        }
        catch (SQLException e) {
            System.out.println("An error occurred!");
        }

        System.out.println("Please enter your operate.(Enter Help to get all operation)");
        while(true){
            String operate = sc.nextLine();
            switch(operate){
                case "Help":
                    System.out.println("Show: show the merchant information.");
                    System.out.println("Deals: show the dishes.");
                    System.out.println("Sort: show the sorts.");
                    System.out.println("Choose: choose the meal.");
                    System.out.println("Exit: exit the merchant.");
                    break;
                case "Show":
                    show();
                    break;
                case "Deals":
                    printDeals();
                    break;
                case "Sort":
                    printSort();
                    break;
                case "Choose":
                    chooseMeal();
                    break;
                case "Exit":
                    return;
                default:
                    System.out.println("Input error! Please enter again.");
                    break;
            }
        }
    }

    public void order(){
        try {
            ArrayList<String[]> ordHistory = db.userShowOrder(u_id);
            System.out.printf("%-15s%15s%10s%10s%20s", "date", "time", "is_online", "state", "name");
            String[] VIS = {"date", "time", "is_online", "state", "name"};
            for(String[] his : ordHistory){
                dealMethod.printStr(his, VIS);
            }
        }
        catch (SQLException e){
            System.out.println("An error occurred!");
        }
    }

    public void show(){
        try {
            ArrayList<String[]> informationMer = db.showDetailedInformationOfMerchant(s_id);
            System.out.printf("%-5s%-20s%-20s%-15s%-15s%n","id", "name", "address", "phone_number", "main_dish");
            String[] VIS = {"id", "name", "address", "phone_number", "main_dish"};
            for(String[] strings : informationMer) {
                dealMethod.printStr(strings, VIS);
            }
        } catch (SQLException e) {
            System.out.println("An error occurred!");
        }
    }

    public void printDeals(){
        try {
            ArrayList<String[]> dealTemp = db.showDishOfMerchant(s_id);
            String[] idsDealTemp = dealMethod.getID(dealTemp);
            System.out.printf("%-5s%-5s%-20s%-10s%-10s%-10s%-10s%-10s%-10s%-20s%-20s%n","id", "sid", "name", "price", "picture", "sort",
                    "nutrition", "allergen", "score", "total_score", "score_count");
            String[] VisD = {"id", "sid", "name", "price", "picture", "sort", "nutrition", "allergen",
                    "score", "total_score", "score_count"};
            for(String idDeal : idsDealTemp) {
                ArrayList<String[]> dealDetails = db.showDetailedInformationOfDish(Integer.parseInt(idDeal));
                dealMethod.printStr(dealDetails.getFirst(), VisD);
            }
        } catch (SQLException e) {
            System.out.println("An error occurred!");
        }
    }

    public void printSort(){
        try {
            ArrayList<String[]> sortTemp = db.showSortOfMerchant(s_id);
            System.out.printf("%-10s%n", "sort");
            for(String[] strings : sortTemp) {
                dealMethod.printStr(strings, new String[]{"sort"});
            }
        } catch (SQLException e) {
            System.out.println("An error occurred!");
        }
    }

    public void chooseMeal(){
        double sumMeal = 0;
        boolean onlineTemp = false;
        int[] mealId = new int[100];
        int[] mealNum = new int[100];
        double[] mealPrice = new double[100];
        int maxM = 0;
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("Please enter the id of the meal.");
            String id = sc.nextLine();
            try {
                ArrayList<String[]> mealList = db.showDishOfMerchant(s_id);
                String[] idTemp = new String[mealList.size()];
                String[] mealPriceTemp = new String[mealList.size()];
                for (int i = 0; i < mealList.size(); i++) {
                    idTemp[i] = mealList.get(i)[0];
                    mealPriceTemp[i] = mealList.get(i)[2];
                }
                int flagID = 0;
                for(int i = 0; i < mealList.size(); i++) {
                    if(id.equals(idTemp[i])){
                        mealPrice[maxM] = Double.parseDouble(mealPriceTemp[i]);
                        flagID = 1;
                        break;
                    }
                }
                if(flagID == 0){
                    System.out.println("The meal can't be found! Please enter again.");
                    continue;
                }
            }
            catch (SQLException e) {
                System.out.println("An error occurred!");
            }
            System.out.println("Please enter the number of the meal.(number > 0)");
            String num = sc.nextLine();
            while(true){
                if(Integer.parseInt(num) <= 0){
                    System.out.println("The number isn't valid! Please enter again.");
                    num = sc.nextLine();
                }
                else{
                    break;
                }
            }
            mealNum[maxM] = Integer.parseInt(num);
            mealId[maxM] = Integer.parseInt(id);
            maxM++;
            System.out.println("Do you still want to order?");
            System.out.println("1 for yes. else for no");
            String still = sc.nextLine();
            if(still.equals("1")){
                continue;
            }
            else{
                break;
            }
        }
        System.out.println("Please choose the online or not.(1 for yes, else for no)");
        String choose = sc.nextLine();
        if(choose.equals("1")){
            onlineTemp = true;
        }
        for(int i = 0; i < maxM; i++){
            sumMeal += mealPrice[i] * mealId[i];
        }
        System.out.println("You should pay "+ sumMeal);
        try {
            db.userOrderDish(u_id, s_id, mealId, mealNum, onlineTemp);
        }
        catch (SQLException e) {
            System.out.println("An error occurred!");
        }
    }
}
