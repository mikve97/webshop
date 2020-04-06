package resources;

import io.dropwizard.auth.AuthenticationException;
import models.NewUserModel;
import services.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.Date;

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

        return Response.ok(this.uService.createNewUser(user, new Date())).build();
    }

    @Path("/countAllUsers")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response setNewUser(@HeaderParam("Token") String TokenHeaderParam) throws AuthenticationException {
//        int product = this.oService.setNewOrder(2);

        return Response.ok(this.uService.countAllUsers(TokenHeaderParam)).build();
    }



}
