package mappers;

import models.ProductModel;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper implements ResultSetMapper<ProductModel> {

    @Override
    public ProductModel map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new ProductModel(
                r.getInt("product_id"),
                r.getString("product_name"),
                r.getString("product_description"),
                r.getDouble("price"),
                r.getInt("product_category_id"),
                r.getString("category_name")
        );
    }
}
