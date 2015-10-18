package Homepage;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
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
public class GenerateHomepage { 
    private List<NewsStation>  newsStationList;
    private List<HashMap<String, String>> articleList;
    private HashMap<String, String> article;
    private SyndFeedInput input = new SyndFeedInput();
    private URL feedUrl;
    private int count;
    private String title;
    private String link;
    private Date date;
    private String listItem;
     
    public GenerateHomepage()
    {
        newsStationList = new ArrayList();
        setUpNewsSationList();
        input = new SyndFeedInput();    
    }
 
    public void generatePage(HttpServletRequest request, HttpServletResponse response)
    {
        try 
        {    
            aggregateRssFeeds();
            request.setAttribute("newsStationList", newsStationList);
            //response.sendRedirect("homepage.jsp");
            request.getRequestDispatcher("homepage.jsp").forward(request, response);      
        } catch (IOException ex) {
            Logger.getLogger(GenerateHomepage.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (ServletException ex)
        {
            ex.printStackTrace();
        }         
    }
    
    public void aggregateRssFeeds()
    {
        try
        {                
//            final OutputStream os2 = new FileOutputStream("/Users/cameronthomas/Desktop/list.txt");
//            final PrintStream printStream2 = new PrintStream(os2);
            
            int testCount = 1;

    
            for(NewsStation station: newsStationList )
            {
                feedUrl = station.getRssUrl();
                articleList = new ArrayList();
                SyndFeed feed = input.build (new XmlReader(feedUrl));
                
                // Count to keep track of number of articles for station
                // Limit of 10 articles
                count = 0;
                
              //  printStream2.println("test count" + testCount);
                testCount++;
                
           
                for (SyndEntry entry : (List<SyndEntry>) feed.getEntries()) 
                {
                    link = entry.getLink();                
                    Document doc = Jsoup.connect(link).get();
                    
                    if(!doc.toString().contains("rape"))
                    {
              //           printStream2.println("no rape");
                    
                        article = new HashMap();                
                        title = entry.getTitle();

                        date = entry.getPublishedDate();

                        // Insert article data into HashMap
                        article.put("title", entry.getTitle());
                        article.put("link", entry.getLink());
                        article.put("date", date.toString());
                        
                       
                        articleList.add(article);
                        
                        count++; 
                        
               //       printStream2.println(article.get("title")); 
//                    printStream2.println(); 
//                    printStream2.println(article.toString());     
                    }
                    else 
               //         printStream2.println("rape");

                        // Limits station to 10 articles
                        if(count == 10)
                            break;
                    
                }

                station.setArticleList(articleList);
                
//                printStream2.println();   
//                printStream2.println();         
            }  
            
         //   printStream2.close();
        }
       
        catch(MalformedURLException ex)
        {
            ex.printStackTrace();   
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
    
    private void setUpNewsSationList()
    {
        try
        {
            newsStationList.add(new NewsStation("https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Fox_News_Channel_logo.png/400px-Fox_News_Channel_logo.png",
                                "Fox News",
                                new URL("http://feeds.foxnews.com/foxnews/latest")));

            newsStationList.add(new NewsStation("https://upload.wikimedia.org/"
                                + "wikipedia/commons/thumb/8/8b/Cnn.svg/460px-Cnn.svg.png",
                                "CNN",
                                new URL("http://rss.cnn.com/rss/cnn_topstories.rss")));

            newsStationList.add(new NewsStation("http://www.deseretnews.com/img/masthead/deseret-news-mast-@2x.png", "Deseret News",
                                new URL("http://www.deseretnews.com/news/index.rss")));

            newsStationList.add(new NewsStation("http://www.localnews8.com/image/view/-/19411376/highRes/2/-/50ax11/-/site-header-logo-png.png", "Local News 8",
                                new URL("http://www.localnews8.com/14412868?format=rss_2.0&view=feed")));

            newsStationList.add(new NewsStation("https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/ESPN_wordmark.svg/440px-ESPN_wordmark.svg.png", "ESPN",
                                new URL("http://sports.espn.go.com/espn/rss/news")));
            
//            newsStationList.add(new NewsStation("https://upload.wikimedia.org/wikipedia/en/f/fa/ABCNewsLogo.png", "ABC News",
//                                new URL("http://feeds.abcnews.com/abcnews/topstories")));
        }
        catch(MalformedURLException ex)
        {
            ex.printStackTrace();  
        }      
    }
}
