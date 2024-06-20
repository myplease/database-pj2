package Database;

import java.sql.SQLException;
import java.util.ArrayList;
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
                default:
                    System.out.println("Input error!");
                    break;
            }
        }
    }

    private void add(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Which table do you want to add?");
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
        try {
            db.addData(table, attributeList);
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
        dealMethod.printStr(attributeList, attributeList);
        try {
            ArrayList<String[]> allAttribute = db.selectData(table, attributeList, conditionA, conditionB);
            for(String[] attribute : allAttribute){
                dealMethod.printStr(attribute, attributeList);
            }
        }
        catch (SQLException e){
            System.out.println("Query error.");
        }
    }
}
