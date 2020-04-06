package mappers;

import models.UserModel;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * With the mapper the resultset of the database can be converted into an object
 *
 * @author Mike van Es
 */

public class UserMapper implements ResultSetMapper<UserModel> {

    @Override
    public UserModel map(int i, ResultSet rs, StatementContext statementContext) throws SQLException {

        return new UserModel(
                rs.getInt("user_id"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getBoolean("super_user"),
                rs.getDate("created_at")
        );
    }
}