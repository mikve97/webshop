package persistences;

import mappers.ProductMapper;
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

	/**
	 * @author Mike van Es
	 * @return List<ProductModel> products
	 */
	@SqlQuery("SELECT * FROM product p LEFT JOIN product_category pc ON p.product_category_id = pc.product_category_id")
	List<ProductModel> getAllProducts();

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

	void close();
	
}
