import java.net.*;
public class URLDepthPair {
    private String url;
    private int depth = 0;
    public URLDepthPair(String site, int ch) {
        url = site;
        depth = ch;
    }
    public void setURL(String newUrl) {
        url = newUrl;
    }
    public String getURL() {
        return url;
    }
    public String toString() {
        return Integer.toString(depth) + '\t' + url;
    }
    public int getDepth() {
        return depth;
    }
    public String getPath() {
        try
        {
            return (new URL(url)).getPath();
        }
        catch (MalformedURLException e) {
            System.err.println("MalformedURLException : " + e.getMessage());
            return null;
        }
    }
    public String getHost() {
        try
        {
            return (new URL(url)).getHost();
        } catch (MalformedURLException e) {
            System.err.println("MalformedURLException : " + e.getMessage());
            return null;
        }
    }
}
