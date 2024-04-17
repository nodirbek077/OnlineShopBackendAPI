package uz.supersite;

import java.util.NavigableSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainExample {
        public static void main(String[] args) {


            String text = "00646IBROXIMOV ISLOMJON MUSURMON OGLI ПНФЛ 30912965450060 СУГУРТА УЧУН ТУЛОВ POLIS: INB 13448#";

            // Regex pattern
            String regexPattern = "INB\\s*[:№\\s]\\s*(\\d+)";

            // Creating a Pattern object
            Pattern pattern = Pattern.compile(regexPattern);

            // Creating a Matcher object
            Matcher matcher = pattern.matcher(text);

            // Find the match
            if (matcher.find()) {
                // Extracting the matched values
                String inbValue = matcher.group(1);

                // Printing the results
                System.out.println("INB " + inbValue);
            } else {
                System.out.println("No match found.");
            }
    }
}
