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
                    System.out.println("AnalyseUser: show the activity level of users.");
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
        System.out.println("What do you want to see?");
        System.out.println("1 for Day. 2 for Week. 3 for Month. 4 for Year. 5 for detailed of a day. else for exit");
        try {
            while(true){
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
        }
        catch(SQLException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
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
        try {
            db.deleteData(table, new String[]{id});
        }
        catch(Exception e){
            System.out.println("Delete error!");
        }
    }

    private void change(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Which table do you want to change?");
        String table = sc.nextLine();
        System.out.println("Please input the id.");
        String id = sc.nextLine();
        System.out.println("Which attribute do you want to change");
        String attribute = sc.nextLine();
        System.out.println("What's the new value.");
        String value = sc.nextLine();
        try {
            db.changeData(table, new String[]{id}, attribute, value);
        }
        catch(Exception e){
            System.out.println("Change error!");
        }
    }

    private void query(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Which table do you want to query?");
        String table = sc.nextLine();
        System.out.println("Please input the attribute.(stop stands end)");
        String[] attributeList = new String[105];
        int cmp = 0;
        while(true){
            System.out.println("Input" + cmp + ": ");
            String attribute = sc.nextLine();
            if(attribute.equals("stop")){
                break;
            }
            attributeList[cmp] = attribute;
            cmp++;
        }
        System.out.println("Please input the condition.(A = B)");
        System.out.println("input A");
        String conditionA = sc.nextLine();
        System.out.println("input B");
        String conditionB = sc.nextLine();
        try {
            ArrayList<String[]> allAttribute = db.selectData(table, attributeList, conditionA, conditionB);
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
                dealMethod.printStr(attributeList, attributeList);
                for(String[] attribute : allAttribute){
                    dealMethod.printStr(attribute, attributeList);
                }
            }
        }
        catch (SQLException e){
            System.out.println("Query error.");
        }
    }
}
