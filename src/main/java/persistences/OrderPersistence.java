package persistences;

import mappers.OrderMapper;
import mappers.ProductMapper;
import models.ContactModel;
import models.OrderModel;
import models.ProductModel;
import models.UserModel;
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
    public  int insertOrderTransWithoutUser(ContactModel contact, ProductModel[] products){
        try {
            Date insertDate = new Date();
            int orderId = insertOrder(contact.getContactNawId(), insertDate);
            if(orderId == 1){
//              Only continue of the order line was created, we should only insert one order at a time.
                OrderModel order = getLatestOrderFromContact(contact.getContactNawId(), insertDate);
//              Insert orderLines
                for (ProductModel pm : products){
                    int orderLine = insertOrderLines(order.getOrderId(), pm.getProductId());
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
    @Transaction
    public  int insertOrderTransWithUser(ContactModel contact, UserModel user, ProductModel[] products){
        try {
            Date insertDate = new Date();
            int orderId = insertOrderWithUser(contact.getContactNawId(), user.getUserId(), insertDate);
            if(orderId == 1){
//              Only continue of the order line was created, we should only insert one order at a time.
                OrderModel order = getLatestOrderFromContactAndUser(contact.getContactNawId(), user.getUserId(), insertDate);
//              Insert orderLines
                for (ProductModel pm : products){
                    int orderLine = insertOrderLines(order.getOrderId(), pm.getProductId());
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
    @SqlQuery("SELECT o.* FROM orders o")
    public abstract List<OrderModel> getAllOrders();

    @RegisterMapper(OrderMapper.class)
    @SqlQuery("SELECT o.* FROM orders o WHERE o.contact_naw_id = :cni AND created_at = :cAt ORDER BY created_at DESC LIMIT 1")
    public abstract OrderModel getLatestOrderFromContact(@Bind("cni") int cni, @Bind("cAt") Date createdAt);

    @RegisterMapper(OrderMapper.class)
    @SqlQuery("SELECT o.* FROM orders o WHERE o.contact_naw_id = :cni AND o.user_id = :uid AND created_at = :cAt ORDER BY created_at DESC LIMIT 1")
    public abstract OrderModel getLatestOrderFromContactAndUser(@Bind("cni") int cni, @Bind("uid") int user_id, @Bind("cAt") Date createdAt);

    @RegisterMapper(ProductMapper.class)
    @SqlQuery("SELECT p.*, pc.* FROM orders_product op LEFT JOIN product p ON op.product_id = p.product_id LEFT JOIN product_category pc ON p.product_category_id = pc.product_category_id WHERE op.order_id = :oId ")
    public abstract List<ProductModel> getAllProductsByOrderId(@Bind("oId") int orderId);


    @SqlUpdate("INSERT INTO orders (contact_naw_id,  created_at) VALUES(:cni, :cAt)")
    public abstract int insertOrder(@Bind("cni") int contact_naw_id, @Bind("cAt") Date createdAt);

    @SqlUpdate("INSERT INTO orders (contact_naw_id, user_id,  created_at) VALUES(:cni, :uid, :cAt)")
    public abstract int insertOrderWithUser(@Bind("cni") int contact_naw_id, @Bind("uid") int user_id, @Bind("cAt") Date createdAt);

    @SqlUpdate("INSERT INTO orders_product (order_id,  product_id) VALUES(:oi, :pi)")
    public abstract int insertOrderLines(@Bind("oi") int order_id, @Bind("pi") int product_id);

    @SqlQuery("SELECT id FROM postalcodes WHERE postalcode = :pc")
    public abstract int getPostalCode(@Bind("pc") String pc);

    public abstract void close();


}
