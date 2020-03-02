package persistences;

import models.UserModel;
import mappers.UserMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * Here the query is executed to see if the user exists in the database
 *
 * @author Ali Rezaa Ghariebiyan
 * @version 08-11-2019
 */

@RegisterMapper(UserMapper.class)
public interface UserPersistence {
    @SqlQuery("SELECT * FROM df_user WHERE username = :username")
    UserModel getUserByUsername(@Bind("username") String username);

    @SqlQuery("SELECT * FROM df_user WHERE username = :username AND password = :password")
    UserModel getUserByUsernameAndPassword(@Bind("username") String username, @Bind("password") String password);

    void close();
}