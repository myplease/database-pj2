package Database;

import java.util.ArrayList;

public class dealMethod {
    public static void printStr(String[] list, String[] vis){
        int cmp = 0;
        for (String s : list) {
            if(s == null && vis[cmp].equals("comment")){
                s = "No text.";
            }
            else if(s == null){
                s = "0";
            }

            switch(vis[cmp]){
                case "id" -> System.out.printf("%-5s", s);
                case "name" -> System.out.printf("%-15s", s);
                case "address" -> System.out.printf("%-20s", s);
                case "phone_number" -> System.out.printf("%-15s", s);
                case "main_dish" -> System.out.printf("%-15s", s);
                case "sid" -> System.out.printf("%-5s", s);
                case "price", "sort" -> System.out.printf("%-10s", s);
                case "picture" -> System.out.printf("%-10s", s);
                case "nutrition" -> System.out.printf("%-10s", s);
                case "allergen" -> System.out.printf("%-10s", s);
                case "score" -> System.out.printf("%-10s", s);
                case "total_score" -> System.out.printf("%-20s", s);
                case "score_count" -> System.out.printf("%-20s", s);
                case "gender" -> System.out.printf("%-10s", s);
                case "student_id" -> System.out.printf("%-20s", s);
                case "date" -> System.out.printf("%-15s", s);
                case "time" -> System.out.printf("%-15s", s);
                case "is_online" -> System.out.printf("%-10s", s);
                case "status" -> System.out.printf("%-10s", s);
                case "state" -> System.out.printf("%-10s", s);
                case "text" -> System.out.printf("%-256s", s);
                case "age" -> System.out.printf("%-5s", s);
                case "job" -> System.out.printf("%-10s", s);
                case "comment" -> System.out.printf("%-256s", s);
                case "online_num" -> System.out.printf("%-15s", s);
                case "offline_num" -> System.out.printf("%-15s", s);
                case "like_num" -> System.out.printf("%-15s", s);
                case "dish_name" -> System.out.printf("%-15s", s);
                case "user_name" -> System.out.printf("%-15s", s);
                case "week_sale_num" -> System.out.printf("%-15s", s);
                case "month_sale_num" -> System.out.printf("%-15s", s);
                case "year_sale_num" -> System.out.printf("%-15s", s);
                case "order_num" -> System.out.printf("%-15s", s);
                case "dish_num" -> System.out.printf("%-10s", s);
                case "password" -> System.out.printf("%-20s", s);
                case "within_1_DAY" -> System.out.printf("%-25s", s);
                case "between_1_and_2_DAY" -> System.out.printf("%-25s", s);
                case "between_2_and_3_DAY" -> System.out.printf("%-25s", s);
                case "between_3_and_4_DAY" -> System.out.printf("%-25s", s);
                case "between_4_and_5_DAY" -> System.out.printf("%-25s", s);
                case "within_1_WEEK" -> System.out.printf("%-25s", s);
                case "between_1_and_2_WEEK" -> System.out.printf("%-25s", s);
                case "between_2_and_3_WEEK" -> System.out.printf("%-25s", s);
                case "between_3_and_4_WEEK" -> System.out.printf("%-25s", s);
                case "between_4_and_5_WEEK" -> System.out.printf("%-25s", s);
                case "within_1_MONTH" -> System.out.printf("%-25s", s);
                case "between_1_and_2_MONTH" -> System.out.printf("%-25s", s);
                case "between_2_and_3_MONTH" -> System.out.printf("%-25s", s);
                case "between_3_and_4_MONTH" -> System.out.printf("%-25s", s);
                case "between_4_and_5_MONTH" -> System.out.printf("%-25s", s);
                case "within_1_YEAR" -> System.out.printf("%-25s", s);
                case "between_1_and_2_YEAR" -> System.out.printf("%-25s", s);
                case "between_2_and_3_YEAR" -> System.out.printf("%-25s", s);
                case "between_3_and_4_YEAR" -> System.out.printf("%-25s", s);
                case "between_4_and_5_YEAR" -> System.out.printf("%-25s", s);
                case "morning" -> System.out.printf("%-15s", s);
                case "afternoon" -> System.out.printf("%-15s", s);
                case "evening" -> System.out.printf("%-15s", s);
                case "night" -> System.out.printf("%-15s", s);
                case "merchant_name" -> System.out.printf("%-20s", s);
                case "comment_count" -> System.out.printf("%-20s", s);
                case "order_count" -> System.out.printf("%-20s", s);
                case "no_score_count" -> System.out.printf("%-20s", s);
                case "1_count" -> System.out.printf("%-10s", s);
                case "2_count" -> System.out.printf("%-10s", s);
                case "3_count" -> System.out.printf("%-10s", s);
                case "4_count" -> System.out.printf("%-10s", s);
                case "5_count" -> System.out.printf("%-10s", s);
                case "total_count" -> System.out.printf("%-20s", s);
                case "sale_num" -> System.out.printf("%-10s", s);
                case "change_price_count" -> System.out.printf("%-20s", s);
                case "skip" -> {}
                default -> System.out.printf("%-5s", s);
            }
            cmp++;
            if(cmp == vis.length) cmp = 0;
        }
        System.out.println();
    }

    public static ArrayList<String[]> copyArrayList(ArrayList<String[]> listB, int begin, int end){
        ArrayList<String[]> listA = new ArrayList<>();
        for(int i = begin; i < end; i++){
            listA.add(listB.get(i));
        }
        return listA;
    }

    public static String[] getID(ArrayList<String[]> list){
        String[] ids = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ids[i] = list.get(i)[0];
        }
        return ids;
    }

    public static int judgeRawValue(String s){
        return switch (s) {
            case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" -> 1;
            default -> 0;
        };
    }

    public static int judgePageValue(String s){
        for(int i = 0; i < s.length(); i++){
            if(judgeRawValue(String.valueOf(s.charAt(i))) == 0){
                return 0;
            }
        }
        return 1;
    }
}
