package social.bsky.shubhamg.spicedb_tester;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class App {
	private static final Logger logger = Logger.getLogger(App.class.getName());
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
    	
    	final String ACCESS_TOKEN = properties.getProperty("access.token");
    	final String TARGET = properties.getProperty("target");
    	final String PERMISSION_SYSTEM = properties.getProperty("permission.system");
    	
    	SpiceDBClient spiceDBClient = new SpiceDBClient(TARGET, ACCESS_TOKEN);
    	
    	String schema = """
    			definition PERMISSION_SYSTEM/user {}
    			
    			definition PERMISSION_SYSTEM/group {
    				relation member: PERMISSION_SYSTEM/user
    			}
    			
    			definition PERMISSION_SYSTEM/event {
    				relation recruiter: PERMISSION_SYSTEM/user | PERMISSION_SYSTEM/group#member
    				relation panelist: PERMISSION_SYSTEM/user | PERMISSION_SYSTEM/group#member
    				relation coordinator: PERMISSION_SYSTEM/user | PERMISSION_SYSTEM/group#member
    				relation owner: PERMISSION_SYSTEM/user | PERMISSION_SYSTEM/group#member
    				
    				permission view = panelist + manage
    				permission manage = recruiter + delete
    				permission delete = owner + coordinator
    			}
    			
    			definition PERMISSION_SYSTEM/job {
    				relation reader: PERMISSION_SYSTEM/user
    				relation owner: PERMISSION_SYSTEM/user
    				
    				permission view = reader + edit
    				permission edit = owner
    				permission delete = owner
    			}
    	""";
    	schema = schema.replaceAll("PERMISSION_SYSTEM", PERMISSION_SYSTEM);
    	
    	String writeSchemaResponse = spiceDBClient.writeSchema(schema);
    	System.out.println(writeSchemaResponse);
    	
    }
}
