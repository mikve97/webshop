package services;

import io.dropwizard.auth.AuthenticationException;
import models.OrderModel;
import models.ProductModel;
import org.skife.jdbi.v2.DBI;
import persistences.OrderPersistence;
import persistences.ProductPersistence;

import java.sql.SQLException;
import java.util.List;

public class OrderService {

    private AuthenticationService authenticationService;
    private DBI dbi;
    public OrderService() throws SQLException {
        this.authenticationService = new AuthenticationService();

        nl.dfbackend.git.util.DbConnector.getInstance();
        dbi = nl.dfbackend.git.util.DbConnector.getDBI();


    }

    public List<OrderModel> getAllOrders(String token) throws AuthenticationException {
//        if (this.authenticationService.authenticate(token).isPresent()) {
        OrderPersistence productDAO = dbi.open(OrderPersistence.class);
        List<OrderModel> fetchedProducts = productDAO.getAllOrders();
        productDAO.close();

        for(OrderModel order : fetchedProducts) {
            //Retrieve the products from a given order. And insert them in to the order.
            order.setProducts( this.getProductsByOrderId(order.getOrderId()) );
        }

        return fetchedProducts;
//        } else {
//            return null;
//        }
    }

    private List<ProductModel> getProductsByOrderId(int oId){
        OrderPersistence productDAO = dbi.open(OrderPersistence.class);
        List<ProductModel> fetchedProducts = productDAO.getAllProductsByOrderId(oId);
        productDAO.close();

        return fetchedProducts;

    }

    public List<OrderModel> getOrderByUser(String token) throws AuthenticationException {
//        if (this.authenticationService.authenticate(token).isPresent()) {
        OrderPersistence productDAO = dbi.open(OrderPersistence.class);
        List<OrderModel> fetchedProducts = productDAO.getAllOrders();
        productDAO.close();

        for(OrderModel order : fetchedProducts) {
            //Retrieve the products from a given order. And insert them in to the order.
            order.setProducts( this.getProductsByOrderId(order.getOrderId()) );
        }

        return fetchedProducts;
//        } else {
//            return null;
//        }
    }


}
