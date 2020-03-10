package resources;

import io.dropwizard.auth.AuthenticationException;
import models.OrderModel;
import models.ProductModel;
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
    //TODO: QUERY PARAM WITH REPLACE WITH PRODUCTMODEL
    public Response setNewOrder(@HeaderParam("Token") String TokenHeaderParam, @QueryParam("products") String JSONproduct) throws AuthenticationException {
        int product = this.oService.setNewOrder(2);

        if(product != -1){
            return Response.ok(product).build();
        }else{
            return Response.ok("Failed creating order").build();
        }
    }


}
