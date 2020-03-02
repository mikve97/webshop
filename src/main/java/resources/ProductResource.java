package resources;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.dropwizard.auth.AuthenticationException;
import models.ProductModel;
import services.ProductService;

@Path("/product")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {
    private  ProductService pService;

    public ProductResource() throws SQLException {
        this.pService = new ProductService();
    }

    @Path("/getAllProducts")
    @GET
    public Response getAllProducts(@QueryParam("Token") String TokenHeaderParam) throws AuthenticationException {
        List<ProductModel> product = this.pService.getAllProducts(TokenHeaderParam);

        if(product != null){
            return Response.ok(product).build();
        }else{
            return Response.ok("No products found or the user not not authenticated").build();
        }
    }

    @Path("/getProduct/{productId}")
    @GET
    public Response setProduct(@HeaderParam("Token") String TokenHeaderParam, @PathParam("productId") int pId) throws AuthenticationException {
        System.out.println(TokenHeaderParam + " "+  pId);

        //Returns the found product
        ProductModel product = this.pService.getProduct(TokenHeaderParam, pId);

        if(product != null){
            return Response.ok(product).build();
        }else{
            return Response.ok("No product found with id: "+ pId).build();
        }



    }

    @Path("/setProduct")
    @POST
    public Response setProduct(@HeaderParam("Token") String TokenHeaderParam, @QueryParam("name") String name, @QueryParam("description") String descr, @QueryParam("price") Double price, @QueryParam("catId") int catId) throws AuthenticationException {
        System.out.println(TokenHeaderParam + " "+  name  + " "+ descr  + " "+ price  + " "+ catId);

        //Returns the amount of rows updated.
        return Response.ok(this.pService.setProduct(TokenHeaderParam, name, descr, price, catId)).build();
    }
}

