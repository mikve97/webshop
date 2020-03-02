package mappers;

import com.mysql.cj.protocol.Resultset;
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
    public OrderModel map(int index, ResultSet r, StatementContext ctx) throws SQLException {

        return new OrderModel(r.getInt("order_id"),
                r.getInt("user_id"),
                r.getDate("created_on"),
                new ArrayList<>());

    }
}
