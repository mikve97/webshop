package services;

import io.dropwizard.auth.AuthenticationException;
import models.*;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.skife.jdbi.v2.DBI;
import persistences.OrderPersistence;
import persistences.ProductPersistence;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    public List<OrderModel> getAllOrders(String token) throws AuthenticationException, SQLException {
        if (this.authenticationService.authenticate(token).isPresent()) {
            OrderPersistence orderDAO = dbi.open(OrderPersistence.class);

            List<OrderModel> fetchedOrders = orderDAO.getAllOrders();
            orderDAO.close();

            ProductService ps = new ProductService();
            for (OrderModel order : fetchedOrders) {
                order.setProducts(ps.getProductsByOrderId(order.getOrderId()));
            }

            return fetchedOrders;
        }else{
            return null;
        }
    }

    public List<OrderModel> getOrderFromUser(String token, int userId) throws AuthenticationException, SQLException
    {
        if (this.authenticationService.authenticate(token).isPresent()) {
            OrderPersistence orderDAO = dbi.open(OrderPersistence.class);
            List<OrderModel> fetchedOrders = orderDAO.getOrdersFromUser(userId);
            orderDAO.close();

            ProductService ps = new ProductService();
            for (OrderModel order : fetchedOrders)
            {
                order.setProducts(ps.getProductsByOrderId(order.getOrderId()));
            }

            return fetchedOrders;
        }
        return null;
    }

    public int countAllOrders(String token) throws AuthenticationException{
        if (this.authenticationService.authenticate(token).isPresent() && this.authenticationService.retrieveClaim(token, "superUser") == "true") {
            OrderPersistence orderDAO = dbi.open(OrderPersistence.class);

            int fetchedOrders = orderDAO.countAllOrders();
            orderDAO.close();

            return fetchedOrders;
        } else {
            return 0;
        }
    }



    public int setNewOrder(ContactModel contact, OrdersProductModel[] products, NewUserModel user) throws AuthenticationException, SQLException {
        OrderPersistence orderDAO = dbi.open(OrderPersistence.class);
        if(!user.getEmail().isEmpty() && !user.getPassword().isEmpty()){
            //Create a new user
            UserService us = new UserService();
            Date creationDate = new Date();

            //This method either creates a new user or returns the already existing user.
            UserModel newUser = us.createNewUser(user, creationDate);
            ContactService cs = new ContactService();

            DateFormat format = new SimpleDateFormat("YYYY-MM-dd");
            ContactModel newContact;

            if(new Timestamp(creationDate.getTime()).equals(new Timestamp(newUser.getCreatedAt().getTime()))){
                //New user make this address his favorite
                newContact = cs.createNewContactNawWithUserCoupling(newUser.getUserId(), contact, true);
            }else{
                newContact = cs.createNewContactNawWithUserCoupling(newUser.getUserId(), contact, false);
            }

            int orderSucceeded = orderDAO.insertOrderTransWithUser(newContact, newUser, products);
            orderDAO.close();
            return orderSucceeded;
        }else{
            ContactService cs = new ContactService();
            //This method either creates or returns a new contact
            ContactModel newContact = cs.createNewContactNaw(contact, true);

            int orderSucceeded  = orderDAO.insertOrderTransWithoutUser(newContact, products);
            orderDAO.close();
            return orderSucceeded;
        }
    }

    public int setDeliveryState(String token, int orderId, boolean status) throws AuthenticationException {
        if (this.authenticationService.authenticate(token).isPresent() && this.authenticationService.retrieveClaim(token, "superUser") == "true") {
            OrderPersistence orderDAO = dbi.open(OrderPersistence.class);

            int fetchedOrders = orderDAO.setDeliveryStatus(orderId, status);
            orderDAO.close();

            return fetchedOrders;
        } else {
            return 0;
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
