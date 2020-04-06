package services;

import io.dropwizard.auth.AuthenticationException;
import models.CategoryModel;
import models.ProductModel;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.skife.jdbi.v2.DBI;
import persistences.ProductPersistence;
import persistences.UserPersistence;

import java.sql.SQLException;
import java.util.List;

public class ProductService {
    private AuthenticationService authenticationService;
    private DBI dbi;
    public ProductService() throws SQLException {
        this.authenticationService = new AuthenticationService();

        util.DbConnector.getInstance();
        dbi = util.DbConnector.getDBI();


    }
    public List<CategoryModel> getAllCategories(String TokenHeaderParam) throws AuthenticationException {
        if (this.authenticationService.authenticate(TokenHeaderParam).isPresent() && this.authenticationService.retrieveClaim(TokenHeaderParam, "superUser").equals("true" )) {
            ProductPersistence productDAO = dbi.open(ProductPersistence.class);
            List<CategoryModel> fetchedCategories = productDAO.getAllCategory();
            productDAO.close();


            return fetchedCategories;
        }else{
            return null;
        }
    }

    public List<ProductModel> getAllProducts(String TokenHeaderParam) throws AuthenticationException {
        if (this.authenticationService.authenticate(TokenHeaderParam).isPresent() && this.authenticationService.retrieveClaim(TokenHeaderParam, "superUser").equals("true" )) {
            ProductPersistence productDAO = dbi.open(ProductPersistence.class);
            List<ProductModel> fetchedProducts = productDAO.getAllProducts();
            productDAO.close();


            return fetchedProducts;
        }else{
            return null;
        }
    }

    public List<ProductModel> getAllProductsInStock() {
        ProductPersistence productDAO = dbi.open(ProductPersistence.class);
        List<ProductModel> fetchedProducts = productDAO.getAllProductsInStock();
        productDAO.close();

        return fetchedProducts;
    }




    public ProductModel getProduct(String TokenHeaderParam, int pId) throws AuthenticationException {
        if (this.authenticationService.authenticate(TokenHeaderParam).isPresent() && this.authenticationService.retrieveClaim(TokenHeaderParam, "superUser").equals("true" )) {
            ProductPersistence productDAO = dbi.open(ProductPersistence.class);
            ProductModel product = productDAO.getProducts(pId);
            productDAO.close();

            return product;
        } else {
            return null;
        }
    }

    public int setProduct(String TokenHeaderParam, String name, String descr, Double price, int catId) throws AuthenticationException {
        if (this.authenticationService.authenticate(TokenHeaderParam).isPresent() && this.authenticationService.retrieveClaim(TokenHeaderParam, "superUser").equals("true" )) {
            ProductPersistence productDAO = dbi.open(ProductPersistence.class);
            //Returns the number of rows changed, if the query failed this should return 0
            int row = productDAO.setProduct(name, descr, price, catId);
            productDAO.close();

            return row;
        } else {
            return 0;
        }
    }

    public int changeStock(String TokenHeaderParam, int pId, boolean inStock) throws AuthenticationException {
        if (this.authenticationService.authenticate(TokenHeaderParam).isPresent() && this.authenticationService.retrieveClaim(TokenHeaderParam, "superUser").equals("true" )) {
            ProductPersistence productDAO = dbi.open(ProductPersistence.class);

            int row = productDAO.setStock(pId, inStock);
            productDAO.close();

            return row;
        } else {
            return 0;
        }

    }
}
