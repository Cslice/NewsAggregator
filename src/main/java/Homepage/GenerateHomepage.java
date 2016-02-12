package Homepage;

import Database.DatabaseAPI;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import java.io.FileNotFoundException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private ArrayList<String> excludeWordList;
    private ArrayList<String> includeWordList;
    private SyndFeedInput input = new SyndFeedInput();
    private URL feedUrl;
    private int count;
    private String title;
    private String link;
    private Date date;
    private String listItem;
    private String databaseId;
    private DatabaseAPI database;
     
    public GenerateHomepage()
    {
        newsStationList = new ArrayList();
        setupNewsSationList();
        input = new SyndFeedInput();
        excludeWordList = new ArrayList();
        includeWordList = new ArrayList();   
    }
 
    public void generatePage(HttpServletRequest request, HttpServletResponse response)
    {
        try 
        {    
            aggregateRssFeeds(request.getSession(false).getAttribute("username").toString());
            request.setAttribute("newsStationList", newsStationList);
            request.getRequestDispatcher("homepage.jsp").forward(request, response);      
        } catch (IOException ex) {
            Logger.getLogger(GenerateHomepage.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (ServletException ex)
        {
            ex.printStackTrace();
        }         
    }
    
    public void aggregateRssFeeds(String username)
    {
        setupWordLists(username);
        
        try
        {   
            String test = "";
            for(NewsStation station: newsStationList )
            {
                feedUrl = station.getRssUrl();
                articleList = new ArrayList();
                SyndFeed feed = input.build (new XmlReader(feedUrl));
                Boolean addArticle = true;
                
                
                // Count to keep track of number of articles for station
                // Limit of 10 articles
                count = 0;
           
                for (SyndEntry entry : (List<SyndEntry>) feed.getEntries()) 
                {
                    link = entry.getLink();                
                    Document doc = Jsoup.connect(link).get();
                    addArticle = true;
                    
                    for(String word: excludeWordList)
                    {
                        if(doc.toString().contains(" " + word))
                        {
                            addArticle = false; 
                            test += word + "   " + link + "\n";
                            break;
                        }
                    }
                    
                    
                    if(addArticle)
                    {
                        for(String word: includeWordList)
                        {
                            if(!doc.toString().contains(word))
                            {
                                addArticle = false; 
                                break;
                            }
                        } 
                    }
                    
                    writeFile("/Users/cameronthomas/Desktop/includeWordTest.txt",
                            includeWordList.toString());
                    
                    if(addArticle)
                    {
                        article = new HashMap();                
                        title = entry.getTitle();
                        date = entry.getPublishedDate();

                        // Insert article data into HashMap
                        article.put("title", entry.getTitle());
                        article.put("link", entry.getLink());
                        article.put("date", date.toString());
                        articleList.add(article);

                        count++;                       
                    }
                    
                    if(count == 10)
                        break;             
                }
                
                test += "\n\n\n";

                
                station.setArticleList(articleList);        
            } 
            writeFile("/Users/cameronthomas/Desktop/excludeWordTest.txt",
                            test);
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
    
    private void setupNewsSationList()
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
    
    public void setupWordLists(String username)
    {
        try
        {   
            // Get id of user in user table
            String selectQuery = "SELECT id from user WHERE username = '" + username + "'";
            database = new DatabaseAPI();
            ResultSet rs = database.readDatabase(selectQuery);
            rs.next();
            databaseId = rs.getString("id");
            rs.close();

            // Get words to exclude list
            selectQuery = "SELECT * from words_to_exclude WHERE user_id = " + databaseId;
            rs = database.readDatabase(selectQuery);
            
            while(rs.next())
            {
               excludeWordList.add(rs.getString("word"));
            }
            
            // Get words to include list
            selectQuery = "SELECT * from words_to_include WHERE user_id = " + databaseId;
            rs = database.readDatabase(selectQuery);
            
            while(rs.next())
            {
               includeWordList.add(rs.getString("word"));
            }
        }
        catch(SQLException se){
        //Handle errors for JDBC
        se.printStackTrace();
        }    
        
    }
    
     public static void writeFile(String fileName, String data)
    {
        
        try {
            final OutputStream os2 = new FileOutputStream(fileName);
            final PrintStream p = new PrintStream(os2);
            
            p.print(data);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }      
    }   
}
