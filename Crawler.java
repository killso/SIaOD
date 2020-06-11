import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.*;
public class Crawler {

    public static final String Start = "a href=\"";
    public static final String End = "\"";
    public static ArrayList<String> getSites(URLDepthPair siteForAnalysis) {
        ArrayList<String> url1 = new ArrayList<String>();
        String host = siteForAnalysis.getHost();
        String path = siteForAnalysis.getPath();
        Socket connection;
        try {
            connection = new Socket(host, 80);
        }
        catch (UnknownHostException e) {
            System.err.println("UnknownHostException: " + e.getMessage());
            return url1;
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            return url1;
        }
        try
        {
            connection.setSoTimeout(3000);
        }
        catch (SocketException e) {
            System.err.println("SocketException: " + e.getMessage());
            return url1;
        }
        OutputStream outputStream;
        try
        {
            outputStream = connection.getOutputStream();
        }
        catch (IOException exc) {
            System.err.println("IOException: " + exc.getMessage());
            return url1;
        }
        PrintWriter printWr = new PrintWriter(outputStream, true);
        printWr.println("GET " + path + " HTTP/1.1");
        printWr.println("Host: " + host);
        printWr.println("Connection: close");
        printWr.println();
        InputStream inputStream;
        try
        {
            inputStream = connection.getInputStream();
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            return url1;
        }
        InputStreamReader in = new InputStreamReader (inputStream);
        BufferedReader inLines = new BufferedReader(in);
        while(true)
        {
            String str;
            int beginIndex=0;
            int endIndex=0;
            try {
                str=inLines.readLine();
            }
            catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
                break;
            }
            if(str==null) break;
            while (true){
                beginIndex = str.indexOf(Start,beginIndex);
                if(beginIndex==(-1)) break;
                endIndex = str.indexOf(End, beginIndex);
                beginIndex += Start.length();
                url1.add(str.substring(beginIndex,endIndex));
                beginIndex=endIndex;
            }
        }
        for (int i = 0; i < url1.size();i++) {
            if (url1.get(i).startsWith("/")) {
                String site1 = siteForAnalysis.getURL() + url1.get(i).substring(1);
                url1.set(i, site1);
            }
        }
        return url1;
    }
    public static void main(String args[]) throws UnknownHostException, IOException {
        int depth = 0;
        String url;
        int potoki =0 ;
        try {
            url=args[0];
            depth=Integer.parseInt(args[1]);
        }
        catch (NumberFormatException nfe) {
            System.out.println("usage : java Crawler <URL> <depth>");
            return;
        }
        URLDepthPair startSite = new URLDepthPair(url, 0);
        ArrayList<URLDepthPair> sites = new ArrayList<URLDepthPair>();
        sites.add(startSite);
        ArrayList<String> process = getSites(startSite);
        for (String site:process) {
            URLDepthPair pair = new URLDepthPair(site, 1);
            if (!sites.contains(pair)) sites.add(pair);
        }
        int depth2=2;
        while (depth2<=depth) {
            ArrayList<String> allSitesOnDepth = new ArrayList<String>();
            for (String siteOnDepth:process) {
                URLDepthPair siteOnDepthPair = new URLDepthPair(siteOnDepth, depth2);
                ArrayList<String> sitesOfSiteOnDepth = getSites(siteOnDepthPair);
                for (String tmp:sitesOfSiteOnDepth) {
                    if (!allSitesOnDepth.contains(tmp)) allSitesOnDepth.add(tmp);
                }
            }
            for (String site:allSitesOnDepth) {
                if (!sites.contains(site)) sites.add(new URLDepthPair(site, depth2));
            }
            depth2++;
        }
        for (URLDepthPair site:sites) {
            System.out.println(site.getDepth() + "\t" + site.getURL());
        }


        if (args.length != 3)
            return;
        try {
            url = args[0];
            depth = Integer.parseInt(args[1]);
           potoki = Integer.parseInt(args[2]);
        } catch (NumberFormatException nfe) {
            System.out.println("usage: java Crawler <URL> <depth> <number of crawler threads>");
            return;
        }
        
        URLDepthPair startSite = new URLDepthPair(url, 0);
        URLPool sites = new URLPool(depth);
        sites.addURL(startSite);

        // даем потокам задание и ждем выполнения
        int countActiveThreadOnStart = Thread.activeCount();
        int waitingThreads = sites.getWaitingThreads();
        while (waitingThreads <=potoki) {
            int countThreadsNow = Thread.activeCount() - countActiveThreadOnStart;
            if (countThreadsNow <potoki) {
                new Thread(new CrawlerTask(sites)).start();
            } else if (waitingThreads == potoki) {
                for (URLDepthPair pair:sites.getFinishedList()) {
                    System.out.println(pair);
                }
                System.exit(1);
            }
            waitingThreads = sites.getWaitingThreads();
        }
    }
}
