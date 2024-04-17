package uz.supersite;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PrintRegion {
        private static final String url = "jdbc:oracle:thin:@10.10.11.161:1521:OSAGO";
        private static final String username = "osago";
        private static final String password = "uzb";

        public static void main(String[] args) {
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection connection = DriverManager.getConnection(url, username, password);
                System.out.println("Connected to the Oracle Test Database");

                ObjectMapper objectMapper = new ObjectMapper();
                File jsonFile = new File("C:\\Users\\Administrator\\Desktop\\INSON\\Mulk\\real_data_ru.json");

                List<Map<String, String>> jsonData = objectMapper.readValue(jsonFile, new TypeReference<>() {});
//                String getPolisInfo = "INSERT INTO SP_PROP_REGION (SP_ID, SP_NAME_UZ, SP_NAME_RU, SP_NAME_EN, SP_NAME_UZ_KR, CENTRE_UZ, CENTRE_RU, CENTRE_EN, CENTRE_UZ_KR, SP_CREATED_DATE) VALUES (?, ?, ?, ?,?, ?, ?, ?, ?, sysdate)";
                String getPolisInfo = "UPDATE SP_PROP_REGION SET SP_NAME_RU = ?, CENTRE_RU = ?, SP_CREATED_DATE = sysdate WHERE SP_ID IN (1714)";

                PreparedStatement statement = connection.prepareStatement(getPolisInfo);
                for (Map<String, String> record : jsonData){
//                    String mhobt = record.get("MHOBT");
                    String mhobt7 = record.get("MHOBT7");
                    String NAME = record.get("NAME");
                    String CENTRE = record.get("CENTRE");

                    if("1714".equals(mhobt7)) {

//                        statement.setString(1, mhobt7); // Assuming MHOBT should be SP_ID
//                        statement.setString(2, NAME); // Assuming NAME should be SP_NAME_RU
//                        statement.setString(3, null);
//                        statement.setString(4, null);
//                        statement.setString(5, null);
//                        statement.setString(6, CENTRE);
//                        statement.setString(7, null);
//                        statement.setString(8, null);
//                        statement.setString(9, null);
                        statement.setString(1, NAME); // Assuming NAME should be SP_NAME_RU
                        statement.setString(2, CENTRE);
                        statement.executeUpdate();
                    }
                }

                DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.ss");
                Date date = new Date();
                String dateStr = dateFormat.format(date);
                System.out.println("Data inserted successfully" + "\nPrinted date: " + dateStr);
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}
