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
    private ArrayList<String> wordsToExcludeInArticle;
    private ArrayList<String> wordsToIncludeInArticle;
    private SyndFeedInput input = new SyndFeedInput();
    private String databaseId;
    private DatabaseAPI database;
     
    public GenerateHomepage()
    {
        newsStationList = new ArrayList();
        setupNewsSationList();
        input = new SyndFeedInput();
        wordsToExcludeInArticle = new ArrayList();
        wordsToIncludeInArticle = new ArrayList();   
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
    
    /*
    * This is a function I wrote for a web app for my final project in my 
    * Web Engineering 2 class. The web app combined the RSS feeds from 
    * 5 different news stations and then filters the news based on the 
    * preferences of the user
    */
    public void aggregateRssFeeds(String username)
    {
        // 
        setupWordLists(username);
        URL rssFeedUrlForNewsStation;
        List<HashMap<String, String>> articleList;
        SyndFeed rssFeed;
        Boolean addArticle;
        HashMap<String, String> article;
        String title;
        Date date;
        int numberOfArticlesAccepted;
        String articleURL;
        
        try
        {   
            for(NewsStation newsStation: newsStationList )
            {
                rssFeedUrlForNewsStation = newsStation.getRssUrl();
                articleList = new ArrayList();
                rssFeed = input.build (new XmlReader(rssFeedUrlForNewsStation));
                addArticle = true;
                
                // Count to keep track of number of articles for newsStation
                // Limit of 10 articles
                numberOfArticlesAccepted = 0;
           
                for (SyndEntry articleInNewsStationList : (List<SyndEntry>) rssFeed.getEntries()) 
                {
                    articleURL = articleInNewsStationList.getLink();                
                    Document htmlOfArticle = Jsoup.connect(articleURL).get();
                    addArticle = true;
                    
                    for(String word: wordsToExcludeInArticle)
                    {
                        if(htmlOfArticle.toString().contains(" " + word))
                        {
                            addArticle = false; 
                            break;
                        }
                    }
                         
                    if(addArticle)
                    {
                        for(String word: wordsToIncludeInArticle)
                        {
                            if(!htmlOfArticle.toString().contains(" " + word))
                            {
                                addArticle = false; 
                                break;
                            }
                        } 
                    }
                    
                    if(addArticle)
                    {
                        article = new HashMap();                
                        title = articleInNewsStationList.getTitle();
                        date = articleInNewsStationList.getPublishedDate();

                        // Insert article data into HashMap
                        article.put("title", articleInNewsStationList.getTitle());
                        article.put("link", articleInNewsStationList.getLink());
                        article.put("date", date.toString());
                        articleList.add(article);

                        numberOfArticlesAccepted++;                       
                    }
                    
                    if(numberOfArticlesAccepted == 10)
                        break;             
                }
                
                newsStation.setArticleList(articleList);        
            } 
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
               wordsToExcludeInArticle.add(rs.getString("word"));
            }
            
            // Get words to include list
            selectQuery = "SELECT * from words_to_include WHERE user_id = " + databaseId;
            rs = database.readDatabase(selectQuery);
            
            while(rs.next())
            {
               wordsToIncludeInArticle.add(rs.getString("word"));
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
