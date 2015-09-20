/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Homepage;

import java.net.URL;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author cameronthomas
 */
public class NewsStation {
    private String imgHtml;
    private String name;
    private List<HashMap<String, String>> articleList ;
    private URL rssUrl;

    public NewsStation(String imgHtml, String name, URL rssUrl) {
        this.imgHtml = imgHtml;
        this.name = name;
        this.rssUrl = rssUrl;
    }

    public String getImgHtml() {
        return imgHtml;
    }

    public String getName() {
        return name;
    }

    public List<HashMap<String, String>> getArticleList() {
        return articleList;
    }
    
    public URL getRssUrl() {
        return rssUrl;
    }
    
    public void setArticleList(List<HashMap<String, String>> articleList) {
        this.articleList = articleList;
    }   
}
