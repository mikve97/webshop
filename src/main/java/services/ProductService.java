package services;

import io.dropwizard.auth.AuthenticationException;
import models.ProductModel;
import persistences.ProductPersistence;
import nl.dfbackend.git.util.DbConnector;
import org.skife.jdbi.v2.DBI;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.QueryParam;
import java.sql.SQLException;
import java.util.List;

public class ProductService {
    private AuthenticationService authenticationService;
    private DBI dbi;
    public ProductService() throws SQLException {
        this.authenticationService = new AuthenticationService();

        DbConnector.getInstance();
        dbi = DbConnector.getDBI();


    }

    public List<ProductModel> getAllProducts(String token) throws AuthenticationException {
        if (this.authenticationService.authenticate(token).isPresent()) {
            ProductPersistence productDAO = dbi.open(ProductPersistence.class);
            List<ProductModel> fetchedProducts = productDAO.getAllProducts();
            productDAO.close();
            return fetchedProducts;
        } else {
            return null;
        }
    }

    public ProductModel getProduct(String TokenHeaderParam, int pId) throws AuthenticationException {
//        if (this.authenticationService.authenticate(token).isPresent()) {
        ProductPersistence productDAO = dbi.open(ProductPersistence.class);
        ProductModel product = productDAO.getProducts(pId);
        productDAO.close();

        return product;
//        } else {
//            return null;
//        }
    }

    public int setProduct(String TokenHeaderParam, String name, String descr, Double price, int catId) throws AuthenticationException {
//        if (this.authenticationService.authenticate(token).isPresent()) {
        ProductPersistence productDAO = dbi.open(ProductPersistence.class);
        //Returns the number of rows changed, if the query failed this should return 0
        int row = productDAO.setProduct(name, descr, price, catId);
        productDAO.close();

        return row;
//        } else {
//            return null;
//        }
    }
}
