package reducers;

import models.OrderModel;
import models.ProductModel;
import org.jdbi.v3.core.result.LinkedHashMapRowReducer;
import org.jdbi.v3.core.result.RowView;

import java.util.Map;

public class OrderReducer implements LinkedHashMapRowReducer<Integer, OrderModel> {
    @Override
    public void accumulate(Map<Integer, OrderModel> map, RowView rowView) {
        OrderModel o = map.computeIfAbsent(rowView.getColumn("f_id", Integer.class),
                id -> rowView.getRow(OrderModel.class));

        if (rowView.getColumn("order_id", Integer.class) != null) {
            o.getProducts().add(rowView.getRow(ProductModel.class));
        }
    }
}
