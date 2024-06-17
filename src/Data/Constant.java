package Data;

import java.util.Arrays;
import java.util.List;

public class Constant {
    public static List<String> merchantSchema = Arrays.asList("id","name","address","phone_number","main_dish");
    public static List<String> userSchema = Arrays.asList("id","name","gender","student_id","password");
    public static List<String> dishSchema = Arrays.asList("id","sid","name","price","picture","sort","nutrition","allergen","score","total_score","score_count");
    public static List<String> ordersSchema = Arrays.asList("id","date","time","uid","sid","comment","state");
    public static List<String> order_dishSchema = Arrays.asList("bid","fid","number","score");
    public static List<String> like_merchantSchema = Arrays.asList("uid","sid");
    public static List<String> like_dishSchema = Arrays.asList("uid","fid");
    public static List<String> messageSchema = Arrays.asList("id","uid","is_read","date","time","text");

}
