package persistences;

import mappers.ProductMap;
import models.ProductModel;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * @author Mike van Es
 */
@RegisterMapper(ProductMap.class)
public interface ProductPersistence {

	/**
	 * @author Mike van Es
	 * @return List<ProductModel> products
	 */
	@SqlQuery("SELECT * FROM product p LEFT JOIN product_category pc ON p.product_category_id = pc.product_category_id")
	List<ProductModel> getAllProducts();


	@SqlUpdate("INSERT INTO product (product_category_id, product_name, product_description, price)\n" + "VALUES (:pCatId, :pName, :pDescr, :pPrice );")
	int setProduct(@Bind("pName") String name, @Bind("pDescr") String descr, @Bind("pPrice") Double price, @Bind("pCatId") int catId);

	
}
