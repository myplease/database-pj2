package Data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Initial {
    public static void initialMerchant() throws IOException {
        //name address phone_number main_dish password
        String line;
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/files/data/merchant_initial_data.txt"));
        for (int i = 0; i < Constant.initialMerchantNumber; i++) {
            line = "merchant_name" + i +
                    " address" + i +
                    " phone_number" + i +
                    " main_dish" + i +
                    " password" + i ;
            if(i != Constant.initialMerchantNumber - 1) line += "\n";
            writer.write(line);
        }
        writer.close();
    }
    public static void initialUser() throws IOException {
        //name gender student_number age job password
        String line;
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/files/data/user_initial_data.txt"));
        for (int i = 0; i < Constant.initialUserNumber; i++) {
            line = "user_name" + i +
                    " male" +
                    " student_number" + i +
                    " 20" +
                    " student" +
                    " password" + i ;
            if(i != Constant.initialUserNumber - 1) line += "\n";
            writer.write(line);
        }
        writer.close();
    }
    public static void initialDish() throws IOException {
        //sid dish_name price picture sort nutrition allergen score total_score score_count
        String line;
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/files/data/dish_initial_data.txt"));
        for (int i = 0; i < Constant.initialMerchantNumber; i++) {
            for (int j = 0; j < Constant.initialDishNumberEachMerchant; j++) {
                line = "" + (i+1) +
                        " dish_name"+(i+1)+"_"+(j+1)+
                        " 10" +
                        " picture"+(i+1)+"_"+(j+1)+
                        " sort" + (j % Constant.initialSortNumber) +
                        " nutrition"+(i+1)+"_"+(j+1)+
                        " allergen"+(i+1)+"_"+(j+1)+
                        " 0" + " 0" + " 0";
                if (i != Constant.initialMerchantNumber - 1 || j != Constant.initialDishNumberEachMerchant - 1) line += "\n";
                writer.write(line);
            }
        }
        writer.close();
    }
    public static void initialOrders() throws IOException {
        //date time uid sid is_online state
        String line;
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/files/data/orders_initial_data.txt"));
        for (int i = 0; i < Constant.initialUserNumber; i++) {
            for (int j = 0; j < Constant.initialOrderNumberEachUser; j++) {
                int I = i+1;
                int J = j+1;
                String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                line = date + " " + time +
                        " " + I +
                        " " + getMerchantIdByUid(i*j + j + 1) +
                        " 0" +
                        " 0";
                if (i != Constant.initialUserNumber - 1 || j != Constant.initialOrderNumberEachUser - 1) line += "\n";
                writer.write(line);
            }
        }
        writer.close();
    }
    public static void initialLikeDish() throws IOException {
        //uid fid
        String line;
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/files/data/like_dish_initial_data.txt"));
        for (int i = 0; i < Constant.initialUserNumber; i++) {
            for (int j = 0; j < Constant.initialLikeDishNumberEachUser && j < Constant.initialDishNumberEachMerchant * Constant.initialMerchantNumber; j++) {
                int I = i+1;
                int J = j+1;
                line = "" + I +
                        " " + (j + 1);
                if (i != Constant.initialUserNumber - 1 || j != Constant.initialLikeDishNumberEachUser - 1) line += "\n";
                writer.write(line);
            }
        }
        writer.close();
    }
    public static void initialLikeMerchant() throws IOException {
        String line;
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/files/data/like_merchant_initial_data.txt"));
        for (int i = 0; i < Constant.initialUserNumber; i++) {
            for (int j = 0; j < Constant.initialLikeMerchantNumberEachUser && j < Constant.initialMerchantNumber; j++) {
                int I = i+1;
                int J = j+1;
                line = "" + I +
                        " " + (j+1);
                if (i != Constant.initialUserNumber - 1 || j != Constant.initialLikeMerchantNumberEachUser - 1) line += "\n";
                writer.write(line);
            }
        }
        writer.close();
    }
    public static void initialOrderDish() throws IOException {
        String line;
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/files/data/order_dish_initial_data.txt"));
        for (int i = 0; i < Constant.initialUserNumber*Constant.initialOrderNumberEachUser; i++) {
            for (int j = 0; j < Constant.initialDishNumberEachOrder; j++) {
                int bid = i+1;
                int fid = getDishIdBySid(getMerchantIdByUid(bid),j);
                line = "" + bid +
                        " " + fid + " 1" + " 0";
                if (i != Constant.initialUserNumber*Constant.initialOrderNumberEachUser - 1 || j != Constant.initialDishNumberEachOrder - 1) line += "\n";
                writer.write(line);
            }
        }
        writer.close();
    }
    public static void initialMessage() throws IOException {
        String line;
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/files/data/message_initial_data.txt"));
        for (int i = 0; i < Constant.initialUserNumber; i++) {
            for (int j = 0; j < Constant.initialMessageEachUser; j++) {
                int uid = i + 1;
                String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                line = "" + uid + " 0" + " " + date + " " + time + " WELCOME!";
                if (i != Constant.initialUserNumber - 1 || j != Constant.initialMessageEachUser - 1) line += "\n";
                writer.write(line);
            }
        }
        writer.close();
    }
    private static int getMerchantIdByUid(int bid){
        return bid % Constant.initialMerchantNumber + 1;
    }
    private static int getDishIdBySid(int sid,int index){
        index %= Constant.initialDishNumberEachMerchant;
        return (sid - 1) * Constant.initialDishNumberEachMerchant + index + 1;
    }
}
