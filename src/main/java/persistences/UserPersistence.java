package persistences;

import models.UserModel;
import mappers.UserMapper;


import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;


@RegisterMapper(UserMapper.class)
public interface UserPersistence {
    @SqlQuery("SELECT * FROM account WHERE email = :email")
    UserModel getUserByEmail(@Bind("email") String email);

    @SqlQuery("SELECT * FROM account WHERE email = :username AND password = :password")
    UserModel getUserByUsernameAndPassword(@Bind("username") String username, @Bind("password") String password);

    void close();
}