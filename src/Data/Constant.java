package Data;

import java.util.Arrays;
import java.util.List;

public class Constant {
    public static List<String> merchantSchema = Arrays.asList("id","name","address","phone_number","main_dish","password");
    public static List<String> userSchema = Arrays.asList("id","name","gender","student_id","age","job","password");
    public static List<String> dishSchema = Arrays.asList("id","sid","name","price","picture","sort","nutrition","allergen","score","total_score","score_count");
    public static List<String> ordersSchema = Arrays.asList("id","date","time","uid","sid","is_online","comment","state");
    public static List<String> order_dishSchema = Arrays.asList("bid","fid","number","score");
    public static List<String> like_merchantSchema = Arrays.asList("uid","sid");
    public static List<String> like_dishSchema = Arrays.asList("uid","fid");
    public static List<String> messageSchema = Arrays.asList("id","uid","is_read","date","time","text");
    public static List<String> price_historySchema = Arrays.asList("fid","date","time","price");
    public static int INF_ORDER_NUM_OF_FAN = 3;


    public static int initialMerchantNumber = 10;
    public static int initialUserNumber = 10;
    public static int initialDishNumberEachMerchant = 10;
    public static int initialSortNumber = 3;
    public static int initialOrderNumberEachUser = 10;
    public static int initialLikeDishNumberEachUser = 10;
    public static int initialLikeMerchantNumberEachUser = 10;
    public static int initialDishNumberEachOrder = 3;
    public static int initialMessageEachUser = 1;

    public static boolean isDebug = true;
}
