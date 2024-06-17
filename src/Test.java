import java.util.ArrayList;

public class Test {
    public static void main(String[] args) throws Exception {
        Database database = new Database("root","Zhang2004");
        ArrayList<String[]> rows = database.userShowMessageUnread(1);
        for (int i = 0; i < rows.size(); i++) {
            for (int j = 0; j < rows.get(i).length; j++) {
                System.out.print(rows.get(i)[j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
