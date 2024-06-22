package Data;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) throws Exception {
        Database database = new Database("root","Zhang2004");
//        database.userOrderDish(1,1,new int[]{3,4},new int[]{3,3},true);
//        System.out.println(database.getMerchantIdByPhone_Number("00000000000"));
        ArrayList<String[]> rows = database.getAvgScoreAndSaleOfDishFromMerchant(1);
        for (int i = 0; i < rows.size(); i++) {
            for (int j = 0; j < rows.get(i).length; j++) {
                System.out.print(rows.get(i)[j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
