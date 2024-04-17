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

public class PrintDistrict {

        private static final String url = "jdbc:oracle:thin:@10.10.11.161:1521:OSAGO";
        private static final String username = "osago";
        private static final String password = "uzb";

        public static void main(String[] args) {
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection connection = DriverManager.getConnection(url, username, password);
                System.out.println("Connected to the Oracle Test Database");

                ObjectMapper objectMapper = new ObjectMapper();
                File jsonFile = new File("C:\\Users\\Clone\\Documents\\real_data_kr.json");

                List<Map<String, String>> jsonData = objectMapper.readValue(jsonFile, new TypeReference<>() {});

//                String getPolisInfo = "INSERT INTO SP_PROP_DISTRICT (SP_ID, SP_NAME_UZ, CENTRE_UZ, SP_CODE,SP_CREATED_DATE) VALUES (?, ?, ?, ?, sysdate)";
                String getPolisInfo = "UPDATE SP_PROP_DISTRICT SET SP_ID = ?, " +
                        "SP_NAME_UZ_KR = ?, CENTRE_RU = ?, " +
                        "SP_CREATED_DATE = sysdate WHERE SP_ID = 1726262";
//                String getPolisInfo = "INSERT INTO SP_PROP_DISTRICT (SP_ID, SP_NAME_UZ, SP_NAME_RU, SP_NAME_UZ_KR, SP_NAME_EN, CENTRE_UZ, CENTRE_RU, CENTRE_UZ_KR, CENTRE_EN, SP_CODE,SP_CREATED_DATE) VALUES (?, ?, ?, ?,?, ?, ?, ?,?,?, sysdate)";

                PreparedStatement statement = connection.prepareStatement(getPolisInfo);

                for (Map<String, String> record : jsonData){
                    String mhobt = record.get("MHOBT");
                    String mhobt7 = record.get("MHOBT7");
                    String NAME = record.get("NAME");
                    String CENTRE = record.get("CENTRE");

                    if("1726262".equals(mhobt)) {
                        statement.setString(1, mhobt);
                        statement.setString(2, NAME);
//                        statement.setString(3, null);
//                        statement.setString(4, null);
//                        statement.setString(5, null);
                        statement.setString(3, CENTRE);
//                        statement.setString(7, null);
//                        statement.setString(8, null);
//                        statement.setString(9, null);
//                        statement.setString(10, String.valueOf(1726));
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
