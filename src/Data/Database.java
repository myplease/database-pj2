package Data;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
        reader = new BufferedReader(new FileReader(new File("src/files/data/orders_initial_data.txt")));
        while ((line = reader.readLine())!=null){
            String[] argv = line.split(" ");
            if(argv.length == Constant.ordersSchema.size() - 1) addData("orders",argv);
            else addDataByEntry("orders",new String[]{"date","time","uid","sid","is_online","state"},argv);
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
            case "orders" -> {
                schema = Constant.ordersSchema;
                sqlStatement = "DELETE FROM orders WHERE id = ?";
                argc = 1;
            }
            case "order_dish" -> {
                schema = Constant.order_dishSchema;
                sqlStatement = "DELETE FROM order_dish WHERE bid = ? AND fid = ?";
                argc = 2;
            }
            case "like_merchant" -> {
                schema = Constant.like_merchantSchema;
                sqlStatement = "DELETE FROM like_merchant WHERE uid = ? AND sid = ?";
                argc = 2;
            }
            case "like_dish" -> {
                schema = Constant.like_dishSchema;
                sqlStatement = "DELETE FROM like_dish WHERE uid = ? AND fid = ?";
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
            case "orders" -> {
                schema = Constant.ordersSchema;
                sqlStatement = "WHERE id = ?";
                argc = 1;
            }
            case "order_dish" -> {
                schema = Constant.order_dishSchema;
                sqlStatement = "WHERE bid = ? AND fid = ?";
                argc = 2;
            }
            case "like_merchant" -> {
                schema = Constant.like_merchantSchema;
                sqlStatement = "WHERE uid = ? AND sid = ?";
                argc = 2;
            }
            case "like_dish" -> {
                schema = Constant.like_dishSchema;
                sqlStatement = "WHERE uid = ? AND fid = ?";
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
        statement.setString(1,value);
        for (int i = 0; i < argv.length; i++) {
            statement.setString(i+2,argv[i]);
        }
        if(statement.executeUpdate() >= 1) return true;
        else return false;
    }
    public ArrayList<String[]> selectData(String table,String[] argv,String entry,String value) throws SQLException {
        List<String> schema;
        switch (table) {
            case "merchant" -> schema = Constant.merchantSchema;
            case "user" -> schema = Constant.userSchema;
            case "dish" -> schema = Constant.dishSchema;
            case "orders" -> schema = Constant.ordersSchema;
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
            if(debug)System.err.println("Select err! Table: '" + table + "' do not have property '" + entry +"' ");
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
        statement.setString(1,value);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<String[]> resultList = new ArrayList<>();
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
                sqlStatement = "INSERT INTO merchant (name,address,phone_number,main_dish,password) VALUES (?,?,?,?,?)";
                argc = schema.size() - 1;
            }
            case "user" -> {
                schema = Constant.userSchema;
                sqlStatement = "INSERT INTO user(name,gender,student_id,age,job,password) VALUES (?,?,?,?,?,?)";
                argc = schema.size() - 1;
            }
            case "dish" -> {
                schema = Constant.dishSchema;
                sqlStatement = "INSERT INTO dish(sid,name,price,picture,sort,nutrition,allergen,score,total_score,score_count) VALUES (?,?,?,?,?,?,?,?,?,?)";
                argc = schema.size() - 1;
            }
            case "orders" -> {
                schema = Constant.ordersSchema;
                sqlStatement = "INSERT INTO orders(date,time,uid,sid,is_online,comment,state) VALUES (?,?,?,?,?,?,?)";
                argc = schema.size() - 1;
            }
            case "order_dish" -> {
                schema = Constant.order_dishSchema;
                sqlStatement = "INSERT INTO order_dish(bid,fid,number,score) VALUES (?,?,?,?)";
                argc = schema.size();
            }
            case "like_merchant" -> {
                schema = Constant.like_merchantSchema;
                sqlStatement = "INSERT INTO like_merchant(uid,sid) VALUES (?,?)";
                argc = schema.size();
            }
            case "like_dish" -> {
                schema = Constant.like_dishSchema;
                sqlStatement = "INSERT INTO like_dish(uid,fid) VALUES (?,?)";
                argc = schema.size();
            }
            case "message" -> {
                schema = Constant.messageSchema;
                sqlStatement = "INSERT INTO message(uid,is_read,date,time,text) VALUES (?,?,?,?,?)";
                argc = schema.size() - 1;
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
    public boolean addDataByEntry(String table,String[] entry,String[] argv) throws SQLException {
        List<String> schema;
        switch (table) {
            case "merchant" -> schema = Constant.merchantSchema;
            case "user" -> schema = Constant.userSchema;
            case "dish" -> schema = Constant.dishSchema;
            case "orders" -> schema = Constant.ordersSchema;
            case "order_dish" -> schema = Constant.order_dishSchema;
            case "like_merchant" -> schema = Constant.like_merchantSchema;
            case "like_dish" -> schema = Constant.like_dishSchema;
            case "message" -> schema = Constant.messageSchema;
            default -> {
                if (debug) System.err.println("addData err! Table: '" + table + "' do not exist!");
                return false;
            }
        }
        for (int i = 0; i < entry.length; i++) {
            if(!schema.contains(entry[i])){
                if (debug) System.err.println("addData err!");
                return false;
            }
        }
        String line = "INSERT INTO " + table + " (";
        for (int i = 0; i < entry.length; i++) {
            if(i != entry.length - 1)line+=entry[i]+",";
            else line+=entry[i];
        }
        line += ") VALUES (";
        for (int i = 0; i < entry.length; i++) {
            if(i != entry.length - 1)line += "?,";
            else line += "?";
        }
        line += ")";
        PreparedStatement statement = connection.prepareStatement(line);
        for (int i = 0; i < argv.length; i++) {
            statement.setString(i+1,argv[i]);
        }
        if(statement.executeUpdate() >= 1) return true;
        else return false;
    }
    public String getUserIdByStudent_id(String student_id) throws SQLException {
        String line = "SELECT id FROM user WHERE student_id = ?";
        PreparedStatement statement = connection.prepareStatement(line);
        statement.setString(1,student_id);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet.getString("id");
    }
    public String getMerchantIdByPhone_Number(String phone_number) throws SQLException {
        String line = "SELECT id FROM merchant WHERE phone_number = ?";
        PreparedStatement statement = connection.prepareStatement(line);
        statement.setString(1,phone_number);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet.getString("id");
    }
    public boolean userRegister(String name,String gender,String student_id,int age,String job,String password) throws SQLException {
        String[] argv = {name,gender,student_id,Integer.toString(age),job,password};
        return addData("user",argv);
    }
    public boolean merchantRegister(String name,String address,String phone_number,String main_dish,String password) throws SQLException {
        String[] argv = {name,address,phone_number,main_dish,password};
        return addData("merchant",argv);
    }
    public boolean merchantAddDish(int sid,String name,String price,String picture,String sort,String nutrition,String allergen) throws SQLException {
        String[] argv = {Integer.toString(sid),name,price,picture,sort,nutrition,allergen,Integer.toString(0),Integer.toString(0),Integer.toString(0)};
        return addData("dish",argv);
    }
    public boolean merchantChangePrice(int fid,int price) throws SQLException {
        String[] argv = {Integer.toString(fid)};
        return changeData("dish",argv,"price",Integer.toString(price));
    }
    public boolean merchantChangeSort(int fid,String sort) throws SQLException {
        String[] argv = {Integer.toString(fid)};
        return changeData("dish",argv,"sort",sort);
    }
    public ArrayList<String[]> merchantGetOrder(int sid) throws SQLException {
        String line = "SELECT bid,fid,date,time,is_online,name FROM orders JOIN order_dish JOIN dish ON orders.id = order_dish.bid AND order_dish.fid = dish.id WHERE orders.sid = ? AND state = 1 ORDER BY date,time DESC";
        PreparedStatement statement = connection.prepareStatement(line);
        statement.setInt(1,sid);
        ResultSet resultSet = statement.executeQuery();
        String[] schema = {"bid","fid","date","time","is_online","name"};
        return resultSetToList(resultSet,schema);
    }
    public boolean merchantOrderReady(int bid) throws SQLException {
        String[] argv = {Integer.toString(bid)};
        return changeData("orders",argv,"state",Integer.toString(2));
    }
    public ArrayList<String[]> getUserInformation(int id) throws SQLException {
        String line = "SELECT * FROM user WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(line);
        statement.setInt(1,id);
        ResultSet resultSet = statement.executeQuery();
        String[] schema = Constant.userSchema.toArray(new String[0]);
        return resultSetToList(resultSet,schema);
    }
    public ArrayList<String[]> getMerchantInformation(int id) throws SQLException {
        String line = "SELECT * FROM merchant WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(line);
        statement.setInt(1,id);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        String[] result= new String[Constant.merchantSchema.size() + 1];
        for (int i = 0; i < Constant.merchantSchema.size(); i++) {
            result[i] = resultSet.getString(Constant.merchantSchema.get(i));
        }
        result[Constant.merchantSchema.size()] = Float.toString(getMerchantScore(id));
        ArrayList<String[]> list = new ArrayList<>();
        list.add(result);
        return list;
    }
    public ArrayList<String[]> userSearchForMerchant(String information) throws SQLException {//用信息检索商家，返回简略信息
        String line = "SELECT id,name,main_dish FROM merchant WHERE name LIKE ?";
        String str = "%"+ information +"%";
        PreparedStatement statement = connection.prepareStatement(line);
        statement.setString(1,str);
        ResultSet resultSet = statement.executeQuery();
        String[] schema = {"id","name","main_dish"};
        return resultSetToList(resultSet,schema);
    }
    public ArrayList<String[]> showDetailedInformationOfMerchant(int id) throws SQLException {
        String line = "SELECT id,name,address,phone_number,main_dish,password FROM merchant WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(line);
        statement.setInt(1,id);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        String[] result= new String[Constant.merchantSchema.size() + 1];
        for (int i = 0; i < Constant.merchantSchema.size(); i++) {
            result[i] = resultSet.getString(Constant.merchantSchema.get(i));
        }
        result[Constant.merchantSchema.size()] = Float.toString(getMerchantScore(id));
        ArrayList<String[]> list = new ArrayList<>();
        list.add(result);
        return list;
    }
    public ArrayList<String[]> showDetailedInformationOfDish(int id) throws SQLException {
        String line = "SELECT id,sid,name,price,picture,sort,nutrition,allergen,score,total_score,score_count FROM dish WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(line);
        statement.setInt(1,id);
        ResultSet resultSet = statement.executeQuery();
        String[] schema = Constant.dishSchema.toArray(new String[0]);
        return resultSetToList(resultSet,schema);
    }
    public ArrayList<String[]> userSearchForDishInMerchant(int sid,String information) throws SQLException {
        String line = "SELECT id,name,price,picture,sort FROM dish WHERE name LIKE ? AND sid = ? ORDER BY sort";
        String str = "%"+ information +"%";
        PreparedStatement statement = connection.prepareStatement(line);
        statement.setString(1,str);
        statement.setInt(2,sid);
        ResultSet resultSet = statement.executeQuery();
        String[] schema = {"id","name","price","picture","sort"};
        return resultSetToList(resultSet,schema);
    }
    public boolean userLikeDish(int uid,int fid) throws SQLException {
        String[] argv = {Integer.toString(uid),Integer.toString(fid)};
        return addData("like_dish",argv);
    }
    public boolean userLikeMerchant(int uid,int sid) throws SQLException {
        String[] argv = {Integer.toString(uid),Integer.toString(sid)};
        return addData("like_merchant",argv);
    }
    public ArrayList<String[]> userShowLikeDish(int uid) throws SQLException {
        String line = "SELECT id,name,price,picture,sort FROM like_dish JOIN dish ON dish.id = like_dish.fid WHERE like_dish.uid = ?";
        PreparedStatement statement = connection.prepareStatement(line);
        statement.setInt(1,uid);
        ResultSet resultSet = statement.executeQuery();
        String[] schema = {"id","name","price","picture","sort"};
        return resultSetToList(resultSet,schema);
    }
    public ArrayList<String[]> userShowLikeMerchant(int uid) throws SQLException {
        String line = "SELECT id,name,main_dish FROM like_merchant JOIN merchant ON like_merchant.sid = merchant.id WHERE like_merchant.uid = ?";
        PreparedStatement statement = connection.prepareStatement(line);
        statement.setInt(1,uid);
        ResultSet resultSet = statement.executeQuery();
        String[] schema = {"id","name","main_dish"};
        return resultSetToList(resultSet,schema);
    }
    public ArrayList<String[]> userShowMessageUnread(int uid) throws SQLException {
        String line = "SELECT id,date,time,text FROM message WHERE uid = ? AND is_read = 0";
        PreparedStatement statement = connection.prepareStatement(line);
        statement.setInt(1,uid);
        ResultSet resultSet = statement.executeQuery();
        String[] schema = {"id","date","time","text"};
        return resultSetToList(resultSet,schema);
    }
    public boolean userReadMessage(int mid) throws SQLException {
        String[] argv = {Integer.toString(mid)};
        return changeData("message",argv,"is_read","1");
    }
    public ArrayList<String[]> userShowOrder(int uid) throws SQLException {
        String line = "SELECT bid,fid,date,time,is_online,state,name FROM orders JOIN order_dish JOIN dish ON orders.id = order_dish.bid AND order_dish.fid = dish.id WHERE orders.uid = ? ORDER BY date,time DESC";
        PreparedStatement statement = connection.prepareStatement(line);
        statement.setInt(1,uid);
        ResultSet resultSet = statement.executeQuery();
        String[] schema = {"bid","fid","date","time","is_online","state","name"};
        return resultSetToList(resultSet,schema);
    }
    public ArrayList<String[]> userShowUncompletedOrder(int uid) throws SQLException {
        String line = "SELECT bid,fid,date,time,is_online,state,name FROM orders JOIN order_dish JOIN dish ON orders.id = order_dish.bid AND order_dish.fid = dish.id WHERE orders.uid = ? AND not orders.state = 2 ORDER BY date,time DESC";
        PreparedStatement statement = connection.prepareStatement(line);
        statement.setInt(1,uid);
        ResultSet resultSet = statement.executeQuery();
        String[] schema = {"bid","fid","date","time","is_online","state","name"};
        return resultSetToList(resultSet,schema);
    }
    public boolean userConfirmOrder(int bid) throws SQLException {
        String[] argv = {Integer.toString(bid)};
        return changeData("orders",argv,"state",Integer.toString(1));
    }
    public boolean userDeleteOrder(int bid) throws SQLException {
        String[] argv = {Integer.toString(bid)};
        return deleteData("orders",argv);
    }
    public boolean userCommentOnOrder(int bid,String comment) throws SQLException {
        String[] argv = {Integer.toString(bid)};
        return changeData("orders",argv,"comment",comment);
    }
    public boolean userPutScoreOnOrderDish(int bid,int fid,int score) throws SQLException {//score should be [1,5]
        String[] argv = {Integer.toString(bid),Integer.toString(fid)};
        String line = "SELECT * FROM order_dish WHERE bid = ? AND fid = ? AND score = 0";
        PreparedStatement statement = connection.prepareStatement(line);
        statement.setInt(1,bid);
        statement.setInt(2,fid);
        ResultSet resultSet = statement.executeQuery();
        if(!resultSet.next()) return false;
        return changeData("order_dish",argv,"score",Integer.toString(score));
    }
    public boolean userOrderDish(int uid,int sid,int[] fid,int[] num,boolean is_online) throws SQLException {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String[] entry = {"date","time","uid","sid","is_online","state"};
        String[] orders_argv = {date,time,Integer.toString(uid),Integer.toString(sid),Integer.toString(is_online?1:0),Integer.toString(0)};
        addDataByEntry("orders",entry,orders_argv);
        String line = "SELECT max(id) as id FROM orders";
        PreparedStatement statement = connection.prepareStatement(line);
        ResultSet resultSet = statement.executeQuery();
        String[] schema = {"id"};
        ArrayList<String[]>resultList = resultSetToList(resultSet,schema);
        int id = Integer.parseInt(resultList.get(0)[0]);
        for (int i = 0; i < fid.length; i++) {
            String[] order_dish_argv = {Integer.toString(id),Integer.toString(fid[i]),Integer.toString(num[i]),Integer.toString(0)};
            addData("order_dish",order_dish_argv);
        }
        return true;
    }
    public ArrayList<String[]> userShowPriceHistory(int fid) throws SQLException {
        String line = "SELECT date,time,price FROM price_history WHERE price_history.fid = ? ORDER BY date,time DESC";
        PreparedStatement statement = connection.prepareStatement(line);
        statement.setInt(1,fid);
        ResultSet resultSet = statement.executeQuery();
        String[] schema = {"date","time","price"};
        return resultSetToList(resultSet,schema);
    }
    public ArrayList<String[]> showDishOfMerchant(int sid) throws SQLException {
        String line = "SELECT id,name,price,picture,sort FROM dish WHERE sid = ? ORDER BY sort";
        PreparedStatement statement = connection.prepareStatement(line);
        statement.setInt(1,sid);
        ResultSet resultSet = statement.executeQuery();
        String[] schema = {"id","name","price","picture","sort"};
        return resultSetToList(resultSet,schema);
    }
    public ArrayList<String[]> showSortOfMerchant(int sid) throws SQLException {
        String line = "SELECT sort FROM dish WHERE sid = ? GROUP BY sort";
        PreparedStatement statement = connection.prepareStatement(line);
        statement.setInt(1,sid);
        ResultSet resultSet = statement.executeQuery();
        String[] schema = {"sort"};
        return resultSetToList(resultSet,schema);
    }
    private ArrayList<String[]> resultSetToList(ResultSet resultSet,String[] schema) throws SQLException {
        ArrayList<String[]> resultList = new ArrayList<>();
        while (resultSet.next()){
            String[] row = new String[schema.length];
            for (int i = 0; i < schema.length; i++) {
                row[i] = resultSet.getString(schema[i]);
            }
            resultList.add(row);
        }
        return resultList;
    }
    private float getMerchantScore(int id) throws SQLException {
        String line = "SELECT AVG(dish.score) as score FROM dish JOIN merchant ON dish.sid = merchant.id WHERE merchant.id = ? AND dish.score > 0";
        PreparedStatement statement = connection.prepareStatement(line);
        statement.setInt(1,id);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet.getFloat("score");
    }
    public ArrayList<String[]> getMostBuyUserOfDishFromMerchant(int sid) throws SQLException {
        String line = "WITH temp AS(SELECT fid,uid,SUM(order_dish.number) as count FROM order_dish JOIN orders ON order_dish.bid = orders.id WHERE orders.sid = ? GROUP BY fid,orders.uid)" +
                ",temp2 AS(SELECT fid,uid,count FROM temp WHERE count = (SELECT MAX(count) FROM temp WHERE fid = temp.fid)) ";
        line = line + "SELECT DISTINCT dish.id as fid,dish.name as dish_name,user.name as user_name FROM temp2 JOIN dish JOIN user WHERE dish.id = temp2.fid AND user.id = temp2.uid";
        PreparedStatement statement = connection.prepareStatement(line);
        statement.setInt(1,sid);
        ResultSet resultSet = statement.executeQuery();
        String[] schema = {"fid","dish_name","user_name"};
        return resultSetToList(resultSet,schema);
    }
    public ArrayList<String[]> getAvgScoreAndSaleOfDishFromMerchant(int sid) throws SQLException {
        String line = "WITH online_sale_num AS ( SELECT order_dish.fid as fid,sum(order_dish.number) as online_num FROM dish JOIN order_dish JOIN orders ON dish.id = order_dish.fid AND order_dish.bid = orders.id WHERE dish.sid = ? AND orders.is_online = 1 GROUP BY fid)" +
                ",offline_sale_num AS ( SELECT order_dish.fid as fid,sum(order_dish.number) as offline_num FROM dish JOIN order_dish JOIN orders ON dish.id = order_dish.fid AND order_dish.bid = orders.id WHERE dish.sid = ? AND orders.is_online = 0 GROUP BY fid)"+
                ",like_num AS ( SELECT dish.id as fid,count(like_dish.uid) as num FROM dish JOIN like_dish ON dish.id = like_dish.fid WHERE dish.id = ? GROUP BY fid)"+
        "SELECT id,name,price,picture,sort,dish.score as score,online_num,offline_num,num as like_num FROM dish LEFT JOIN online_sale_num ON dish.id = online_sale_num.fid LEFT JOIN offline_sale_num ON dish.id = offline_sale_num.fid LEFT JOIN like_num ON dish.id = like_num.fid WHERE dish.sid = ?";
        PreparedStatement statement = connection.prepareStatement(line);
        statement.setInt(1,sid);
        statement.setInt(2,sid);
        statement.setInt(3,sid);
        statement.setInt(4,sid);
        ResultSet resultSet = statement.executeQuery();
        String[] schema = {"id","name","price","picture","sort","score","online_num","offline_num","like_num"};
        return resultSetToList(resultSet,schema);
    }//收藏量
    public ArrayList<String[]> userGetSaleNumOfDishFromLike(int uid, boolean is_online) throws SQLException {
        String line = "WITH week_sale AS ( SELECT order_dish.fid as fid,sum(order_dish.number) as week_sale_num FROM orders JOIN order_dish ON orders.id = order_dish.bid WHERE TIMESTAMP(date, time) >= NOW() - INTERVAL 1 WEEK AND orders.is_online = ? GROUP BY fid)" +
                ",month_sale AS( SELECT order_dish.fid as fid,sum(order_dish.number) as month_sale_num FROM orders JOIN order_dish ON orders.id = order_dish.bid WHERE TIMESTAMP(date, time) >= NOW() - INTERVAL 1 MONTH AND orders.is_online = ? GROUP BY fid)"+
                ",year_sale AS ( SELECT order_dish.fid as fid,sum(order_dish.number) as year_sale_num FROM orders JOIN order_dish ON orders.id = order_dish.bid WHERE TIMESTAMP(date, time) >= NOW() - INTERVAL 1 YEAR AND orders.is_online = ? GROUP BY fid)"+
                "SELECT dish.id as fid,name,week_sale_num,month_sale_num,year_sale_num FROM dish JOIN like_dish ON dish.id = like_dish.fid LEFT JOIN week_sale ON week_sale.fid = dish.id LEFT JOIN month_sale ON month_sale.fid = dish.id LEFT JOIN year_sale ON year_sale.fid = dish.id WHERE like_dish.uid = ?";
        PreparedStatement statement = connection.prepareStatement(line);
        statement.setInt(1,is_online?1:0);
        statement.setInt(2,is_online?1:0);
        statement.setInt(3,is_online?1:0);
        statement.setInt(4,uid);
        ResultSet resultSet = statement.executeQuery();
        String[] schema = {"fid","name","week_sale_num","month_sale_num","year_sale_num"};
        return resultSetToList(resultSet,schema);
    }
    public ArrayList<String[]> showFansOfMerchant(int sid) throws SQLException {
        String line = "WITH fans AS (SELECT uid,count(id) as num FROM orders WHERE orders.sid = ? GROUP BY uid having count(id) >= ?)"+
                " SELECT DISTINCT id as uid,name,num as order_num FROM user JOIN fans ON user.id = fans.uid";
        PreparedStatement statement = connection.prepareStatement(line);
        statement.setInt(1,sid);
        statement.setInt(2,Constant.INF_ORDER_NUM_OF_FAN);
        ResultSet resultSet = statement.executeQuery();
        String[] schema = {"uid","name","order_num"};
        return resultSetToList(resultSet,schema);
    }
    public ArrayList<String[]> showOrderDishOfUserInMerchant(int sid,int uid,String time) throws SQLException {
        List<String> time_list = Arrays.asList("WEEK","MONTH","YEAR");
        if(!time_list.contains(time)) return null;
        String line = "WITH order_dish_num AS (SELECT fid,sum(number) as num FROM orders JOIN order_dish ON orders.id = order_dish.bid WHERE orders.sid = ? AND orders.uid = ? AND TIMESTAMP(date, time) >= NOW() - INTERVAL 1 " + time + " GROUP BY fid)" +
                " SELECT fid,name as dish_name,num as dish_num FROM dish JOIN order_dish_num ON dish.id = order_dish_num.fid WHERE dish.sid = ?";
        PreparedStatement statement = connection.prepareStatement(line);
        statement.setInt(1,sid);
        statement.setInt(2,uid);
        statement.setInt(3,sid);
        ResultSet resultSet = statement.executeQuery();
        String[] schema = {"fid","dish_name","dish_num"};
        return resultSetToList(resultSet,schema);
    }
    public ArrayList<String[]> showCommentOnMerchant(int sid) throws SQLException {
        String line = "SELECT id as bid,date,time,comment FROM orders WHERE orders.sid = ? AND orders.comment IS NOT null ORDER BY date,time DESC";
        PreparedStatement statement = connection.prepareStatement(line);
        statement.setInt(1,sid);
        ResultSet resultSet = statement.executeQuery();
        String[] schema = {"bid","date","time","comment"};
        return resultSetToList(resultSet,schema);
    }
    public ArrayList<String[]> showOrderNumOfTime(String time) throws SQLException {
        int day_num;
        switch (time){
            case "DAY"->day_num = 1;
            case "WEEK"->day_num = 7;
            case "MONTH"->day_num = 30;
            case "YEAR"->day_num = 365;
            default -> {
                return null;
            }
        }
        String line = "SELECT SUM(CASE WHEN DATEDIFF(CURDATE(), date) <= ? THEN 1 ELSE 0 END) AS within_1_"+time+"," +
                "SUM(CASE WHEN DATEDIFF(CURDATE(), date) > ? AND DATEDIFF(CURDATE(), date) <= ? THEN 1 ELSE 0 END) AS between_1_and_2_"+time+"," +
                "SUM(CASE WHEN DATEDIFF(CURDATE(), date) > ? AND DATEDIFF(CURDATE(), date) <= ? THEN 1 ELSE 0 END) AS between_2_and_3_"+time+"," +
                "SUM(CASE WHEN DATEDIFF(CURDATE(), date) > ? AND DATEDIFF(CURDATE(), date) <= ? THEN 1 ELSE 0 END) AS between_3_and_4_"+time+"," +
                "SUM(CASE WHEN DATEDIFF(CURDATE(), date) > ? AND DATEDIFF(CURDATE(), date) <= ? THEN 1 ELSE 0 END) AS between_4_and_5_"+time+" " +
                "FROM orders";
        PreparedStatement statement = connection.prepareStatement(line);
        statement.setInt(1,day_num);
        statement.setInt(2,day_num);
        statement.setInt(3,day_num*2);
        statement.setInt(4,day_num*2);
        statement.setInt(5,day_num*3);
        statement.setInt(6,day_num*3);
        statement.setInt(7,day_num*4);
        statement.setInt(8,day_num*4);
        statement.setInt(9,day_num*5);
        ResultSet resultSet = statement.executeQuery();
        String[] schema = {"within_1_" + time,"between_1_and_2_" + time,"between_2_and_3_" + time,"between_3_and_4_" + time,"between_4_and_5_" + time};
        return resultSetToList(resultSet,schema);
    }
    public ArrayList<String[]> showOrderNumOfDayTime() throws SQLException {
        String line = "SELECT SUM(CASE WHEN TIME(time) >= '06:00:00' AND TIME(time) < '12:00:00' THEN 1 ELSE 0 END) AS morning," +
                "SUM(CASE WHEN TIME(time) >= '12:00:00' AND TIME(time) < '18:00:00' THEN 1 ELSE 0 END) AS afternoon," +
                "SUM(CASE WHEN TIME(time) >= '18:00:00' AND TIME(time) < '24:00:00' THEN 1 ELSE 0 END) AS evening," +
                "SUM(CASE WHEN TIME(time) >= '00:00:00' AND TIME(time) < '06:00:00' THEN 1 ELSE 0 END) AS night " +
                "FROM orders";
        PreparedStatement statement = connection.prepareStatement(line);
        ResultSet resultSet = statement.executeQuery();
        String[] schema = {"morning","afternoon","evening","night"};
        return resultSetToList(resultSet,schema);
    }
    public ArrayList<String[]> showOrderNumOfFeatureGroup(String entry,String value) throws SQLException {
        if(!Constant.userSchema.contains(entry)){
            if(debug)System.err.println("in showOrderNumOfFeatureGroup(),user do not have" + entry);
            return null;
        }
        String line = "WITH featured_user AS ( SELECT * FROM user WHERE "+entry+" = ?) ";
        line += "SELECT sid,merchant.name as merchant_name,count(orders.id) as order_num FROM featured_user JOIN orders ON featured_user.id = orders.uid JOIN merchant ON orders.sid = merchant.id GROUP BY sid,merchant_name";
        PreparedStatement statement = connection.prepareStatement(line);
        statement.setString(1,value);
        ResultSet resultSet = statement.executeQuery();
        String[] schema = {"sid","merchant_name","order_num"};
        return resultSetToList(resultSet,schema);
    }
    public ArrayList<String[]> showOrderNumOfFeatureGroupOfDishFromMerchant(int sid,String entry,String value) throws SQLException {
        if(!Constant.userSchema.contains(entry)){
            if(debug)System.err.println("in showOrderNumOfFeatureGroupOfDishFromMerchant(),user do not have" + entry);
            return null;
        }
        String line = "WITH featured_user AS ( SELECT * FROM user WHERE "+entry+" = ?) ";
        line += "SELECT order_dish.fid as fid,dish.name as dish_name,sum(order_dish.number) as order_num FROM featured_user JOIN orders ON featured_user.id = orders.uid JOIN order_dish ON orders.id = order_dish.bid JOIN dish ON dish.id = order_dish.fid WHERE orders.sid = ? AND dish.sid = ? GROUP BY fid,dish_name";
        PreparedStatement statement = connection.prepareStatement(line);
        statement.setString(1,value);
        statement.setInt(2,sid);
        statement.setInt(3,sid);
        ResultSet resultSet = statement.executeQuery();
        String[] schema = {"fid","dish_name","order_num"};
        return resultSetToList(resultSet,schema);
    }
    public ArrayList<String[]> showCommentNumOfFeatureGroup(String entry,String value) throws SQLException {
        if(!Constant.userSchema.contains(entry)){
            if(debug)System.err.println("in showCommentNumOfFeatureGroup(),user do not have" + entry);
            return null;
        }
        String line = "WITH featured_user AS ( SELECT * FROM user WHERE "+entry+" = ?) " +
                "SELECT SUM(CASE WHEN comment IS NOT NULL THEN 1 ELSE 0 END) AS comment_count," +
                "count(orders.id) AS order_count "+
                "FROM orders JOIN featured_user ON orders.uid = featured_user.id";
        PreparedStatement statement = connection.prepareStatement(line);
        statement.setString(1,value);
        ResultSet resultSet = statement.executeQuery();
        String[] schema = {"comment_count","order_count"};
        return resultSetToList(resultSet,schema);
    }
    public ArrayList<String[]> showScoreNumOfFeatureGroup(String entry,String value) throws SQLException {
        if(!Constant.userSchema.contains(entry)){
            if(debug)System.err.println("in showScoreNumOfFeatureGroup(),user do not have" + entry);
            return null;
        }
        String line = "WITH featured_user AS ( SELECT * FROM user WHERE "+entry+" = ?) " +
                "SELECT SUM(CASE WHEN order_dish.score = 0 THEN 1 ELSE 0 END) AS no_score_count," +
                "SUM(CASE WHEN order_dish.score = 1 THEN 1 ELSE 0 END) AS 1_count," +
                "SUM(CASE WHEN order_dish.score = 2 THEN 1 ELSE 0 END) AS 2_count," +
                "SUM(CASE WHEN order_dish.score = 3 THEN 1 ELSE 0 END) AS 3_count," +
                "SUM(CASE WHEN order_dish.score = 4 THEN 1 ELSE 0 END) AS 4_count," +
                "SUM(CASE WHEN order_dish.score = 5 THEN 1 ELSE 0 END) AS 5_count," +
                "count(*) AS total_count "+
                "FROM orders JOIN featured_user ON orders.uid = featured_user.id JOIN order_dish ON orders.id = order_dish.bid";
        PreparedStatement statement = connection.prepareStatement(line);
        statement.setString(1,value);
        ResultSet resultSet = statement.executeQuery();
        String[] schema = {"no_score_count","1_count","2_count","3_count","4_count","5_count","total_count"};
        return resultSetToList(resultSet,schema);
    }
    public ArrayList<String[]> showDishSaleNumOver(int num) throws SQLException {
        String line = "SELECT dish.id as fid,sid,dish.name as dish_name,sum(order_dish.number) as sale_num FROM dish JOIN order_dish ON dish.id = order_dish.fid GROUP BY fid,sid,dish_name HAVING sale_num >= ? ORDER BY sale_num DESC";
        PreparedStatement statement = connection.prepareStatement(line);
        statement.setInt(1,num);
        ResultSet resultSet = statement.executeQuery();
        String[] schema = {"fid","sid","dish_name","sale_num"};
        return resultSetToList(resultSet,schema);
    }
    public ArrayList<String[]> showMostOftenChangePriceMerchant(int change_num) throws SQLException {
        String line =  "SELECT merchant.id as sid,merchant.name as merchant_name,count(*) as change_price_count FROM merchant JOIN dish ON dish.sid = merchant.id JOIN price_history ON dish.id = price_history.fid WHERE DATEDIFF(CURDATE(), date) <= 30 GROUP BY sid,merchant_name HAVING change_price_count > ? ORDER BY change_price_count DESC";
        PreparedStatement statement = connection.prepareStatement(line);
        statement.setInt(1,change_num);
        ResultSet resultSet = statement.executeQuery();
        String[] schema = {"sid","merchant_name","change_price_count"};
        return resultSetToList(resultSet,schema);
    }
}
