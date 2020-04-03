package resources;

import com.google.gson.Gson;
import io.dropwizard.auth.AuthenticationException;
import models.*;
import services.OrderService;
import services.ProductService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

@Path("/order")
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {
    private OrderService oService;

    public OrderResource() throws SQLException {
        this.oService = new OrderService();
    }

    @Path("/getAllOrders")
    @GET
    public Response getAllOrders(@HeaderParam("Token") String TokenHeaderParam) throws AuthenticationException {
        List<OrderModel> orders = this.oService.getAllOrders(TokenHeaderParam);

        if(orders != null){
            return Response.ok(orders).build();
        }else{
            return Response.ok("No products found").build();
        }
    }

    @Path("/setNewOrder")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setNewOrder(NewOrder order) throws AuthenticationException, SQLException {
        Gson g = new Gson();
        ContactModel contact = g.fromJson(order.getContactNaw(), ContactModel.class);
        ProductModel[] products = g.fromJson(order.getProducts(), ProductModel[].class);
        NewUserModel newUser = g.fromJson(order.getNewUser(), NewUserModel.class);

        int product = this.oService.setNewOrder(contact, products, newUser);

//        if(product != -1){
//            return Response.ok(product).build();
//        }else{
//            return Response.ok("Failed creating order").build();
//        }
        return Response.accepted().build();
    }

    @Path("/checkPostalCode/{postalcode}")
    @GET
    public Response getPostalCode(@PathParam("postalcode") String postalcode){
        boolean valid = this.oService.checkPostalCode(postalcode);

        return Response.ok(valid).build();
    }


}
