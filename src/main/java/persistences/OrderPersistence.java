package persistences;

import mappers.OrderMapper;
import mappers.ProductMapper;
import models.*;
import org.skife.jdbi.v2.exceptions.TransactionException;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.Transaction;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;


import java.util.Date;
import java.util.List;
/**
 * @author Mike van Es
 */

public abstract class  OrderPersistence {

    /**
     * @author Mike van Es
     * @return List<OrderModel> orders
     */
    @Transaction
    public  int insertOrderTransWithoutUser(ContactModel contact, OrdersProductModel[] products){
        try {
            Date insertDate = new Date();
            int orderId = insertOrder(contact.getContactNawId(), insertDate);
            if(orderId == 1){
//              Only continue of the order line was created, we should only insert one order at a time.
                OrderModel order = getLatestOrderFromContact(contact.getContactNawId(), insertDate);
//              Insert orderLines
                for (OrdersProductModel pm : products){
                    int orderLine = insertOrderLines(order.getOrderId(), pm.getProductId(),  pm.getProductAmount());
                    if(orderLine != 1){
                        //Revert transaction
                        throw new TransactionException("Something went worng whilst inserting orderlines, nothing was inserted");
                    }
                }
                return order.getOrderId();
            }
        }catch (Exception e){
            //Something went worng, we should rollback
            throw new TransactionException("nothing inserted");
        }

        return -1;
    }


    /**
     * @author Mike van Es
     * @return List<OrderModel> orders
     */

    public  int insertOrderTransWithUser(ContactModel contact, UserModel user, OrdersProductModel[] products){
        try {
            Date insertDate = new Date();
            int orderId = insertOrderWithUser(contact.getContactNawId(), user.getUserId(), insertDate);

            if(orderId == 1){
//              Only continue of the order line was created, we should only insert one order at a time.
                OrderModel order = getLatestOrderFromContactAndUser(contact.getContactNawId(), user.getUserId(), insertDate);
//              Insert orderLines
                for (OrdersProductModel pm : products){
                    System.out.println(pm.getProductId());
                    int orderLine = insertOrderLines(order.getOrderId(), pm.getProductId(), pm.getProductAmount());
                    if(orderLine != 1){
                        //Revert transaction
                        throw new TransactionException("Something went worng whilst inserting orderlines, nothing was inserted");
                    }
                }
                return order.getOrderId();
            }
        }catch (Exception e){
            //Something went worng, we should rollback
            throw new TransactionException("nothing inserted");
        }

        return -1;
    }

    @RegisterMapper(OrderMapper.class)
    @SqlQuery("SELECT o.*, cn.* FROM orders o \n" +
            "LEFT JOIN  contact_naw cn ON cn.contact_naw_id = o.contact_naw_id GROUP BY o.order_id, cn.contact_naw_id")
    public abstract List<OrderModel> getAllOrders();

    @RegisterMapper(OrderMapper.class)
    @SqlQuery("SELECT count(order_id) FROM orders o")
    public abstract int countAllOrders();

    @RegisterMapper(OrderMapper.class)
    @SqlQuery("SELECT o.*, cn.* FROM orders o LEFT JOIN  account_contact_coupling acc ON acc.contact_naw_id = o.contact_naw_id \n" +
            "LEFT JOIN  contact_naw cn ON cn.contact_naw_id = acc.contact_naw_id WHERE acc.account_id = :uId GROUP BY o.order_id, cn.contact_naw_id")
    public abstract List<OrderModel> getOrdersFromUser(@Bind("uId") int userId);

    @RegisterMapper(OrderMapper.class)
    @SqlQuery("SELECT o.*, cn.* FROM orders o LEFT JOIN  account_contact_coupling acc ON acc.contact_naw_id = o.contact_naw_id \n" +
            " LEFT JOIN  contact_naw cn ON cn.contact_naw_id = acc.contact_naw_id WHERE o.contact_naw_id = :cni AND o.created_at = :cAt ORDER BY o.created_at, o.order_id DESC LIMIT 1")
    public abstract OrderModel getLatestOrderFromContact(@Bind("cni") int cni, @Bind("cAt") Date createdAt);

    @RegisterMapper(OrderMapper.class)
    @SqlQuery("SELECT o.*, cn.* FROM orders o LEFT JOIN  account_contact_coupling acc ON acc.contact_naw_id = o.contact_naw_id \n" +
            "LEFT JOIN  contact_naw cn ON cn.contact_naw_id = acc.contact_naw_id  WHERE o.contact_naw_id = :cni AND o.user_id = :uid AND o.created_at = :cAt ORDER BY o.created_at, o.order_id DESC LIMIT 1")
    public abstract OrderModel getLatestOrderFromContactAndUser(@Bind("cni") int cni, @Bind("uid") int user_id, @Bind("cAt") Date createdAt);

    @SqlUpdate("INSERT INTO orders (contact_naw_id,  created_at) VALUES(:cni, :cAt)")
    public abstract int insertOrder(@Bind("cni") int contact_naw_id, @Bind("cAt") Date createdAt);

    @SqlUpdate("INSERT INTO orders (contact_naw_id, user_id,  created_at) VALUES(:cni, :uid, :cAt)")
    public abstract int insertOrderWithUser(@Bind("cni") int contact_naw_id, @Bind("uid") int user_id, @Bind("cAt") Date createdAt);

    @SqlUpdate("INSERT INTO orders_product (order_id,  product_id, product_amount) VALUES(:oi, :pi, :pA)")
    public abstract int insertOrderLines(@Bind("oi") int order_id, @Bind("pi") int product_id, @Bind("pA") int productAmount);

    @SqlUpdate("UPDATE orders \n" +
            "SET delivered = :status \n" +
            "WHERE order_id = :oId;")
    public abstract int setDeliveryStatus(@Bind("oId") int order_id, @Bind("status") boolean status);

    @SqlQuery("SELECT id FROM postalcodes WHERE postalcode = :pc")
    public abstract int getPostalCode(@Bind("pc") String pc);

    public abstract void close();


}
