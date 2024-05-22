package Database;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class User {
    private int id;
    private String name;
    private String gender;
    private String studentID;
    private Merchant nowMerchant;
    private double balance;
    private double pay;
    private List<Dish> order = new ArrayList<>();
    private List<Dish> collectionDish = new ArrayList<>();
    private List<Merchant> collectionMerchant = new ArrayList<>();

    public void setId(int id) {
        this.id = id;
    }
    public int getId(){
        return id;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getGender() {
        return this.gender;
    }

    public void setStudentID(String studentID){
        this.studentID = studentID;
    }
    public String getStudentID(){
        return this.studentID;
    }

    public void setBalance(double balance){
        this.balance = balance;
    }
    public double getBalance(){
        return this.balance;
    }

    public void setPay(double pay){
        this.pay = pay;
    }
    public double getPay(){
        return this.pay;
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
        System.out.println("ID: " + this.id);
        System.out.println("Name: " + this.name);
        System.out.println("Gender: " + this.gender);
        System.out.println("StudentID: " + this.studentID);
        System.out.println("Balance: " + this.balance);
    }

    public void modify(){
        System.out.println("Please choose the attribute to modify.");
        System.out.println("name, gender or studentID (Enter exit to exit)");
        Scanner sc = new Scanner(System.in);
        while(true){
            String attribute = sc.nextLine();
            switch(attribute){
                case "name":
                    System.out.println("Please enter the new name.");
                    String name = sc.nextLine();
                    setName(name);
                    break;
                case "gender":
                    System.out.println("Please enter the new gender.");
                    String gender = sc.nextLine();
                    setGender(gender);
                    break;
                case "studentID":
                    System.out.println("Please enter the new studentID.");
                    String studentID = sc.nextLine();
                    setStudentID(studentID);
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Input error! Please enter again.");
                    break;
            }
            //数据库操作，更改数据库的内容。
        }
    }

    public void search(){
        System.out.println("Please enter the name or id of the merchant.");
        Scanner sc = new Scanner(System.in);
        //数据库操作，把信息输出，如果有多个同名商户就继续操作，让用户选择一个进入。
        //然后把信息存储到nowMerchant中。
        //如果用户不选择的话就直接return。
        //并且简单展示信息。

        System.out.println("Please enter your operate.(Enter Help to get all operation)");
        while(true){
            String operate = sc.nextLine();
            switch(operate){
                case "Help":
                    System.out.println("Show: show the merchant information.");
                    System.out.println("Collection: collect a merchant or a dish.");
                    System.out.println("searchMeal: search the meal.");
                    System.out.println("Exit: exit the merchant.");
                case "Show":
                    show();
                    break;
                case "Collection":
                    collectMerchant(nowMerchant);
                    break;
                case "searchMeal":
                    searchMeal();
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
        for (Dish item : order) {
            System.out.println("The dish name: " + item.getName());
            double aggregatePrice = item.getPrice() * item.getNumber();
            System.out.println("The dish price: " + aggregatePrice);
        }
    }

    public void check(){
        if(getBalance() < getPay()){
            System.out.println("Insufficient balance! Cant check!");
        }
        else{
            setBalance(getBalance() - getPay());
            //数据库操作，更改用户信息。
        }
    }

    public void show(){
        //展示商户信息。
    }

    public void collectMerchant(Merchant merchant){
        collectionMerchant.add(merchant);
    }

    public void searchMeal(){
        System.out.println("Please enter the name or id of the meal.");
        //数据库操作，找到这个meal并且返回。
        Dish meal = null;//把这个meal的属性都更改
        //展示信息。

        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your operate. Add or Collect the meal.");
        while(true){
            int flag = 0;
            String operate = sc.nextLine();
            switch(operate){
                case "Add":
                    order.add(meal);
                    break;
                case "Collect":
                    collectionDish.add(meal);
                    break;
                default:
                    flag = 1;
                    System.out.println("Input error! Please enter again.");
                    break;
            }
            if(flag == 0){
                break;
            }
        }
    }
}
