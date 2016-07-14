package su.vistar.gvpromoweb.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;


/*работа с файлом settings.properties*/

public class PropertyService {
    
   public void writeProperty(String key, String value) throws IOException{
    try {
        Properties props = new Properties();
        props.setProperty(key, value);
        File f = new File("settings.properties");
        OutputStream out = new FileOutputStream(f);
        props.store(out, "This is an optional header comment string");
    }
    catch (Exception e ) {
        e.printStackTrace();
    }
   }
   
   public String readProperty(String key){
       
       Properties props = new Properties();
       InputStream is = null;
        try {
            File f = new File("settings.properties");
            is = new FileInputStream(f);
        }
        catch ( Exception e ) { is = null; } 
        try {
            if ( is == null ) {
                is = getClass().getResourceAsStream("settings.properties");
            }
            props.load( is );
        }
        catch ( Exception e ) { }
        return props.getProperty(key);
   }
}
