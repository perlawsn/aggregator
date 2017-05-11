package org.dei.perla.socket;

import java.net.URL;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.io.InputStreamReader;


//http://www.vogella.com/tutorials/RSSFeed/article.html#read
public class RssReader {

    public static void main(String[] args) {
        boolean ok = false;
        if (args.length==1) {
            try {
                URL feedUrl = new URL("http://feeds.feedburner.com/ingv/VVGo");

                SyndFeedInput input = new SyndFeedInput();
                SyndFeed feed = input.build(new XmlReader(feedUrl));

                System.out.println(feed);

                ok = true;
            }
            catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("ERROR: "+ex.getMessage());
            }
        }

        if (!ok) {
            System.out.println();
            System.out.println("FeedReader reads and prints any RSS/Atom feed type.");
            System.out.println("The first parameter must be the URL of the feed to read.");
            System.out.println();
        }
    }

}