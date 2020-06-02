import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;
public class Main {
    public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int n = 0;
    int k = 0;
    int con = 0;
    System.out.println("Введите размер исходного массива: ");
    n = in.nextInt();
    System.out.println("Введите входной предел: ");
    con = in.nextInt();
    int[] mas = new int[n];
    Random random = new Random();
    for (int i=0;i<n;i++) {
        mas[i] = random.nextInt(con);
    }
    Arrays.sort(mas);
    if (mas.length<=31) {
        for (int i=0;i<n;i++) {
            System.out.print(mas[i] + " ");
        }
    }
    else
        System.out.println("Слишком длинный массив");
    System.out.println();
    System.out.println("Введите новое значение для массива: ");
    int newVal = in.nextInt();
    int[] mas1 = back(mas, n, newVal);
    Arrays.sort(mas1);
    if (mas1.length < 30) {
        for (int i=0;i<mas1.length;i++) {
            System.out.print(mas1[i] + " ");
        }
    }
    System.out.println();
    System.out.println("Введите номер нужного элемента: ");
    k = in.nextInt();
    double before = System.nanoTime();
    int position = Interpolation.InterpolationSearch(mas1, k);
    double after = System.nanoTime();
    System.out.println();
    System.out.println("Время интерполяционного поиска: " + (after - before) + "нс");
    before = System.nanoTime();
    int position1 = Arrays.binarySearch(mas1, k);
    after = System.nanoTime();
    System.out.println();
    System.out.println("Время интегрированного поиска: " + (after - before) + "нс");
    if (position == -1) {
        System.out.println("Такого элемента не существует");
    }
    else {
        System.out.println("Элемент существует на " + position + " позиции");
        System.out.println("Удалять этот элемент?" + "\n" + "1 - да или 0 - нет");
        int d = 0;
        d = in.nextInt();
        System.out.println();
        if (d == 1)
        {
            delete(mas1, position);
            System.out.println();
            System.out.println("Элемент удален в " + position + " позиции");
        }
    }
}
    public static void delete (int[] mas, int ind) {
        for (int i=ind;i<mas.length-1;i++)
            mas[i] = mas[i + 1];
        mas[mas.length-1] = 0;
        if (mas.length<=31) {
            for (int i=0;i<mas.length-1;i++)
                System.out.print(mas[i] + " ");
        }
    }
    public static int[] back(int[] mas, int size, int sence) {
        int[] mas1=new int[size+1];
        for (int i=0;i<size;i++)
            mas1[i] = mas[i];
        mas1[size] =sence;
        return mas1;
    }
}
