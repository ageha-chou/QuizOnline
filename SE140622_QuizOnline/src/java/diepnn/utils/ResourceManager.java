/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diepnn.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 *
 * @author Delwyn
 */
public class ResourceManager {
    public static Properties getProperties(String path) 
            throws FileNotFoundException, IOException{
        InputStreamReader in = null;
        Properties properties = new Properties();
        try{
            in = new InputStreamReader(new FileInputStream(path));
            properties.load(in);
        }finally{
            if(in != null) in.close();
        }
        return properties;
    }
    
}
