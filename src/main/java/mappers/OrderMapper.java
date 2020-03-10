package mappers;

import models.OrderModel;
import models.ProductModel;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderMapper implements ResultSetMapper<OrderModel> {
    @Override
    public OrderModel map(int i, ResultSet rs, StatementContext statementContext) throws SQLException {
        return new OrderModel(rs.getInt("order_id"),
                rs.getInt("user_id"),
                rs.getInt("contact_naw_id"),
                rs.getDate("created_at"),
                new ArrayList<>());
    }
}
