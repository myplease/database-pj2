package Data;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) throws Exception {
        Database database = new Database("root","Zhang2004");
//        database.userOrderDish(1,1,new int[]{3,4},new int[]{3,3},true);
//        System.out.println(database.getMerchantIdByPhone_Number("00000000000"));
//        database.merchantAddDish(1,"1","1","1","1","1","1");
//        database.userOrderDish(1,1,new int[]{1,10},new int[]{1,2},false);
//        database.userPutScoreOnOrderDish(4,10,5);
        ArrayList<String[]> rows = database.userGetSaleNumOfDishFromLike(1,false);
        for (int i = 0; i < rows.size(); i++) {
            for (int j = 0; j < rows.get(i).length; j++) {
                System.out.print(rows.get(i)[j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
