package social.bsky.shubhamg.spicedb_tester;

import java.io.InputStream;
import java.util.Properties;

public class App {
	private static final Properties properties = new Properties();
	
	public static void readProperties(String filename) {
		String filepath = "config/" + filename;
    	
    	try (InputStream input = App.class.getClassLoader().getResourceAsStream(filepath)) {
    		if(input == null) {
    			System.out.println("Sorry, unable to find " + filename);
                return;
            }
            
            properties.load(input);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
    public static void main( String[] args ){
    	readProperties("application.properties");
    	
    	String accessToken = properties.getProperty("access.token");
    	String target = properties.getProperty("target");
    	
    	SpiceDBClient spiceDBClient = new SpiceDBClient(accessToken, target);
    	
    }
}
