package mappers;

import models.ProductModel;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper implements ResultSetMapper<ProductModel> {

    @Override
    public ProductModel map(int i, ResultSet rs, StatementContext statementContext) throws SQLException {
        return new ProductModel(
                rs.getInt("product_id"),
                rs.getString("product_name"),
                rs.getString("product_description"),
                rs.getDouble("price"),
                rs.getInt("product_category_id"),
                rs.getString("category_name"),
                rs.getBoolean("in_stock")
        );
    }
}
