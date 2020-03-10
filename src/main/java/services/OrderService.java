package services;

import io.dropwizard.auth.AuthenticationException;
import models.OrderModel;
import models.ProductModel;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.skife.jdbi.v2.DBI;
import persistences.OrderPersistence;
import persistences.ProductPersistence;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class OrderService {

    private AuthenticationService authenticationService;
    private DBI dbi;
    public OrderService() throws SQLException {
        this.authenticationService = new AuthenticationService();

        util.DbConnector.getInstance();
        dbi = util.DbConnector.getDBI();


    }

    public List<OrderModel> getAllOrders(String token) throws AuthenticationException {
//        if (this.authenticationService.authenticate(token).isPresent()) {
            OrderPersistence orderDAO = dbi.open(OrderPersistence.class);

            List<OrderModel> fetchedOrders = orderDAO.getAllOrders();
            orderDAO.close();

        for(OrderModel order : fetchedOrders) {
            //Retrieve the products from a given order. And insert them in to the order.
            order.setProducts( this.getProductsByOrderId(order.getOrderId()) );
        }

        return fetchedOrders;
//        } else {
//            return null;
//        }
    }


    //TODO: MOVE TO PRODUCT SERVICE
    private List<ProductModel> getProductsByOrderId(int oId){
        OrderPersistence orderDAO = dbi.open(OrderPersistence.class);
        List<ProductModel> fetchedProducts = orderDAO.getAllProductsByOrderId(oId);
        orderDAO.close();

        return fetchedProducts;

    }

    //TODO: REPLACE USER ID IN THE TOKEN VAR WITH THE JWT CLAIMS USERID
    public int setNewOrder(int token) throws AuthenticationException {
//        if (this.authenticationService.authenticate(token).isPresent()) {

        OrderPersistence orderDAO = dbi.open(OrderPersistence.class);
        int fetchedProducts = orderDAO.insertOrderTrans(token, new Date());
        System.out.println(fetchedProducts);
        orderDAO.close();


        return fetchedProducts;
//        } else {
//            return null;
//        }
    }


}
