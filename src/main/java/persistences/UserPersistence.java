package persistences;

import models.UserModel;
import mappers.UserMapper;


import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.Date;


@RegisterMapper(UserMapper.class)
public interface UserPersistence {
    @SqlQuery("SELECT * FROM account WHERE email = :email")
    UserModel getUserByEmail(@Bind("email") String email);

    @SqlQuery("SELECT count(user_id) FROM account")
    int countAllUsers();


    @SqlUpdate("INSERT INTO account(email, password, created_at) VALUES(:username, :password, :ca)")
    int createNewUser(@Bind("username") String username, @Bind("password") String password,  @Bind("ca") Date created_at);

    void close();
}