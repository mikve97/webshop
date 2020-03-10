package persistences;

import mappers.OrderMapper;
import mappers.ProductMapper;
import models.OrderModel;
import models.ProductModel;
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
    public  int insertOrderTrans(int userId, Date insertDate){
        try {
            int orderId = insertOrder(userId, insertDate);
            System.out.println(orderId);
            if(orderId == 1){
                //Only continue of the order line was created, we should only insert one order at a time.
                OrderModel order = getLatestOrderFromuUser(userId, insertDate);
                //Insert orderLines

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
    @SqlQuery("SELECT o.* FROM orders o WHERE o.user_id = :uId AND created_at = :cAt ORDER BY created_at DESC LIMIT 1")
    public abstract OrderModel getLatestOrderFromuUser(@Bind("uId") int userId, @Bind("cAt") Date createdAt);

    @RegisterMapper(ProductMapper.class)
    @SqlQuery("SELECT p.*, pc.* FROM orders_product op LEFT JOIN product p ON op.product_id = p.product_id LEFT JOIN product_category pc ON p.product_category_id = pc.product_category_id WHERE op.order_id = :oId ")
    public abstract List<ProductModel> getAllProductsByOrderId(@Bind("oId") int orderId);


    @SqlUpdate("INSERT INTO orders (contact_naw_id, user_id, created_at) VALUES(1, :uId, :cAt)")
    public abstract int insertOrder(@Bind("uId") int userID, @Bind("cAt") Date createdAt);

    public abstract void close();


}
