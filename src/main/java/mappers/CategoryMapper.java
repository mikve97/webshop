package mappers;

import models.CategoryModel;
import models.ProductModel;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMapper implements ResultSetMapper<CategoryModel> {

    @Override
    public CategoryModel map(int i, ResultSet rs, StatementContext statementContext) throws SQLException {
        return new CategoryModel(
                rs.getInt("product_category_id"),
                rs.getString("category_name")
        );
    }
}
