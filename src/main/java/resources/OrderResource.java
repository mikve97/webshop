package resources;

import io.dropwizard.auth.AuthenticationException;
import models.OrderModel;
import models.ProductModel;
import services.OrderService;
import services.ProductService;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    public Response getAllProducts(@HeaderParam("Token") String TokenHeaderParam) throws AuthenticationException {
        List<OrderModel> product = this.oService.getAllOrders(TokenHeaderParam);

        if(product != null){
            return Response.ok(product).build();
        }else{
            return Response.ok("No products found").build();
        }
    }
}
