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

@Path("/api/order")
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

    @Path("/countAllOrders")
    @GET
    public Response countAllOrders(@HeaderParam("Token") String TokenHeaderParam) throws AuthenticationException {
        int orders = this.oService.countAllOrders(TokenHeaderParam);

        if(orders > 0){
            return Response.ok(orders).build();
        }else{
            return Response.ok("No products found").build();
        }
    }

    @Path("/getOrderFromUser/{userId}")
    @GET
    public Response getOrderFromUser(@HeaderParam("Token") String TokenHeaderParam, @PathParam("userId") int userId) throws AuthenticationException {
        List<OrderModel> orders = this.oService.getOrderFromUser(TokenHeaderParam, userId);
        System.out.println(orders);

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

        return Response.accepted(product).build();
    }

    @Path("/checkPostalCode/{postalcode}")
    @GET
    public Response getPostalCode(@PathParam("postalcode") String postalcode){
        boolean valid = this.oService.checkPostalCode(postalcode);

        return Response.ok(valid).build();
    }

    @Path("/setDelivery/{orderId}")
    @POST
    public int setOrderDeliveryStatus(@HeaderParam("Token") String TokenHeaderParam, @PathParam("orderId") int orderId, boolean status) throws AuthenticationException {
        int result = this.oService.setDeliveryState(TokenHeaderParam, orderId, status);

        return result;
    }


}
