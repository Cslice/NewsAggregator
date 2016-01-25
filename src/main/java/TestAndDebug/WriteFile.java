/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TestAndDebug;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author cameronthomas
 */
public class WriteFile {
    public void write(String data)
    {
        try
        {
          File file = new File("/Users/cameronthomas/Desktop/test.txt");
          // creates the file
          file.createNewFile();
          // creates a FileWriter Object
          FileWriter writer = new FileWriter(file); 
          // Writes the content to the file
          writer.write(data); 
          writer.flush();
          writer.close();   
        }
        catch (IOException e)
        {
            e.printStackTrace();;
        }
    }
    
}
