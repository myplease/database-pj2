import java.util.Arrays;
import java.util.List;

public class Constant {
    public static List<String> merchantSchema = Arrays.asList("id","name","address","phone_number","main_dish");
    public static List<String> userSchema = Arrays.asList("id","name","gender","student_id");
    public static List<String> dishSchema = Arrays.asList("id","sid","name","price","picture","sort","nutrition","allergen");
    public static List<String> orderSchema = Arrays.asList("id","time","uid","sid");
    public static List<String> order_dishSchema = Arrays.asList("bid","fid","number");
    public static List<String> like_merchantSchema = Arrays.asList("uid","sid");
    public static List<String> like_dishSchema = Arrays.asList("uid","fid");
    public static List<String> messageSchema = Arrays.asList("id","uid","read","time","context");

}