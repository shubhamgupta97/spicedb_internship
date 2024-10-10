package social.bsky.shubhamg.spicedb_tester;

import java.util.logging.Logger;

import com.authzed.api.v1.PermissionService.CheckPermissionResponse.Permissionship;

public class SpiceDBClient {
	private static final Logger logger = Logger.getLogger(SpiceDBClient.class.getName());
    private final String target;
    private final String token;
    
    SpiceDBClient(String target, String token) {
    	this.target = target;
    	this.token = token;
    }
    
    public String writeSchema() { return ""; }
    public String readSchema() { return ""; }
    public Permissionship checkPermission() { return null; }
    
}
