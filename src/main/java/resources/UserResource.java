package resources;

import io.dropwizard.auth.AuthenticationException;
import models.NewUserModel;
import services.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    private UserService uService;
    public UserResource() throws SQLException {
        this.uService = new UserService();
    }

    @Path("/setNewUser")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setNewUser(NewUserModel user) throws AuthenticationException {
//        int product = this.oService.setNewOrder(2);

        return Response.ok(this.uService.createNewUser(user, -1)).build();
    }
}
