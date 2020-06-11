import java.util.*;
public class CrawlerTask implements Runnable {
    public URLPool workPool;
    public CrawlerTask(URLPool linkOnPool) {
        workPool = linkOnPool;
    }
    public void run() {
        URLDepthPair workPair = workPool.Pair(); //  Получение пары URL-Depth из пула
        LinkedList<String> linksList = Crawler.getSites(workPair); // 2). Получение веб-страницы по URL-адресу
        int depth = workPair.getDepth()+1;
        if (depth > workPool.MaxDepth()) return;
        for (String url:linksList) {
            workPool.addURL(new URLDepthPair(url, depth));
        }
    }

}