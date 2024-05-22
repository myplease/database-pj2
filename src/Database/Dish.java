package Database;

public class Dish {
    private int id;
    private String name;
    private double price;
    private int number = 0;
    private String picture;
    private String classification;
    private String saleVolume;
    private String component;
    private String nutrition;
    private String allergen;

    public Dish(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Dish(){}

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    public String getPicture() {
        return picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getClassification() {
        return classification;
    }
    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getSaleVolume() {
        return saleVolume;
    }
    public void setSaleVolume(String saleVolume) {
        this.saleVolume = saleVolume;
    }

    public String getComponent() {
        return component;
    }
    public void setComponent(String component) {
        this.component = component;
    }

    public String getNutrition() {
        return nutrition;
    }
    public void setNutrition(String nutrition) {
        this.nutrition = nutrition;
    }

    public String getAllergen() {
        return allergen;
    }
    public void setAllergen(String allergen) {
        this.allergen = allergen;
    }

    public void show(){
        System.out.println("ID: " + this.id);
        System.out.println("Name: " + this.name);
        System.out.println("Price: " + this.price);
        System.out.println("Picture: " + this.picture);
        System.out.println("Classification: " + this.classification);
        System.out.println("SaleVolume: " + this.saleVolume);
        System.out.println("Component: " + this.component);
        System.out.println("Nutrition: " + this.nutrition);
        System.out.println("Allergen: " + this.allergen);
    }
}
