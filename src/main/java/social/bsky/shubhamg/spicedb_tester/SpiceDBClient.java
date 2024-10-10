package social.bsky.shubhamg.spicedb_tester;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.authzed.api.v1.PermissionsServiceGrpc;
import com.authzed.api.v1.SchemaServiceGrpc;
import com.authzed.api.v1.PermissionService.CheckPermissionResponse.Permissionship;
import com.authzed.api.v1.SchemaServiceOuterClass.WriteSchemaRequest;
import com.authzed.api.v1.SchemaServiceOuterClass.WriteSchemaResponse;
import com.authzed.grpcutil.BearerToken;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class SpiceDBClient {
	private static final Logger logger = Logger.getLogger(SpiceDBClient.class.getName());
	private final String target;
	private final String token;
	
	private final SchemaServiceGrpc.SchemaServiceBlockingStub schemaService;
	private final PermissionsServiceGrpc.PermissionsServiceBlockingStub permissionsService;

	SpiceDBClient(String target, String token) {
		this.target = target;
		this.token = token;
		
		ManagedChannel channel = ManagedChannelBuilder
				.forTarget(target)
				.useTransportSecurity()
				.build();
		
		BearerToken bearerToken = new BearerToken(token);
        schemaService = SchemaServiceGrpc.newBlockingStub(channel)
                .withCallCredentials(bearerToken);
        permissionsService = PermissionsServiceGrpc.newBlockingStub(channel)
                .withCallCredentials(new BearerToken(token));
	}

	public String writeSchema(String schema) {
		logger.info("Writing schema...");
		
		WriteSchemaRequest request = WriteSchemaRequest
				.newBuilder()
				.setSchema(schema)
				.build();
		
		WriteSchemaResponse response;
		try {
            response = schemaService.writeSchema(request);
        } catch (Exception e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getMessage());
            return "";
        }
        logger.info("Response: " + response.toString());
        
        return response.toString();
	}

	public String readSchema() { return ""; }

	public Permissionship checkPermission() { return null; }

}
