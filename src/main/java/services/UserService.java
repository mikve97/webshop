package services;

import io.dropwizard.auth.AuthenticationException;
import models.NewUserModel;
import models.UserModel;
import org.mindrot.jbcrypt.BCrypt;
import org.skife.jdbi.v2.DBI;
import persistences.OrderPersistence;
import persistences.UserPersistence;

import java.sql.SQLException;
import java.util.Date;

public class UserService {
    private DBI dbi;
    private UserPersistence userDAO;

    private AuthenticationService authenticationService;

    public UserService() throws SQLException {

        this.authenticationService = new AuthenticationService();
        util.DbConnector.getInstance();
        dbi = util.DbConnector.getDBI();
    }
    public UserModel createNewUser(NewUserModel user, Date created_at){
        UserPersistence userDAO = dbi.open(UserPersistence.class);
        UserModel foundUser = userDAO.getUserByEmail(user.getEmail());

        if(foundUser == null){
            int row = userDAO.createNewUser(user.getEmail(), user.getPassword(), created_at);
            UserModel newUser = userDAO.getUserByEmail(user.getEmail());
            userDAO.close();

            if(row == 1){
                return newUser;
            }else{
                return null;
            }
        }else{
            return foundUser;
        }


    }

    public int countAllUsers(String token) throws AuthenticationException {
        if (this.authenticationService.authenticate(token).isPresent() && this.authenticationService.retrieveClaim(token, "superUser") == "true") {
            UserPersistence userDAO = dbi.open(UserPersistence.class);

            int fetchedUsers = userDAO.countAllUsers();
            userDAO.close();

            return fetchedUsers;
        } else {
            return 0;
        }
    }

}
