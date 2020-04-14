package resources;

import io.dropwizard.auth.AuthenticationException;
import models.ContactModel;
import services.ContactService;
import services.OrderService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

@Path("/api/contact")
@Produces(MediaType.APPLICATION_JSON)
public class ContactResource {
    private ContactService cService;
    public ContactResource() throws SQLException {
        this.cService = new ContactService();
    }

    @Path("/getContact/{userId}")
    @GET
    public List<ContactModel> getContact(@HeaderParam("Token") String TokenHeaderParam, @PathParam("userId") int userId) throws AuthenticationException {
        List<ContactModel> contact = this.cService.getContactByUserId(TokenHeaderParam, userId);

        return contact;
    }

    @Path("/getContactFavorite/{userId}")
    @GET
    public ContactModel getContactFavorite(@HeaderParam("Token") String TokenHeaderParam, @PathParam("userId") int userId) throws AuthenticationException {
        ContactModel contact = this.cService.getContactFavorite(TokenHeaderParam, userId);

        return contact;
    }

    @Path("/changeFavorite")
    @POST
    public Response changeFavorite(@HeaderParam("Token") String TokenHeaderParam, int[] favoriteArr) throws AuthenticationException {
        boolean result = this.cService.changeFavorite(TokenHeaderParam, favoriteArr[0], favoriteArr[1]);

        return Response.ok(result).build();
    }

}
