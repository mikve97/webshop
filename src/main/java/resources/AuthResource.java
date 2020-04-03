package resources;

import java.sql.SQLException;
import java.util.Optional;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import models.CredentialModel;
import models.UserModel;
import services.AuthenticationService;
import services.AuthorisationService;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {
    private AuthenticationService authenticationService;
    private AuthorisationService authorisationService;

    public AuthResource() throws SQLException {
        this.authenticationService = new AuthenticationService();
        this.authorisationService = new AuthorisationService();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean onAuthenticateServingPage(@HeaderParam("Token") String TokenHeaderParam) throws SQLException {
        boolean guard = false;

        if(authorisationService.decodeJWToken(TokenHeaderParam)) {
            guard = true;
            return guard;
        }

        return guard;
    }

    @Path("/login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response onLogin(CredentialModel credentialModel) throws SQLException {
        Gson gson = new Gson();
        String token = authenticationService.authenticateUser(credentialModel);
        return Response.ok(gson.toJson(token)).build();
    }
}