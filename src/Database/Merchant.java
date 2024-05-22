package Database;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Merchant {
    private int id;
    private String name;
    private String address;
    private String phone;
    private String mealMain;
    private ArrayList<Dish> menu = new ArrayList<>();

    public void setId(int id) {
        this.id = id;
    }
    public int getID() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getAddress() {
        return address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPhone() {
        return phone;
    }

    public void setMealMain(String mealMain) {
        this.mealMain = mealMain;
    }
    public String getMealMain() {
        return mealMain;
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
        String operate = sc.nextLine();
        while(true){
            System.out.println("Please enter your operation.(Enter Help to get all operation)");
            String operation = sc.nextLine();
            switch(operation){
                case "help":
                    System.out.println("Show: show your information.");
                    System.out.println("ChangeInformation: change your account.");
                    System.out.println("Home: return to the main interface.");
                    System.out.println("ChangeMeal: change the meal.");
                    System.out.println("Classify: classify your meal.");
                case "Show":
                    show();
                    break;
                case "ChangeInformation":
                    changeInformation();
                    break;
                case "Home":
                    return;
                case "ChangeMeal":
                    changeMeal();
                    break;
                case "Classify":
                    classify();
                    break;
                default:
                    break;
            }
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

    public void show(){
        System.out.println("ID: " + this.id);
        System.out.println("Name: " + this.name);
        System.out.println("Address: " + this.address);
        System.out.println("Phone: " + this.phone);
        System.out.println("main meal: " + this.mealMain);
    }

    public void changeInformation(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please choose the attribute to modify.");
        System.out.println("name, address, phone or mealMain (Enter exit to exit)");
        while(true){
            String attribute = sc.nextLine();
            switch(attribute){
                case "name":
                    System.out.println("Please enter the new name.");
                    String name = sc.nextLine();
                    setName(name);
                    break;
                case "address":
                    System.out.println("Please enter the new address.");
                    String address = sc.nextLine();
                    setAddress(address);
                    break;
                case "phone":
                    System.out.println("Please enter the new phone.");
                    String phone = sc.nextLine();
                    setPhone(phone);
                    break;
                case "mealMain":
                    System.out.println("Please enter the new mealMain.");
                    String mealMain = sc.nextLine();
                    setMealMain(mealMain);
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Input error!");
                    break;
            }
            //数据库操作，更新信息。
        }
    }

    public void changeMeal(){
        Scanner sc = new Scanner(System.in);
        Dish nowDish = new Dish();
        while(true){
            System.out.println("Please choose the meal to modify.");
            String meal = sc.nextLine();
            int flag = 0;
            int pos = 0;
            for(int i = 0; i < menu.size(); i++){
                if(menu.get(i).getName().equals(meal)){
                    flag = 1;
                    pos = i;
                    break;
                }
            }
            if(flag == 0){
                System.out.println("Input error! Please enter again.");
            }
            else{
                nowDish = menu.get(pos);
                menu.remove(pos);
                break;
            }
        }
        while(true){
            System.out.println("Here's the information about the meal.");
            nowDish.show();
            System.out.println("Which attribute do you want to change?(Capitalizes the first letter)");
            System.out.println("PS:enter EXIT to exit.");
            String attribute = sc.nextLine();
            switch(attribute){
                case "Name":
                    System.out.println("Please enter the new name.");
                    String name = sc.nextLine();
                    nowDish.setName(name);
                    break;
                case "Price":
                    System.out.println("Please enter the new price.");
                    String price = sc.nextLine();
                    nowDish.setPrice(Double.parseDouble(price));
                    break;
                case "Picture":
                    System.out.println("Please enter the new picture.");
                    String picture = sc.nextLine();
                    nowDish.setPicture(picture);
                    break;
                case "Classification":
                    System.out.println("Please enter the new classification.");
                    String classification = sc.nextLine();
                    nowDish.setClassification(classification);
                    break;
                case "SaleVolume":
                    System.out.println("Please enter the new sale volume.");
                    String volume = sc.nextLine();
                    nowDish.setSaleVolume(volume);
                    break;
                case "Component":
                    System.out.println("Please enter the new component.");
                    String component = sc.nextLine();
                    nowDish.setComponent(component);
                    break;
                case "Nutrition":
                    System.out.println("Please enter the new nutrition.");
                    String nutrition = sc.nextLine();
                    nowDish.setNutrition(nutrition);
                    break;
                case "Allergen":
                    System.out.println("Please enter the new allergen.");
                    String allergen = sc.nextLine();
                    nowDish.setAllergen(allergen);
                    break;
                case "EXIT":
                    menu.add(nowDish);
                    //然后把这个menu重新传入数据库

                    return;
                default:
                    System.out.println("Input error!");
                    break;
            }
        }
    }

    public void classify(){
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("Please choose the meal to set up the classification.");
            System.out.println("Please enter the meal name.");
            String meal = sc.nextLine();
            System.out.println("Please enter the classification.");
            String classification = sc.nextLine();
            for (Dish dish : menu) {
                if (dish.getName().equals(meal)) {
                    dish.setClassification(classification);
                    break;
                }
            }
            System.out.println("Do you want to exit? (Yes for exit, else for continue)");
            String choice = sc.nextLine();
            if(choice.equals("Yes")){
                //数据库操作，更新菜单。
                return;
            }
        }
    }
}
