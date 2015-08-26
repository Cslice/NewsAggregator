/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Database;

import com.mysql.jdbc.StringUtils;

/**
 *
 * @author cameronthomas
 */
public class DatabaseInfo {
    private String db_url = "";
    private String user = "";
    private String pass = "";  
    private String db_name = "";
    
    public DatabaseInfo()
    {
        String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
        
        // Using opehsift enviroment
        if(!StringUtils.isNullOrEmpty(user))
        {  
            String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
            
            db_name = "newsaggregator";
            db_url = "jdbc:mysql://" + host + ":" + port + "/" + db_name;
            user = "adminDTynxUP";
            pass = "Qkfk1xbnaH4E"; 
        }
        // Using local machine
        else
        {
            db_url = "jdbc:mysql://localhost:8889/news_aggregator";
            user = "root";
            pass = "root"; 
        }
    }

    public String getDb_url() {
        return db_url;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }   
}
