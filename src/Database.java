import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class Database {
    Connection connection;
    boolean debug = true;
    public Database(String username,String password) throws Exception {
            File file = new File("src/files/commands/initial.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", username, password);
            Statement statement = connection.createStatement();
            String line;
            while((line = reader.readLine())!=null){
                int retNum = statement.executeUpdate(line);
            }
            reader.close();
            statement.close();
            initialData();
    }
    public void initialData() throws IOException, SQLException {
        String line;
        BufferedReader reader = new BufferedReader(new FileReader(new File("src/files/data/merchant_initial_data.txt")));
        while ((line = reader.readLine())!=null){
            String[] argv = line.split(" ");
            addData("merchant",argv);
        }
        reader = new BufferedReader(new FileReader(new File("src/files/data/user_initial_data.txt")));
        while ((line = reader.readLine())!=null){
            String[] argv = line.split(" ");
            addData("user",argv);
        }
        reader = new BufferedReader(new FileReader(new File("src/files/data/dish_initial_data.txt")));
        while ((line = reader.readLine())!=null){
            String[] argv = line.split(" ");
            addData("dish",argv);
        }
        reader = new BufferedReader(new FileReader(new File("src/files/data/order_initial_data.txt")));
        while ((line = reader.readLine())!=null){
            String[] argv = line.split(" ");
            addData("order",argv);
        }
        reader = new BufferedReader(new FileReader(new File("src/files/data/order_dish_initial_data.txt")));
        while ((line = reader.readLine())!=null){
            String[] argv = line.split(" ");
            addData("order_dish",argv);
        }
        reader = new BufferedReader(new FileReader(new File("src/files/data/like_merchant_initial_data.txt")));
        while ((line = reader.readLine())!=null){
            String[] argv = line.split(" ");
            addData("like_merchant",argv);
        }
        reader = new BufferedReader(new FileReader(new File("src/files/data/like_dish_initial_data.txt")));
        while ((line = reader.readLine())!=null){
            String[] argv = line.split(" ");
            addData("like_dish",argv);
        }
        reader = new BufferedReader(new FileReader(new File("src/files/data/message_initial_data.txt")));
        while ((line = reader.readLine())!=null){
            String[] argv = line.split(" ");
            addData("message",argv);
        }
    }
    public boolean deleteData(String table,String[] argv) throws SQLException {
        //删除商户，菜，用户，订单，消息都只需要传入一个id，删除收藏菜，收藏店铺，订单项需要传入两个id
        List<String> schema;
        String sqlStatement;
        int argc;
        switch (table) {
            case "merchant" -> {
                schema = Constant.merchantSchema;
                sqlStatement = "DELETE FROM merchant WHERE id = ?";
                argc = 1;
            }
            case "user" -> {
                schema = Constant.userSchema;
                sqlStatement = "DELETE FROM user WHERE id = ?";
                argc = 1;
            }
            case "dish" -> {
                schema = Constant.dishSchema;
                sqlStatement = "DELETE FROM dish WHERE id = ?";
                argc = 1;
            }
            case "order" -> {
                schema = Constant.orderSchema;
                sqlStatement = "DELETE FROM order WHERE id = ?";
                argc = 1;
            }
            case "order_dish" -> {
                schema = Constant.order_dishSchema;
                sqlStatement = "DELETE FROM order_dish WHERE bid = ?,fid = ?";
                argc = 2;
            }
            case "like_merchant" -> {
                schema = Constant.like_merchantSchema;
                sqlStatement = "DELETE FROM like_merchant WHERE uid = ?,sid = ?";
                argc = 2;
            }
            case "like_dish" -> {
                schema = Constant.like_dishSchema;
                sqlStatement = "DELETE FROM like_dish WHERE uid = ?,fid = ?";
                argc = 2;
            }
            case "message" -> {
                schema = Constant.messageSchema;
                sqlStatement = "DELETE FROM message WHERE id = ?";
                argc = 1;
            }
            default -> {
                if (debug) System.err.println("Delete data err! Table: '" + table + "' do not exist!");
                return false;
            }
        }
        if (debug) if(argv.length != argc){
            System.err.println("Delete data err! Table: '" + table + "' need "+ argc +" argv!");
            return false;
        }
        String line = sqlStatement;
        PreparedStatement statement = connection.prepareStatement(line);
        for (int i = 0; i < argv.length; i++) {
            statement.setString(i+1,argv[i]);
        }
        if(statement.executeUpdate() >= 1) return true;
        else return false;
    }
    public boolean changeData(String table,String[] argv,String entry,String value) throws SQLException {
        List<String> schema;
        String sqlStatement;
        int argc;
        switch (table) {
            case "merchant" -> {
                schema = Constant.merchantSchema;
                sqlStatement = "WHERE id = ?";
                argc = 1;
            }
            case "user" -> {
                schema = Constant.userSchema;
                sqlStatement = "WHERE id = ?";
                argc = 1;
            }
            case "dish" -> {
                schema = Constant.dishSchema;
                sqlStatement = "WHERE id = ?";
                argc = 1;
            }
            case "order" -> {
                schema = Constant.orderSchema;
                sqlStatement = "WHERE id = ?";
                argc = 1;
            }
            case "order_dish" -> {
                schema = Constant.order_dishSchema;
                sqlStatement = "WHERE bid = ?,fid = ?";
                argc = 2;
            }
            case "like_merchant" -> {
                schema = Constant.like_merchantSchema;
                sqlStatement = "WHERE uid = ?,sid = ?";
                argc = 2;
            }
            case "like_dish" -> {
                schema = Constant.like_dishSchema;
                sqlStatement = "WHERE uid = ?,fid = ?";
                argc = 2;
            }
            case "message" -> {
                schema = Constant.messageSchema;
                sqlStatement = "WHERE id = ?";
                argc = 1;
            }
            default -> {
                if (debug) System.err.println("Change data err! Table: '" + table + "' do not exist!");
                return false;
            }
        }
        if(debug) if(argv.length != argc){
            System.err.println("Change data err! Table: '" + table + "' need "+ argc +" argv!");
            return false;
        }
        if(debug) if(!schema.contains(entry)){
            System.err.println("Change err! Table: '" + table + "' do not have property '" + entry +"' ");
            return false;
        }
        String line = "UPDATE " + table + " SET " + entry + " = ? " + sqlStatement;
        PreparedStatement statement = connection.prepareStatement(line);
        statement.setString(0,value);
        for (int i = 0; i < 1; i++) {
            statement.setString(i+2,argv[i]);
        }
        if(statement.executeUpdate() >= 1) return true;
        else return false;
    }
    public List<String[]> selectData(String table,String[] argv,String entry,String value) throws SQLException {
        List<String> schema;
        switch (table) {
            case "merchant" -> schema = Constant.merchantSchema;
            case "user" -> schema = Constant.userSchema;
            case "dish" -> schema = Constant.dishSchema;
            case "order" -> schema = Constant.orderSchema;
            case "order_dish" -> schema = Constant.order_dishSchema;
            case "like_merchant" -> schema = Constant.like_merchantSchema;
            case "like_dish" -> schema = Constant.like_dishSchema;
            case "message" -> schema = Constant.messageSchema;
            default -> {
                if (debug) System.err.println("selectData err! Table: '" + table + "' do not exist!");
                return null;
            }
        }
        if(!schema.contains(entry)) {
            System.err.println("Select err! Table: '" + table + "' do not have property '" + entry +"' ");
            return null;
        }
        for (int i = 0; i < argv.length; i++) {
            if(debug) if(!schema.contains(argv[i])) {
                System.err.println("Select err! Table: '" + table + "' do not have property '" + argv[i] +"' ");
                return null;
            }
        }
        String line = "SELECT ";
        for (int i = 0; i < argv.length; i++) {
            if(i != argv.length-1) line += argv[i] + ",";
            else line += argv[i];
        }
        line += " FROM " + table + " WHERE " + entry + " = ?";
        PreparedStatement statement = connection.prepareStatement(line);
        statement.setString(0,value);
        ResultSet resultSet = statement.executeQuery();
        List<String[]> resultList = new ArrayList<>();
        while (resultSet.next()){
            String[] row = new String[argv.length];
            for (int i = 0; i < argv.length; i++) {
                row[i] = resultSet.getString(argv[i]);
            }
            resultList.add(row);
        }
        return resultList;
    }
    public boolean addData(String table,String[] argv) throws SQLException {
        List<String> schema;
        String sqlStatement;
        int argc;
        switch (table) {
            case "merchant" -> {
                schema = Constant.merchantSchema;
                sqlStatement = "INSERT INTO merchant (name,address,phone_number,main_dish) VALUES (?,?,?,?)";
                argc = 4;
            }
            case "user" -> {
                schema = Constant.userSchema;
                sqlStatement = "INSERT INTO user(name,gender,student_id) VALUES (?,?,?)";
                argc = 3;
            }
            case "dish" -> {
                schema = Constant.dishSchema;
                sqlStatement = "INSERT INTO dish(sid,name,price,picture,sort,nutrition,allergen) VALUES (?,?,?,?,?,?,?)";
                argc = 7;
            }
            case "order" -> {
                schema = Constant.orderSchema;
                sqlStatement = "INSERT INTO order(time,uid,sid) VALUES (?,?,?)";
                argc = 3;
            }
            case "order_dish" -> {
                schema = Constant.order_dishSchema;
                sqlStatement = "INSERT INTO order_dish(bid,fid,number) VALUES (?,?,?)";
                argc = 3;
            }
            case "like_merchant" -> {
                schema = Constant.like_merchantSchema;
                sqlStatement = "INSERT INTO like_merchant(uid,sid) VALUES (?,?)";
                argc = 2;
            }
            case "like_dish" -> {
                schema = Constant.like_dishSchema;
                sqlStatement = "INSERT INTO like_dish(uid,fid) VALUES (?,?)";
                argc = 2;
            }
            case "message" -> {
                schema = Constant.messageSchema;
                sqlStatement = "INSERT INTO message(uid,read,time,context) VALUES (?,?,?,?)";
                argc = 4;
            }
            default -> {
                if (debug) System.err.println("Add data err! Table: '" + table + "' do not exist!");
                return false;
            }
        }
        if(debug) if(argv.length != argc){
            System.err.println("Add data err! Table: '" + table + "' need "+ argc +" argv!");
            return false;
        }
        String line = sqlStatement;
        PreparedStatement statement = connection.prepareStatement(line);
        for (int i = 0; i < argv.length; i++) {
            statement.setString(i+1,argv[i]);
        }
        if(statement.executeUpdate() >= 1) return true;
        else return false;
    }

}
