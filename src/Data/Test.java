package Data;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) throws Exception {
//        Initial.initialMerchant();
//        Initial.initialUser();
//        Initial.initialDish();
//        Initial.initialOrders();
//        Initial.initialOrderDish();
//        Initial.initialLikeDish();
//        Initial.initialLikeMerchant();
//        Initial.initialMessage();
        Database database = new Database("root","Zhang2004");
        ArrayList<String[]> rows = database.showMostOftenChangePriceMerchant(0);
        for (int i = 0; i < rows.size(); i++) {
            for (int j = 0; j < rows.get(i).length; j++) {
                System.out.print(rows.get(i)[j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
