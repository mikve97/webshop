package services;

import io.dropwizard.auth.AuthenticationException;
import models.*;
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
    public int setNewOrder(ContactModel contact, ProductModel[] products, NewUserModel user) throws AuthenticationException, SQLException {
//        if (this.authenticationService.authenticate(token).isPresent()) {
        ContactService cs = new ContactService();
        //This method either creates or returns a new contact
        ContactModel newContact = cs.createNewContactNaw(contact);
        OrderPersistence orderDAO = dbi.open(OrderPersistence.class);
        if(user.getEmail() != "" && user.getPassword() !=""){
            //Create a new user
            UserService us = new UserService();

            //This method either creates a new user or returns the already existing user.
            UserModel newUser = us.createNewUser(user, newContact.getContactNawId());
            int orderSucceeded = orderDAO.insertOrderTransWithUser(newContact, newUser, products);
            orderDAO.close();
            return orderSucceeded;
        }else{
            int orderSucceeded  = orderDAO.insertOrderTransWithoutUser(newContact, products);
            orderDAO.close();
            return orderSucceeded;
        }
    }

    public boolean checkPostalCode(String pc){

        OrderPersistence orderDAO = dbi.open(OrderPersistence.class);
        int result = orderDAO.getPostalCode(pc);
        orderDAO.close();
        if(result > 0)
            return true;
        else{
            return false;
        }
    }


}
