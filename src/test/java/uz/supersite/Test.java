package uz.supersite;

import java.util.Arrays;

public class Test {
    public Object[] arguments;
    public Test(final Object[] args){
        arguments = args;
    }

    public  void swapValues(){
         arguments = new Object[]{arguments[0], arguments[1]};
        Object temp = arguments[0];
        arguments[0]=arguments[1];
        arguments[1]=temp;
    }

    public static void main(String[] args) {
        int x = 0;
        int y = 30;

        for (int outer = 0; outer<3; outer++){
            for (int inner = 4; inner>1;inner--){
                x=x+0;
                y=y-2;
                if (x==6){
                    break;
                }
                x=x+3;
            }
            y=y-2;
        }

        System.out.println(x+" "+ y);
//        System.out.println();


//        System.out.println(Arrays.toString(arrSplit("Robin Singh")));
////        well1(new String[] {"good", "bad", "bad", "bad", "bad"});
//        System.out.println(well1(new String[] {"good", "good", "bad", "bad", "bad"}));
//        System.out.println(position('T'));
//
////        char lowerCaseLetter = Character.toLowerCase('a');
//        System.out.println('v' - 'a' + 1);
    }
    public static int strCount(String str, char letter) {
        int cnt = 0;
        for(int i = 0; i < str.length(); i++){
            if (str.charAt(i) == letter){
                cnt++;
            }
        }
        return cnt;
    }
    public static String position(char alphabet){
        char lowerCaseLetter = Character.toLowerCase(alphabet);

        int position = lowerCaseLetter - 'a' + 1;

        return "Position of alphabet: " + position;
    }
    public static String well1(String[] x){
        int cnt=0;
        for (String s : x) {
            if (s.equals("good")) {
                cnt++;
            }
        }
        if(cnt == 0){
            return "Fail";
        }else if(cnt == 1 || cnt == 2){
            return "Publish";
        }else if(cnt > 2){
            return "I smeel a series!";
        }
        return null;
    }
    public static String[] arrSplit(String str){
        int x = 0;
        String word;
//        str.append(" ");
        str += " ";
        int cnt = 0;
        int index = 0;

        for (int i = 0; i < str.length(); i++){
            if (str.charAt(i)==' '){
                cnt += 1;
            }
        }

        String [] myWords = new String[cnt];

        for (int i = 0; i < str.length(); i++){
            if (str.charAt(i)==' '){
                word = str.substring(x,i);
                x = i+1;
                myWords[index] = word;
                index += 1;
            }
        }

        return myWords;
    }
}