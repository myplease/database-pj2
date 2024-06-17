package Database;

import java.util.ArrayList;

public class dealMethod {
    public static void printStr(String[] list){
        for (String s : list) {
            System.out.println(s);
            System.out.println("\t");
        }
        System.out.println("\n");
    }

    public static String[] getID(ArrayList<String[]> list){
        String[] ids = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ids[i] = list.get(i)[0];
        }
        return ids;
    }
}
