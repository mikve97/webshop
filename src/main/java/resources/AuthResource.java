package resources;

import java.sql.SQLException;
import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import models.CredentialModel;
import models.UserModel;
import services.AuthenticationService;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {
    private AuthenticationService authenticationService;

    public AuthResource() throws SQLException {
        this.authenticationService = new AuthenticationService();
    }
    
    @Path("/{username}/{password}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Optional<UserModel> onLogin(@PathParam("username") String username, @PathParam("password") String password) throws SQLException{
        CredentialModel credential = new CredentialModel(username, password);

        return authenticationService.authenticateUser(credential);
    }
}