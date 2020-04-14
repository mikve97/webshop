package mappers;

import models.UserModel;

import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * With the mapper the resultset of the database can be converted into an object
 *
 * @author Mike van Es
 */

public class UserMapper implements ResultSetMapper<UserModel> {

    @Override
    public UserModel map(int i, ResultSet rs, StatementContext statementContext) throws SQLException {
        Timestamp timestamp = rs.getTimestamp("created_at");
        Date date = new Date(timestamp.getTime());

        return new UserModel(
                rs.getInt("user_id"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getBoolean("super_user"),
                date
        );
    }
}