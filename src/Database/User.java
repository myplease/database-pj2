package Database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import Data.Database;

public class User {
    private int u_id;
    private int m_id;

    private Database db;
    public User(Database db){
        this.db = db;
    }

    public void run(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please choose to login or register(1 for login, 2 for register, else for exit).");
        String choose = sc.nextLine();
        switch(choose){
            case "1":
            case "2":
                System.out.println("Please enter your username: ");
                String username = sc.nextLine();
                System.out.println("Please enter your password: ");
                String password = sc.nextLine();
                LoginOrRegister(choose, username, password);
                break;
            default:
                break;
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
                    System.out.println("Order: view your orders.");
                    System.out.println("Check: check your orders.");
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
                    return;
                case "Check":
                    check();
                    break;
            }
        }
    }

    public void LoginOrRegister(String operate, String username, String password){
        switch(operate){
            case "1":
                //数据库操作，把用户信息传出来。
                //登录操作

                break;
            case "2":
                //数据库操作，把用户信息存入。
                //注册操作
                break;
            default:
                break;
        }
    }

    public void view(){
        try {
            ArrayList<String[]> temp = db.getUserInformation(u_id);
            System.out.println("id\tname\tgender\tstudent_id");
            for (String[] strings : temp) {
                dealMethod.printStr(strings);
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
        String search = sc.nextLine();
        try {
            ArrayList<String[]> temp = db.userSearchForMerchant(search);
            System.out.println("id\tname\tmain_dish");
            for(String[] strings : temp){
                dealMethod.printStr(strings);
            }
            if(temp.size() >= 2){
                System.out.println("Please choose a merchant.(Input id)");
                String[] idTemp = dealMethod.getID(temp);
                while(true){
                    int flag = 0;
                    String idInput = sc.nextLine();
                    for(String str : idTemp){
                        if(str.equals(idInput)){
                            flag = 1;
                            m_id = Integer.parseInt(str);
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
    }

    public void check(){
    }

    public void show(){
        try {
            ArrayList<String[]> informationMer = db.showDetailedInformationOfMerchant(m_id);
            System.out.println("id\tname\taddress\tphone_number\tmain_dish");
            for(String[] strings : informationMer) {
                dealMethod.printStr(strings);
            }
        } catch (SQLException e) {
            System.out.println("An error occurred!");
        }
    }

    public void printDeals(){
        try {
            ArrayList<String[]> dealTemp = db.showDishOfMerchant(m_id);
            String[] idsDealTemp = dealMethod.getID(dealTemp);
            System.out.println("id\tsid\tname\tprice\tpicture\tsort\tnutrition\tallergen" +
                    "\tscore\ttotal_score\tscore_count");
            for(String idDeal : idsDealTemp) {
                ArrayList<String[]> dealDetails = db.showDetailedInformationOfDish(Integer.parseInt(idDeal));
                dealMethod.printStr(dealDetails.get(0));
            }
        } catch (SQLException e) {
            System.out.println("An error occurred!");
        }
    }

    public void printSort(){
        try {
            ArrayList<String[]> sortTemp = db.showSortOfMerchant(m_id);
            System.out.println("sort");
            for(String[] strings : sortTemp) {
                dealMethod.printStr(strings);
            }
        } catch (SQLException e) {
            System.out.println("An error occurred!");
        }
    }

    public void chooseMeal(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the name or id of the meal.");

    }
}
