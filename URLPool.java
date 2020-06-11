import java.util.LinkedList;
public class URLPool {
    public LinkedList<URLDepthPair> end;
    public LinkedList<URLDepthPair> start;
    public int waitingThreads;
    public int maxDepth;

    public URLPool(int a) {
        waitingThreads = 0;
        maxDepth = a;
        start= new LinkedList<URLDepthPair>();
        end = new LinkedList<URLDepthPair>();
    }
    public boolean haveElement(URLDepthPair d) {
        boolean yn = false;
        for (URLDepthPair pair:start) {
            if (d.getURL().equals(pair.getURL()))
                yn = true;
        }
        for (URLDepthPair pair:end) {
            if (d.getURL().equals(pair.getURL()))
                yn = true;
        }
        return yn;
    }

    public synchronized URLDepthPair Pair() {
        if (start.size() == 0) {
            waitingThreads++;
            try {
                this.wait();
            } catch (InterruptedException e) {
                System.err.println("MalformedURLException: " + e.getMessage());
                return null;
            }
        }
        URLDepthPair depthPair = start.removeFirst();
        end.add(depthPair);
        return depthPair;
    }
    public synchronized void addURL(URLDepthPair pair) {
        if (!haveElement(pair))start.addLast(pair);
        if (waitingThreads > 0) waitingThreads--;
        this.notify();
    }

    public synchronized int MaxDepth() {
        return maxDepth;
    }
    public synchronized int getWaitingThreads() {
        return waitingThreads;
    }
    public synchronized LinkedList<URLDepthPair> getFinishedList() {
        return end;
    }

}