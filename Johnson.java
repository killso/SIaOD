import javax.swing.*;
import java.util.InputMismatchException;
import java.util.Scanner;
public class Johnson {
    public int s;
    public int n_uzlov;
    public int matrix[][];
    public int mas[];
    public int[][] shortMas;
    public static final int MAX_VALUE = 999;
    public BellmanFord bellmanFord;
    public DijkstraShortesPath dijsktraShortesPath;
    public static void main(String... arg) {
        int n_vershin;
        int matrix[][];
        Scanner scan = new Scanner(System.in);
        try {
            System.out.println("Введите количество вершин: ");
            n_vershin= scan.nextInt();
            matrix = new int[n_vershin+ 1][n_vershin + 1];

            System.out.println("Введите матрицу для графика: ");
            for (int i=1; i<=n_vershin; i++) {
                for (int j=1 ; j<=n_vershin; j++) {
                    matrix[i][j] = scan.nextInt();
                    if (i==j) {
                        matrix[i][j] = 0;
                        continue;
                    }
                    if (matrix[i][j] == 0) {
                        matrix[i][j] = MAX_VALUE;
                    }
                }
            }
            Draw win = new Draw(matrix);
            long one = System.nanoTime();
            Johnson johnsons1 = new Johnson(n_vershin);
            johnsons1.Johnson1(matrix);
            long end = System.nanoTime();
            long value = end - one;
            System.out.println("Время выполнения: " + (value/1000000L) + " мс");
            win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            win.setSize(1000, 1000);
            win.setVisible(true);
        } catch (InputMismatchException inputMismatch) {
            System.out.println("Неверный формат ввода");
        }
        scan.close();
    }
    public Johnson(int n) {
        this.n_uzlov = n;
        matrix = new int[n + 2][n + 2];
        s=n+1;
        mas = new int[n + 2];
        bellmanFord = new BellmanFord(n + 1);
        dijsktraShortesPath = new DijkstraShortesPath(n);
        shortMas = new int[n + 1][n + 1];
    }
    public void Johnson1(int matRash[][]) {
        dopGraph(matRash);
        bellmanFord.BellmanFord2(s, matrix);
        mas = bellmanFord.getDistance();
        int graph[][]=graph(matRash);
        for (int k0= 1;k0 <= n_uzlov;k0++) {
            dijsktraShortesPath.dijkstraShortestPath(k0, graph);
            int[] res = dijsktraShortesPath.Distance();
            for (int end0 = 1; end0<= n_uzlov; end0++) {
                shortMas[k0][end0] = res[end0]
                        + mas[end0] - mas[k0];
            }
        }
        System.out.println();
        System.out.println("Получившаяся матрица кратчаших путей: ");
        for (int i=1; i<=n_uzlov; i++) {
            System.out.print("\t" + i);
        }
        System.out.println();
        for (int k = 1; k<= n_uzlov; k++) {
            System.out.print(k + "\t");
            for (int end = 1; end <= n_uzlov;end++) {
                System.out.print(shortMas[k][end] + "\t");
            }
            System.out.println();
        }
    }
    public void dopGraph (int matRash[][]) {
        for (int k1 = 1; k1 <= n_uzlov; k1++) {
            for (int end1 = 1; end1 <= n_uzlov;end1++) {
                matrix[k1][end1] = matRash[k1][end1];
            }
        }
        for (int end2 = 1; end2 <= n_uzlov;end2++) {
            matrix[s][end2] = 0;
        }
    }

    public int[][] graph(int matRash[][]) {
        int[][] res = new int[n_uzlov + 1][n_uzlov + 1];
        for (int k2 = 1;k2 <= n_uzlov;k2++) {
            for (int end3 = 1;end3 <= n_uzlov;end3++) {
                res[k2][end3]=matRash[k2][end3]
                        + mas[k2] - mas[end3];
            }
        }
        return res;
    }
    class BellmanFord {
        public static final int MAX_VALUE = 999;
        public int S[]; //расстояния
        public int n_ver; //количество вершин
        public BellmanFord(int nVer) {
            this.n_ver = nVer;
            S = new int[nVer + 1];
        }
        public void BellmanFord2(int a, int matRash[][]) {
            for (int uz = 1; uz <= n_ver; uz++) {
                S[uz] = MAX_VALUE;
            }
            S[a] = 0;
            for (int uz= 1;uz <=(n_ver - 1);uz++) {
                for (int uz1= 1;uz1 <=n_ver; uz1++) {
                    for (int endUz = 1; endUz <=n_ver; endUz++) {
                        if (matRash[uz1][endUz] != MAX_VALUE) {
                            if (S[endUz] >S [uz1]
                                    + matRash[uz1][endUz]) {
                                S[endUz] = S[uz1]
                                        + matRash[uz1][endUz];
                            }
                        }
                    }
                }
            }
            for (int kUz= 1;kUz <=n_ver;kUz++) {
                for (int endUz1 = 1; endUz1 <=n_ver;endUz1++) {
                    if (matRash[kUz][endUz1] != MAX_VALUE) {
                        if (S[endUz1] > S[kUz]
                                +matRash[kUz][endUz1])
                            System.out.println("График содержит отрицательный цикл ");
                    }
                }
            }
        }

        public int[] getDistance() {
            return S;
        }
    }

    class DijkstraShortesPath {
        public static final int MAX_VALUE = 999;
        public boolean yn[];
        public boolean ynNO[];
        public int distance[];
        public int matrix[][];
        public int n_ver;
        public DijkstraShortesPath(int nVer) {
            this.n_ver=nVer;
        }

        public void dijkstraShortestPath(int s, int matRash[][]) {
            int evUz = 0;
            this.yn = new boolean[n_ver + 1];
            this.ynNO = new boolean[n_ver + 1];
            this.distance= new int[n_ver+ 1];
            this.matrix = new int[n_ver + 1][n_ver+ 1];
            for (int top = 1;top <=n_ver;top++) {
                distance[top] = MAX_VALUE;
            }
            for (int sTop = 1; sTop <=n_ver; sTop++) {
                for (int lengthTop = 1; lengthTop <n_ver;lengthTop++) {
                    this.matrix[sTop][lengthTop]
                            =matRash[sTop][lengthTop];
                }
            }
            ynNO[s] = true;
            distance[s] = 0;
            while (UnsettledGraph(ynNO) != 0) {
               evUz =MinimumDistance(ynNO);
                ynNO[evUz] = false;
                yn[evUz] = true;
              Neighbours(evUz);
            }
        }

        public int UnsettledGraph(boolean ynNO1[]) {
            int graph = 0;
            for (int top = 1; top <=n_ver;top++) {
                if (ynNO1[top] == true) {
                    graph++;
                }
            }
            return graph;
        }

        public int MinimumDistance(boolean unsettled[]) {
            int min = MAX_VALUE;
            int uz = 0;
            for (int top= 1; top<=n_ver;top++) {
                if (unsettled[top] == true && distance[top]<min) {
                    uz =top;
                    min = distance[top];
                }
            }
            return uz;
        }
        public void Neighbours(int evUz) {
            int edgeDis = -1;
            int newDis = -1;

            for (int lengthUz = 1;lengthUz <=n_ver;lengthUz++) {
                if (yn[lengthUz] == false) {
                    if (matrix[evUz][lengthUz] != MAX_VALUE) {
                        edgeDis =matrix[evUz][lengthUz];
                        newDis = distance[evUz] + edgeDis;
                        if (newDis < distance[lengthUz]) {
                            distance[lengthUz] = newDis;
                        }
                        ynNO[lengthUz] = true;
                    }
                }
            }
        }

        public int[] Distance() {
            return distance;
        }
    }
}