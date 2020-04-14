package mappers;

import models.OrdersProductModel;
import models.ProductModel;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrdersProductMapper implements ResultSetMapper<OrdersProductModel>
{
    @Override
    public OrdersProductModel map(int i, ResultSet rs, StatementContext statementContext)
            throws SQLException
    {
        ProductModel pm = new ProductModel(rs.getInt("product_id"),
                rs.getString("product_name"),
                rs.getString("product_description"),
                rs.getDouble("price"),
                rs.getInt("product_category_id"),
                rs.getString("category_name"),
                rs.getBoolean("in_stock"));

        return new OrdersProductModel(rs.getInt("order_product_id"),
                rs.getInt("order_id"),
                rs.getInt("product_id"),
                rs.getInt("product_amount"),
                pm);
    }

}