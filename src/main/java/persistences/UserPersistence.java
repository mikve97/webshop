package persistences;

import models.UserModel;
import mappers.UserMapper;


import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;


@RegisterMapper(UserMapper.class)
public interface UserPersistence {
    @SqlQuery("SELECT * FROM account WHERE email = :email")
    UserModel getUserByEmail(@Bind("email") String email);


    @SqlUpdate("INSERT INTO account(email, password, contact_naw_id) VALUES(:username, :password, :cw)")
    int createNewUser(@Bind("username") String username, @Bind("password") String password, @Bind("cw") int contact_naw_id);

    void close();
}