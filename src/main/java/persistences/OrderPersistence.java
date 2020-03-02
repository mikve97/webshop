package persistences;

import mappers.OrderMapper;
import mappers.ProductMapper;
import models.OrderModel;
import models.ProductModel;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;
/**
 * @author Mike van Es
 */

public interface OrderPersistence {

    /**
     * @author Mike van Es
     * @return List<OrderModel> orders
     */
    @RegisterMapper(OrderMapper.class)
    @SqlQuery("SELECT o.* FROM orders o")
    List<OrderModel> getAllOrders();

    @RegisterMapper(ProductMapper.class)
    @SqlQuery("SELECT p.*, pc.* FROM orders_product op LEFT JOIN product p ON op.product_id = p.product_id LEFT JOIN product_category pc ON p.product_category_id = pc.product_category_id WHERE op.order_id = :oId ")
    List<ProductModel> getAllProductsByOrderId(@Bind("oId") int orderId);


    void close();
}
