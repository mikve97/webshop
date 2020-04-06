package resources;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.dropwizard.auth.AuthenticationException;
import models.CategoryModel;
import models.NewProductModel;
import models.ProductModel;
import services.ProductService;

@Path("/product")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {
    private  ProductService pService;

    public ProductResource() throws SQLException {
        this.pService = new ProductService();
    }

    @Path("/getAllCategories")
    @GET
    public Response getAllCategories(@HeaderParam("Token") String TokenHeaderParam) throws AuthenticationException {
        List<CategoryModel> cat = this.pService.getAllCategories(TokenHeaderParam);

        if(cat != null){
            return Response.ok(cat).build();
        }else{
            return Response.ok("No products found or the user not not authenticated").build();
        }
    }

    @Path("/getAllProducts")
    @GET
    public Response getAllProducts(@HeaderParam("Token") String TokenHeaderParam) throws AuthenticationException {
        List<ProductModel> product = this.pService.getAllProducts(TokenHeaderParam);

        if(product != null){
            return Response.ok(product).build();
        }else{
            return Response.ok("No products found or the user not not authenticated").build();
        }
    }

    @Path("/getAllProductsInStock")
    @GET
    public Response getAllProductsInStock() throws AuthenticationException {
        List<ProductModel> product = this.pService.getAllProductsInStock();

        if(product != null){
            System.out.println("test");
            return Response.ok(product).build();
        }else{
            return Response.ok("No products found or the user not not authenticated").build();
        }
    }

    @Path("/getProduct/{productId}")
    @GET
    public Response getProduct(@HeaderParam("Token") String TokenHeaderParam, @PathParam("productId") int pId) throws AuthenticationException {
        //Returns the found product
        ProductModel product = this.pService.getProduct(TokenHeaderParam, pId);

        if(product != null){
            return Response.ok(product).build();
        }else{
            return Response.ok("No product found with id: "+ pId).build();
        }

    }

    @Path("/changeStock/{productId}")
    @POST
    public Response changeStock(@HeaderParam("Token") String TokenHeaderParam, @PathParam("productId") int pId, boolean inStock) throws AuthenticationException {
        //Changes the product stock
        int result = this.pService.changeStock(TokenHeaderParam, pId, inStock);

        if(result != 0){
            return Response.ok(result).build();
        }else{
            return Response.ok("Er is iets fout gegaan bij het updaten van product: "+ pId).build();
        }

    }

    @Path("/setProduct")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setProduct(@HeaderParam("Token") String TokenHeaderParam, NewProductModel pm) throws AuthenticationException {
        //Returns the amount of rows updated.
        return Response.ok(this.pService.setProduct(TokenHeaderParam, pm.getName(), pm.getProductDescription(), pm.getPrice(), pm.getProductCatId())).build();
    }
}

