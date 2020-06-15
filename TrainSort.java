package Labs;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
public class TrainSort {

    public TrainSort(int trains) {
         public TrainSort( int trains)
        {
            tunnel = new LinkedList<>();
            count = trains * 2;
            create(count);
            shake();
        }
    public TrainSort(String path)
    throws IOException
        {
            tunnel = new LinkedList<>();
            String line = IO.getText(path).get(0);
            //parseAndCreate("a1 b2 b3 b4 a3 b1 a2 a4");
            parseAndCreate(line);
            count = tunnel.size();
        }

        Deque<Train> tunnel;
        int count;

        void create ( int count)
        {
            int c = count;
            while (c-- != 0)
                tunnel.add(new Train().getTrain(c >= count / 2 ? "A" : "B"));
        }
        void shake ()
        {
            Deque<Train> deq = new LinkedList<>();
            var arr = (Integer[]) new Randomize().getRandom(count, 100);
            for (int rnd : arr) {
                deq.add(rnd % 2 == 0 ? tunnel.pollFirst() : tunnel.pollLast());
            }
            tunnel = deq;
        }
        boolean parseAndCreate (String line)
        {
            char type = '\0';
            for (char c : line.toCharArray()) {
                if (Character.isLetter(c)) type = c;
                else if (Character.isDigit(c) && type != '\0') {
                    tunnel.addLast(
                            new Train().getTrain(
                                    Character.toString(type),
                                    Integer.parseInt(Character.toString(c)))
                    );
                    type = '\0';
                }
            }
            return tunnel.size() % 2 == 0;
        }
        public void sort ()
        {
            Deque<Train> res = new LinkedList<>();
            Stack<Train> stk = new Stack<>();

            Train a;
            Train b;
            boolean reverse = false;
            int order = 1;
            int count = 0;

            while (!stk.equals(new Stack<>()) || !tunnel.isEmpty()) {
                a = res.peekLast();
                b = tunnel.peekFirst();

                if (b.isOrder(order) && !b.equals(a)) {
                    res.addLast(tunnel.pollFirst());
                    count++;
                    if (count % 2 == 0) order++;
                } else if (!reverse) stk.push(tunnel.pollFirst());
                else tunnel.addFirst(stk.pop());

                if (tunnel.size() == 0 || (stk.size() == 0 && reverse)) {
                    reverse = !reverse;
                    if (stk.size() != 0) tunnel.addFirst(stk.pop());
                }
            }
            tunnel = res;
        }
        public boolean save (String path)
        {
            List<String> list = new LinkedList<>();
            list.add(this.toString());
            try {
                IO.saveText(path, list);
            } catch (IOException e) {
                return false;
            }
            return true;
        }
        @Override public String toString ()
        {
            String concat = "";
            for (Train train : tunnel) {
                concat += train + " ";
            }
            return concat;
        }

        class Train {
            String tClass;
            int order;

            Train getTrain(String tClass, int order) {
                if (tClass.equals("a") || tClass.equals("A")) return new TypeA(order);
                if (tClass.equals("b") || tClass.equals("B")) return new TypeB(order);
                return null;
            }

            Train getTrain(String tClass) {
                if (tClass.equals("a") || tClass.equals("A")) return new TypeA(order);
                if (tClass.equals("b") || tClass.equals("B")) return new TypeB(order);
                return null;
            }

            boolean equals(Train train) {
                if (train == null) return false;
                return this.tClass.equals(train.tClass);
            }

            @Override
            public String toString() {
                return tClass;
            }

            public boolean isOrder(Train train) {
                if (train == null) return false;
                return order == train.order;
            }

            public boolean isOrder(int order) {
                return this.order == order;
            }
        }
        class TypeA extends Train {
            TypeA(int order) {
                tClass = "A";
                this.order = order;
            }

            @Override
            public String toString() {
                return super.toString() + order;
            }
        }
        class TypeB extends Train {
            TypeB(int order) {
                tClass = "B";
                this.order = order;
            }

            @Override
            public String toString() {
                return super.toString() + order;
            }
        }

        public class Randomize {
            public Number[] getRandom(Number[] set) {
                Random rnd = new Random();
                int res = 0;
                for (int i = 0; i < set.length; i++) set[i] = (rnd.nextInt(100));
                return set;
            }

            public Integer[] getRandom(int count, int bound) {
                Random rnd = new Random();
                var set = new Integer[count];
                int res = 0;
                for (int i = 0; i < count; i++) set[i] = (rnd.nextInt(bound));
                return set;
            }
        }

        public static class IO {
            public static List<String> getText(String path)
                    throws IOException {
                File file = new File(path);
                List<String> text = new LinkedList<>();
                if (file.exists()) {
                    text = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
                }
                return text;
            }

            public static boolean saveText(String path, List<String> lines)
                    throws IOException {
                File file = new File(path);
                if (!file.exists()) file.createNewFile();
                FileWriter writer = new FileWriter(path, true);
                for (String line : lines)
                    writer.write('\n' + line);
                writer.flush();
                writer.close();
                return true;
            }
        }

        public static void main (String[]args){
            try {
                trains = new TrainSort("C:\\Users\\devil\\OneDrive\\Рабочий стол\\train.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(trains.toString());
            trains.sort();
            trains.save("C:\\Users\\devil\\OneDrive\\Рабочий стол\\train.txt");
            System.out.println(trains.toString());
        }
    }
}
