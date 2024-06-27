package Database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import static java.lang.Integer.parseInt;

import Data.Database;

public class Administrator {
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
                    System.out.println("Add: add to any table.");
                    System.out.println("Delete: delete from any table.");
                    System.out.println("Change: change any table.");
                    System.out.println("Query: query any table.");
                    System.out.println("Home: return to the main interface.");
                    System.out.println("ActivityUser: show the activity level of users.");
                    System.out.println("AnalyseUser: show the analyse of users.");
                    break;
                case "Add":
                    add();
                    break;
                case "Delete":
                    delete();
                    break;
                case "Change":
                    change();
                    break;
                case "Query":
                    query();
                    break;
                case "Home":
                    return;
                case "ActivityUser":
                    activityUser();
                    break;
                case "AnalyseUser":
                    analyseUser();
                    break;
                default:
                    System.out.println("Input error!");
                    break;
            }
        }
    }

    public void analyseUser(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please choose a features to see.");
        System.out.println("1 for merchant. 2 for meals. 3 for evaluations. 4 for scores. else for exit");
        String option = sc.nextLine();
        switch(option){
            case "1" -> {
                System.out.println("1 for gender. 2 for job. 3 for age. else for exit");
                String choice = sc.nextLine();
                String feature = "";
                switch(choice){
                    case "1" -> feature = "gender";
                    case "2" -> feature = "job";
                    case "3" -> feature = "age";
                    default -> {
                        return;
                    }
                }
                ArrayList<String[]> informationD = new ArrayList<>();
                while(true){
                    System.out.println("Please input the value.");
                    String value = sc.nextLine();
                    try {
                        informationD = db.showOrderNumOfFeatureGroup(feature, value);
                        break;
                    }
                    catch(SQLException e){
                        System.out.println("Your input is invalid. Please try again.");
                    }
                }
                int endPage = (informationD.size() % 10 == 0) ?
                        (Math.max(1, informationD.size() / 10)) : (informationD.size() / 10 + 1);
                while(true) {
                    System.out.println("There are " + endPage + " pages.");
                    System.out.println("Please enter the page you want to see.(Exit to exit)");
                    String page = sc.nextLine();
                    if (page.equals("Exit")) {
                        return;
                    }
                    if (dealMethod.judgePageValue(page) == 0) {
                        System.out.println("Your input is invalid. Please try again.");
                        continue;
                    }
                    if (Integer.parseInt(page) > endPage || Integer.parseInt(page) <= 0) {
                        System.out.println("Invalid page number, please try again.");
                        continue;
                    }
                    int topF = Math.min(Integer.parseInt(page) * 10, informationD.size());
                    ArrayList<String[]> dataPa = dealMethod.copyArrayList(informationD,
                            Integer.parseInt(page) * 10 - 10,
                            topF
                    );
                    String[] VIS = {"sid", "merchant_name", "order_num"};
                    System.out.printf("%-5s%-20s%-15s%n", "sid", "merchant_name", "order_num");
                    for(String[] attribute : dataPa){
                        dealMethod.printStr(attribute, VIS);
                    }
                }
            }
            case "2" -> {
                System.out.println("Please input a id of merchant.");
                String s_id = sc.nextLine();
                System.out.println("1 for gender. 2 for job. 3 for age. else for exit");
                String choice = sc.nextLine();
                String feature = "";
                switch(choice){
                    case "1" -> feature = "gender";
                    case "2" -> feature = "job";
                    case "3" -> feature = "age";
                    default -> {
                        return;
                    }
                }
                ArrayList<String[]> informationD = new ArrayList<>();
                while(true){
                    System.out.println("Please input the value.");
                    String value = sc.nextLine();
                    try {
                        informationD = db.showOrderNumOfFeatureGroupOfDishFromMerchant(Integer.parseInt(s_id),
                                feature, value);
                        break;
                    }
                    catch(SQLException e){
                        System.out.println("Your input is invalid. Please try again.");
                    }
                }
                int endPage = (informationD.size() % 10 == 0) ?
                        (Math.max(1, informationD.size() / 10)) : (informationD.size() / 10 + 1);
                while(true) {
                    System.out.println("There are " + endPage + " pages.");
                    System.out.println("Please enter the page you want to see.(Exit to exit)");
                    String page = sc.nextLine();
                    if (page.equals("Exit")) {
                        return;
                    }
                    if (dealMethod.judgePageValue(page) == 0) {
                        System.out.println("Your input is invalid. Please try again.");
                        continue;
                    }
                    if (Integer.parseInt(page) > endPage || Integer.parseInt(page) <= 0) {
                        System.out.println("Invalid page number, please try again.");
                        continue;
                    }
                    int topF = Math.min(Integer.parseInt(page) * 10, informationD.size());
                    ArrayList<String[]> dataPa = dealMethod.copyArrayList(informationD,
                            Integer.parseInt(page) * 10 - 10,
                            topF
                    );
                    String[] VIS = {"fid", "dish_name", "order_num"};
                    System.out.printf("%-5s%-15s%-15s%n", "fid", "dish_name", "order_num");
                    for(String[] attribute : dataPa){
                        dealMethod.printStr(attribute, VIS);
                    }
                }
            }
            case "3" -> {
                System.out.println("1 for gender. 2 for job. 3 for age. else for exit");
                String choice = sc.nextLine();
                String feature = "";
                switch(choice){
                    case "1" -> feature = "gender";
                    case "2" -> feature = "job";
                    case "3" -> feature = "age";
                    default -> {
                        return;
                    }
                }
                ArrayList<String[]> informationD = new ArrayList<>();
                while(true){
                    System.out.println("Please input the value.");
                    String value = sc.nextLine();
                    try {
                        informationD = db.showCommentNumOfFeatureGroup(feature, value);
                        break;
                    }
                    catch(SQLException e){
                        System.out.println("Your input is invalid. Please try again.");
                    }
                }
                int endPage = (informationD.size() % 10 == 0) ?
                        (Math.max(1, informationD.size() / 10)) : (informationD.size() / 10 + 1);
                while(true) {
                    System.out.println("There are " + endPage + " pages.");
                    System.out.println("Please enter the page you want to see.(Exit to exit)");
                    String page = sc.nextLine();
                    if (page.equals("Exit")) {
                        return;
                    }
                    if (dealMethod.judgePageValue(page) == 0) {
                        System.out.println("Your input is invalid. Please try again.");
                        continue;
                    }
                    if (Integer.parseInt(page) > endPage || Integer.parseInt(page) <= 0) {
                        System.out.println("Invalid page number, please try again.");
                        continue;
                    }
                    int topF = Math.min(Integer.parseInt(page) * 10, informationD.size());
                    ArrayList<String[]> dataPa = dealMethod.copyArrayList(informationD,
                            Integer.parseInt(page) * 10 - 10,
                            topF
                    );
                    String[] VIS = {"comment_count", "order_count"};
                    System.out.printf("%-20s%-20s%n", "comment_count", "order_count");
                    for(String[] attribute : dataPa){
                        dealMethod.printStr(attribute, VIS);
                    }
                }
            }
            case "4" -> {
                System.out.println("1 for gender. 2 for job. 3 for age. else for exit");
                String choice = sc.nextLine();
                String feature = "";
                switch(choice){
                    case "1" -> feature = "gender";
                    case "2" -> feature = "job";
                    case "3" -> feature = "age";
                    default -> {
                        return;
                    }
                }
                ArrayList<String[]> informationD = new ArrayList<>();
                while(true){
                    System.out.println("Please input the value.");
                    String value = sc.nextLine();
                    try {
                        informationD = db.showScoreNumOfFeatureGroup(feature, value);
                        break;
                    }
                    catch(SQLException e){
                        System.out.println("Your input is invalid. Please try again.");
                    }
                }
                int endPage = (informationD.size() % 10 == 0) ?
                        (Math.max(1, informationD.size() / 10)) : (informationD.size() / 10 + 1);
                while(true) {
                    System.out.println("There are " + endPage + " pages.");
                    System.out.println("Please enter the page you want to see.(Exit to exit)");
                    String page = sc.nextLine();
                    if (page.equals("Exit")) {
                        return;
                    }
                    if (dealMethod.judgePageValue(page) == 0) {
                        System.out.println("Your input is invalid. Please try again.");
                        continue;
                    }
                    if (Integer.parseInt(page) > endPage || Integer.parseInt(page) <= 0) {
                        System.out.println("Invalid page number, please try again.");
                        continue;
                    }
                    int topF = Math.min(Integer.parseInt(page) * 10, informationD.size());
                    ArrayList<String[]> dataPa = dealMethod.copyArrayList(informationD,
                            Integer.parseInt(page) * 10 - 10,
                            topF
                    );
                    String[] VIS = {"no_score_count", "1_count", "2_count",
                            "3_count", "4_count", "5_count", "total_count"};
                    System.out.printf("%-20s%-10s%-10s%-10s%-10s%-10s%-20s%n", "no_score_count", "1_count", "2_count",
                            "3_count", "4_count", "5_count", "total_count");
                    for(String[] attribute : dataPa){
                        dealMethod.printStr(attribute, VIS);
                    }
                }
            }
            default -> {
                return;
            }
        }
    }

    public void activityUser(){
        Scanner sc = new Scanner(System.in);
        System.out.println("What do you want to see?");
        System.out.println("1 for Day. 2 for Week. 3 for Month. 4 for Year. 5 for detailed of a day. else for exit");
        try {
            ArrayList<String[]> informationD = new ArrayList<>();
            String[] VIS = new String[]{};
            String option = sc.nextLine();
            switch(option){
                case "1" -> {
                    String time = "DAY";
                    informationD = db.showOrderNumOfTime(time);
                    String[] dVIS = {"within_1_" + time, "between_1_and_2_" + time,
                            "between_2_and_3_" + time, "between_3_and_4_" + time, "between_4_and_5_" + time};
                    VIS = dVIS.clone();
                }
                case "2" -> {
                    String time = "WEEK";
                    informationD = db.showOrderNumOfTime(time);
                    String[] dVIS = {"within_1_" + time, "between_1_and_2_" + time,
                            "between_2_and_3_" + time, "between_3_and_4_" + time, "between_4_and_5_" + time};
                    VIS = dVIS.clone();
                }
                case "3" -> {
                    String time = "MONTH";
                    informationD = db.showOrderNumOfTime(time);
                    String[] dVIS = {"within_1_" + time, "between_1_and_2_" + time,
                            "between_2_and_3_" + time, "between_3_and_4_" + time, "between_4_and_5_" + time};
                    VIS = dVIS.clone();
                }
                case "4" -> {
                    String time = "YEAR";
                    informationD = db.showOrderNumOfTime(time);
                    String[] dVIS = {"within_1_" + time, "between_1_and_2_" + time,
                            "between_2_and_3_" + time, "between_3_and_4_" + time, "between_4_and_5_" + time};
                    VIS = dVIS.clone();
                }
                case "5" -> {
                    informationD = db.showOrderNumOfDayTime();
                    String[] dVIS = {"morning", "afternoon", "evening", "night"};
                    VIS = dVIS.clone();
                }
                default -> {
                    System.out.println("Input error!");
                    return;
                }
            }
            int endPage = (informationD.size() % 10 == 0) ?
                    (Math.max(1, informationD.size() / 10)) : (informationD.size() / 10 + 1);
            while(true) {
                System.out.println("There are " + endPage + " pages.");
                System.out.println("Please enter the page you want to see.(Exit to exit)");
                String page = sc.nextLine();
                if (page.equals("Exit")) {
                    return;
                }
                if (dealMethod.judgePageValue(page) == 0) {
                    System.out.println("Your input is invalid. Please try again.");
                    continue;
                }
                if (Integer.parseInt(page) > endPage || Integer.parseInt(page) <= 0) {
                    System.out.println("Invalid page number, please try again.");
                    continue;
                }
                int topF = Math.min(Integer.parseInt(page) * 10, informationD.size());
                ArrayList<String[]> dataPa = dealMethod.copyArrayList(informationD,
                        Integer.parseInt(page) * 10 - 10,
                        topF
                );
                dealMethod.printStr(VIS, VIS);
                for(String[] attribute : dataPa){
                    dealMethod.printStr(attribute, VIS);
                }
            }
        }
        catch(SQLException e){
            System.out.println("An error occurred.");
        }
    }

    private void add(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Which table do you want to add?");
        String table = sc.nextLine();
        System.out.println("Please input the attribute.(stop stands end)");
        ArrayList<String> attributeList = new ArrayList<>();
        int cmp = 0;
        while(true){
            System.out.println("Input" + cmp + ": ");
            String attribute = sc.nextLine();
            if(attribute.equals("stop")){
                break;
            }
            attributeList.add(attribute);
            cmp++;
        }
        try {
            db.addData(table, attributeList.toArray(new String[0]));
        }
        catch(Exception e){
            System.out.println("Add error!");
        }
    }

    private void delete(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Which table do you want to delete?");
        String table = sc.nextLine();
        System.out.println("Please input the id.");
        String id = sc.nextLine();
        String id2 = "";
        if(table.equals("order_dish") || table.equals("like_dish") || table.equals("like_merchant")){
            System.out.println("Please input the another id.");
            id2 = sc.nextLine();
            try {
                db.deleteData(table, new String[]{id, id2});
            }
            catch(Exception e){
                System.out.println("Delete error!");
            }
        }
        else{
            try {
                db.deleteData(table, new String[]{id});
            }
            catch(Exception e){
                System.out.println("Delete error!");
            }
        }
    }

    private void change(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Which table do you want to change?");
        String table = sc.nextLine();
        System.out.println("Which attribute do you want to change");
        String attribute = sc.nextLine();
        System.out.println("What's the new value.");
        String value = sc.nextLine();
        System.out.println("Please input the id.");
        String id = sc.nextLine();
        String id2 = "";
        if(table.equals("order_dish") || table.equals("like_dish") || table.equals("like_merchant")){
            System.out.println("Please input the another id.");
            id2 = sc.nextLine();
            try {
                db.changeData(table, new String[]{id, id2}, attribute, value);
            }
            catch(Exception e){
                System.out.println("Change error!");
            }
        }
        else{
            try {
                db.changeData(table, new String[]{id}, attribute, value);
            }
            catch(Exception e){
                System.out.println("Change error!");
            }
        }
    }

    private void query(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Which table do you want to query?");
        String table = sc.nextLine();
        System.out.println("Please input the attribute.(stop stands end)");
        ArrayList<String> attributeList = new ArrayList<>();
        int cmp = 0;
        while(true){
            System.out.println("Input" + cmp + ": ");
            String attribute = sc.nextLine();
            if(attribute.equals("stop")){
                break;
            }
            attributeList.add(attribute);
            cmp++;
        }
        System.out.println("Please input the condition.(A = B)");
        System.out.println("input A");
        String conditionA = sc.nextLine();
        System.out.println("input B");
        String conditionB = sc.nextLine();
        try {
            ArrayList<String[]> allAttribute = db.selectData(table, attributeList.toArray(new String[0]),
                    conditionA, conditionB);
            int endPage = (allAttribute.size() % 10 == 0) ?
                    (Math.max(1, allAttribute.size() / 10)) : (allAttribute.size() / 10 + 1);
            while(true) {
                System.out.println("There are " + endPage + " pages.");
                System.out.println("Please enter the page you want to see.(Exit to exit)");
                String page = sc.nextLine();
                if (page.equals("Exit")) {
                    return;
                }
                if (dealMethod.judgePageValue(page) == 0) {
                    System.out.println("Your input is invalid. Please try again.");
                    continue;
                }
                if (Integer.parseInt(page) > endPage || Integer.parseInt(page) <= 0) {
                    System.out.println("Invalid page number, please try again.");
                    continue;
                }
                int topF = Math.min(Integer.parseInt(page) * 10, allAttribute.size());
                ArrayList<String[]> dataPa = dealMethod.copyArrayList(allAttribute,
                        Integer.parseInt(page) * 10 - 10,
                        topF
                );
                dealMethod.printStr(attributeList.toArray(new String[0]), attributeList.toArray(new String[0]));
                for(String[] attribute : dataPa){
                    dealMethod.printStr(attribute, attributeList.toArray(new String[0]));
                }
            }
        }
        catch (SQLException e){
            System.out.println("Query error.");
        }
    }
}
