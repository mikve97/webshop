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
//        if (this.authenticationService.authenticate(token).isPresent()) {
            ProductPersistence productDAO = dbi.open(ProductPersistence.class);
            List<ProductModel> fetchedProducts = productDAO.getAllProducts();
            return fetchedProducts;
//        } else {
//            return null;
//        }
    }

    public int setProduct(String TokenHeaderParam, String name, String descr, Double price, int catId) throws AuthenticationException {
//        if (this.authenticationService.authenticate(token).isPresent()) {
        ProductPersistence productDAO = dbi.open(ProductPersistence.class);
        int productID = productDAO.setProduct(name, descr, price, catId);
        return productID;
//        } else {
//            return null;
//        }
    }
}
