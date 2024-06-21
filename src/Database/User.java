package Database;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
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
                    System.out.println("ShowCollection: show your collections.");
                    System.out.println("UnreadMessage: show the message you dont read.");
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
                case "ShowCollection":
                    showCollection();
                    break;
                case "UnreadMessage":
                    unreadMessage();
                    break;
                default:
                    break;
            }
        }
    }

    public void unreadMessage(){
        Scanner sc = new Scanner(System.in);
        String[] messageID = new String[105];
        try {
            ArrayList<String[]> message = db.userShowMessageUnread(u_id);
            messageID = dealMethod.getID(message);
            String[] VIS = {"id", "date", "time", "text"};
            System.out.printf("%-5s%-15s%-15s%-256s%n","id", "date", "time", "text");
            for(String[] mes : message){
                dealMethod.printStr(mes, VIS);
            }
        }
        catch (SQLException e){
            System.out.println("An error occurred while reading your message");
            e.printStackTrace();
        }
        String m_id = "";
        while(true){
            System.out.println("You can read message.");
            System.out.println("You should input the id. Or Exit to exit.");
            m_id = sc.nextLine();
            if(m_id.equals("exit")){
                return;
            }
            int flag = 0;
            for(String id : messageID){
                if(id.equals(m_id)){
                    flag = 1;
                    break;
                }
            }
            if(flag == 0){
                System.out.println("You input a Non-existent message. Please try again.");
            }
            else{
                break;
            }
        }
        try {
            db.userReadMessage(Integer.parseInt(m_id));
        }
        catch (SQLException e){
            System.out.println("An error occurred while reading your message.");
            e.printStackTrace();
        }
    }

    public void showCollection(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Which one do you want to see.");
        System.out.println("1 for merchants. 2 for meals. else for exit");
        String choose = sc.nextLine();
        if(choose.equals("1")){
            try {
                ArrayList<String[]> likeMerchant = db.userShowLikeMerchant(u_id);
                System.out.printf("%-5s%-15s%-15s%n","id", "name", "main_dish");
                String[] VIS = {"id", "name", "main_dish"};
                for(String[] likeMerchantTemp: likeMerchant){
                    dealMethod.printStr(likeMerchantTemp, VIS);
                }
            }
            catch(SQLException e){
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
        else if(choose.equals("2")){
            try {
                ArrayList<String[]> likeDishes = db.userShowLikeDish(u_id);
                System.out.printf("%-5s%-15s%-10s%-10s%-10s%n","id", "name", "price", "picture", "sort");
                String[] VIS = {"id", "name", "price", "picture", "sort"};
                for(String[] likeDishesTemp: likeDishes){
                    dealMethod.printStr(likeDishesTemp, VIS);
                }
            }
            catch(SQLException e){
                System.out.println("An error occurred.");
                e.printStackTrace();
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
            System.out.printf("%-5s%-15s%-10s%-20s%n","id", "name", "gender", "student_id");
            String[] VIS = {"id", "name", "gender", "student_id", "password"};
            for (String[] strings : temp) {
                dealMethod.printStr(strings, VIS);
            }
        } catch (SQLException e) {
            System.out.println("An error occurred!");
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    public void search(){
        System.out.println("Please enter the name of the merchant.");
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
            System.out.printf("%-5s%-15s%-15s%n", "id", "name", "main_dish");
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
            e.printStackTrace();
        }
        while(true){
            System.out.println("Please enter your operate.(Enter Help to get all operation)");
            String operate = sc.nextLine();
            switch(operate){
                case "Help":
                    System.out.println("Show: show the merchant information.");
                    System.out.println("Deals: show the dishes.");
                    System.out.println("DealInteresting: show the dish by the information you input.");
                    System.out.println("Sort: show the sorts.");
                    System.out.println("Choose: choose the meal.");
                    System.out.println("Collection: collect the merchant.");
                    System.out.println("ShowEvaluation: show the evaluations of the merchant.");
                    System.out.println("Exit: exit the merchant.");
                    break;
                case "Show":
                    show();
                    break;
                case "Deals":
                    printDeals();
                    break;
                case "DealInteresting":
                    dealInteresting();
                    break;
                case "Sort":
                    printSort();
                    break;
                case "Choose":
                    chooseMeal();
                    break;
                case "Collection":
                    collectMerchant();
                    break;
                case "ShowEvaluation":
                    showEvaluation();
                    break;
                case "Exit":
                    return;
                default:
                    System.out.println("Input error! Please enter again.");
                    break;
            }
        }
    }

    public void dealInteresting(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the information which do you want to search for meals.");
        String information = sc.nextLine();
        try {
            ArrayList<String[]> meals = db.userSearchForDishInMerchant(s_id, information);
            String[] VIS = {"id", "name", "price", "picture", "sort"};
            System.out.printf("%-5s%-15s%-10s%-10s%-10s%n", "id", "name", "price", "picture", "sort");
            for(String[] strings : meals){
                dealMethod.printStr(strings, VIS);
            }
        }
        catch (SQLException e) {
            System.out.println("An error occurred!");
            e.printStackTrace();
        }
    }

    public void showEvaluation(){
        //TODO

    }

    public void collectMerchant(){
        try {
            db.userLikeMerchant(u_id, s_id);
        }
        catch(SQLException e){
            System.out.println("Cant collect a merchant twice.");
        }
    }

    public void order(){
        Scanner sc = new Scanner(System.in);
        try {
            ArrayList<String[]> ordHistory = db.userShowOrder(u_id);
            String[] order_id = dealMethod.getID(ordHistory);
            String[] mealID = new String[1005];
            for(int i = 0; i < ordHistory.size(); i++){
                mealID[i] = ordHistory.get(i)[1];
            }
            System.out.printf("%-15s%-15s%-10s%-10s%-15s%n", "date", "time", "is_online", "state", "name");
            String[] VIS = {"skip", "skip", "date", "time", "is_online", "state", "name"};
            for(String[] his : ordHistory){
                dealMethod.printStr(his, VIS);
            }
            System.out.println("You can give a score.(1 for yes, else for no)");
            String judgeFlag = sc.nextLine();
            if(judgeFlag.equals("1")){
                System.out.println("You can judge the order or the meal.(1 for yes, else for meal)");
                String judgeWhich = sc.nextLine();
                String b_id = "";
                if(judgeWhich.equals("1")){
                    while(true){
                        int flag = 0;
                        System.out.println("Which one do you want to evaluate?");
                        b_id = sc.nextLine();
                        for(String o_id : order_id){
                            if(b_id.equals(o_id)){
                                flag = 1;
                                break;
                            }
                        }
                        if(flag == 0){
                            System.out.println("The order isn't existing. Please input again.");
                        }
                        else{
                            break;
                        }
                    }
                    System.out.println("Please input your evaluation.");
                    String evaluation = sc.nextLine();
                    db.userCommentOnOrder(Integer.parseInt(b_id), evaluation);
                }
                else{
                    while(true){
                        System.out.println("Which meal do you want to evaluate?(input the raw beginning at 0)");
                        String raw = sc.nextLine();
                        if(Integer.parseInt(raw) < 0 || Integer.parseInt(raw) >= ordHistory.size()){
                            System.out.println("The meal isn't existing. Please input again.");
                        }
                        else{
                            System.out.println("Please input your score.(1 ~ 5)");
                            while(true){
                                String score = sc.nextLine();
                                if(Integer.parseInt(score) < 1 || Integer.parseInt(score) > 5){
                                    System.out.println("The score is wrong. Please input again.");
                                    continue;
                                }
                                else{
                                    db.userPutScoreOnOrderDish(
                                            Integer.parseInt(ordHistory.get(Integer.parseInt(raw))[0]),
                                            Integer.parseInt(ordHistory.get(Integer.parseInt(raw))[1]),
                                            Integer.parseInt(score));
                                    break;
                                }
                            }
                            break;
                        }
                    }

                }
            }
        }
        catch (SQLException e){
            System.out.println("An error occurred!");
            e.printStackTrace();
        }
    }

    public void show(){
        try {
            ArrayList<String[]> informationMer = db.showDetailedInformationOfMerchant(s_id);
            System.out.printf("%-5s%-15s%-20s%-15s%-15s%-10s%n","id", "name", "address", "phone_number", "main_dish", "score");
            String[] VIS = {"id", "name", "address", "phone_number", "main_dish", "password", "score"};
            for(String[] strings : informationMer) {
                dealMethod.printStr(strings, VIS);
            }
        } catch (SQLException e) {
            System.out.println("An error occurred!");
            e.printStackTrace();
        }
    }

    public void printDeals(){
        try {
            ArrayList<String[]> dealTemp = db.showDishOfMerchant(s_id);
            String[] idsDealTemp = dealMethod.getID(dealTemp);
            System.out.printf("%-5s%-5s%-15s%-10s%-10s%-10s%-10s%-10s%-10s%-20s%-20s%n","id", "sid", "name", "price", "picture", "sort",
                    "nutrition", "allergen", "score", "total_score", "score_count");
            String[] VisD = {"id", "sid", "name", "price", "picture", "sort", "nutrition", "allergen",
                    "score", "total_score", "score_count"};
            for(String idDeal : idsDealTemp) {
                ArrayList<String[]> dealDetails = db.showDetailedInformationOfDish(Integer.parseInt(idDeal));
                dealMethod.printStr(dealDetails.getFirst(), VisD);
            }
        } catch (SQLException e) {
            System.out.println("An error occurred!");
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    public void chooseMeal(){
        double sumMeal = 0;
        boolean onlineTemp = false;
        ArrayList<Integer> mealId = new ArrayList<>();
        ArrayList<Integer> mealNum = new ArrayList<>();
        double[] mealPrice = new double[105];
        int maxM = 0;
        Scanner sc = new Scanner(System.in);
        while(true){
            int whichOne = 0;
            System.out.println("Please enter the id of the meal.(Exit for exit)");
            String id = sc.nextLine();
            if(id.equals("Exit")){
                return;
            }
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
                        whichOne = i;
                        flagID = 1;
                        break;
                    }
                }
                if(flagID == 0){
                    System.out.println("The meal can't be found! Please enter again.");
                    continue;
                }
                System.out.println("Do you want to buy meal or collect it?");
                System.out.println("Buy for buy. Collect for collect. Else for choose another");
                String choice = sc.nextLine();
                if(choice.equals("Buy")){
                    mealPrice[maxM] = Double.parseDouble(mealPriceTemp[whichOne]);
                }
                else if(choice.equals("Collect")){
                    db.userLikeDish(u_id, Integer.parseInt(id));
                    continue;
                }
                else{
                    continue;
                }
            }
            catch (SQLIntegrityConstraintViolationException e){
                System.out.println("Cant collect a thing twice.");
            }
            catch (SQLException e) {
                System.out.println("An error occurred!");
                e.printStackTrace();
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
            mealNum.add(Integer.parseInt(num));
            mealId.add(Integer.parseInt(id));
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
            sumMeal += mealPrice[i] * mealId.get(i);
        }
        System.out.println("You should pay "+ sumMeal);
        int[] mealIdInt = new int[maxM];
        int[] mealNumInt = new int[maxM];
        try {
            for(int i = 0; i < maxM; i++){
                mealIdInt[i] = mealId.get(i);
                mealNumInt[i] = mealNum.get(i);
            }
            db.userOrderDish(u_id, s_id, mealIdInt, mealNumInt, onlineTemp);
        }
        catch (SQLException e) {
            System.out.println("An error occurred!");
            e.printStackTrace();
        }
    }
}
