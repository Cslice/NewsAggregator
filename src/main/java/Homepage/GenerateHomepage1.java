package Homepage;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author cameronthomas
 */
public class GenerateHomepage1 { 
    List<URL> newsUrlList;
    List<HashMap<String, String>> newsList;

    public GenerateHomepage1()
    {
        newsUrlList = new ArrayList();
        newsList = new ArrayList();

        aggregateNewsUrls();  
        aggregateRssFeeds();
    }
    
    public List newsList()
    {
        return newsList;
    }
    
    public void generatePage(HttpServletRequest request, HttpServletResponse response)
    {
        try {
            //response.sendRedirect("homepage.jsp");
            request.getRequestDispatcher("homepage.jsp").forward(request, response);
        } catch (IOException ex) {
            Logger.getLogger(GenerateHomepage1.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (ServletException ex)
        {
            ex.printStackTrace();
        }
            
    }
    
    public void aggregateRssFeeds()
    {
        int count = 0;
        
        try
        {
            for(URL feedUrl: newsUrlList)
            {
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build (new XmlReader(feedUrl));
            
            count = 1;
            
            for (SyndEntry entry : (List<SyndEntry>) feed.getEntries()) 
            {
                String title = entry.getTitle();
                String uri = entry.getUri();
                String link = entry.getLink();
                Date date = entry.getPublishedDate();
                String desc = entry.getDescription().getValue();
                System.out.println();
 
                
                
                if(count == 10)
                {
                    
                    break;
                }
                count++;
            }
            //System.out.println("End loop");
            }      
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }    
        catch(FeedException ex)
        {
            ex.printStackTrace();
        }
    }
    
    private void aggregateNewsUrls()
    {
        try {
            newsUrlList.add(new URL("http://feeds.foxnews.com/foxnews/latest"));
            newsUrlList.add(new URL("http://rss.cnn.com/rss/cnn_topstories.rss"));
            newsUrlList.add(new URL("http://feeds.abcnews.com/abcnews/topstories"));
            newsUrlList.add(new URL("http://www.deseretnews.com/news/index.rss"));
            newsUrlList.add(new URL("http://www.localnews8.com/14412868?format=rss_2.0&view=feed"));
            newsUrlList.add(new URL("http://sports.espn.go.com/espn/rss/news"));
        } catch (MalformedURLException ex) {
            Logger.getLogger(GenerateHomepage1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
