import java.util.Scanner;
public class eMail {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String str = in.next();
        if(str.charAt(0) =='@'||str.charAt(str.length() - 1) =='@')
        {
            System.out.println("No solution");
            return;
        }
        String strMas[] = str.split("@");
        if(strMas.length == 1)
        {
            System.out.println("No solution");
            return;
        }
        StringBuilder str1 = new StringBuilder(strMas[0]);
        for(int i = 1;i < strMas.length - 1; i++){
            if(strMas[i].length()<= 1)
            {
                System.out.println("No solution");
                return;
            }
            str1.append("@");
            str1.append(strMas[i].substring(0,1));
            str1.append(",");
            str1.append(strMas[i].substring(1));
        }
        str1.append("@");
        str1.append(strMas[strMas.length - 1]);
        System.out.println(str1.toString());
    }
}