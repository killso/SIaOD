import java.util.Scanner;
import java.util.Arrays;
public class Kangaroo {
    public static int[] input = new int[500000]; //5*10^5
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int a = scan.nextInt();
        int kan = a;
        for (int i = 0; i < a; i++) {
            input[i] = scan.nextInt();
        }
        Arrays.sort(input , 0 , a);
        int child = 0;
        int  mom = a / 2;
        while (child< a / 2 && mom < a) {
            if (input[child] * 2 <= input[mom]) {
                kan--;
                child++;
                mom++;
            } else {
                mom++;
            }
        }
        System.out.println(kan);
    }
}