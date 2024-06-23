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
                    " 00000000000" +
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
                line = date + time +
                        " " + I +
                        " " + ((i*j % Constant.initialMerchantNumber)+1) +
                        " 0" +
                        " 0";
                if (i != Constant.initialMerchantNumber - 1 || j != Constant.initialOrderNumberEachUser - 1) line += "\n";
                writer.write(line);
            }
        }
        writer.close();
    }
    public static void initialLikeDish() throws IOException {
        String line;
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/files/data/like_dish_initial_data.txt"));
        line = "1 1\n" +
                "1 2\n" +
                "1 3\n" +
                "1 5\n" +
                "2 1\n" +
                "2 2";
        writer.write(line);
        writer.close();
    }
    public static void initialLikeMerchant() throws IOException {
        String line;
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/files/data/merchant_initial_data.txt"));
        line = "1 1\n" +
                "2 1";
        writer.write(line);
        writer.close();
    }
    public static void initialOrderDish() throws IOException {
        String line;
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/files/data/order_dish_initial_data.txt"));
        line = "1 1 1 0\n" +
                "2 1 10 0\n" +
                "3 2 1 0";
        writer.write(line);
        writer.close();
    }
    public static void initialMessage() throws IOException {
        String line;
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/files/data/message_initial_data.txt"));
        line = "1 0 2024-06-17 03:42:50 message_text0";
        writer.write(line);
        writer.close();
    }
}
