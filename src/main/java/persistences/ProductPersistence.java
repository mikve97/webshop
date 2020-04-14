package persistences;

import mappers.CategoryMapper;
import mappers.OrdersProductMapper;
import mappers.ProductMapper;
import models.CategoryModel;
import models.OrdersProductModel;
import models.ProductModel;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;


import java.util.List;

/**
 * @author Mike van Es
 */
@RegisterMapper(ProductMapper.class)
public interface ProductPersistence {

	@RegisterMapper({OrdersProductMapper.class})
	@SqlQuery("SELECT p.*, pc.*, op.* FROM orders_product op LEFT JOIN product p ON op.product_id = p.product_id LEFT JOIN product_category pc ON p.product_category_id = pc.product_category_id WHERE op.order_id = :oId ")
	List<OrdersProductModel> getAllProductsByOrderId(@Bind("oId") int paramInt);

	/**
	 * @author Mike van Es
	 * @return List<ProductModel> products
	 */
	@SqlQuery("SELECT * FROM product p LEFT JOIN product_category pc ON p.product_category_id = pc.product_category_id")
	List<ProductModel> getAllProducts();


	@SqlQuery("SELECT * FROM product p LEFT JOIN product_category pc ON p.product_category_id = pc.product_category_id WHERE p.in_stock = TRUE;")
	List<ProductModel> getAllProductsInStock();

	/**
	 * @author Mike van Es
	 * @return List<ProductModel> products
	 */
	@SqlQuery("SELECT * FROM product p LEFT JOIN product_category pc ON p.product_category_id = pc.product_category_id WHERE p.product_id = :pId")
	ProductModel getProducts(@Bind("pId") int productId);

	/**
	 *
	 * @param name
	 * @param descr
	 * @param price
	 * @param catId
	 * @return The number of rows edited
	 */
	@SqlUpdate("INSERT INTO product (product_category_id, product_name, product_description, price)\n" + "VALUES (:pCatId, :pName, :pDescr, :pPrice );")
	int setProduct(@Bind("pName") String name, @Bind("pDescr") String descr, @Bind("pPrice") Double price, @Bind("pCatId") int catId);

	@SqlUpdate("UPDATE product  \n" +
			"SET in_stock = :stock \n" +
			"WHERE product_id = :pId;  ")
	int setStock(@Bind("pId") int productId, @Bind("stock") boolean in_stock);



	@RegisterMapper(CategoryMapper.class)
	@SqlQuery("SELECT * FROM product_category p")
	List<CategoryModel> getAllCategory();

	void close();

}
