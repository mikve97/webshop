package services;

import models.NewUserModel;
import models.UserModel;
import org.mindrot.jbcrypt.BCrypt;
import org.skife.jdbi.v2.DBI;
import persistences.UserPersistence;

import java.sql.SQLException;

public class UserService {
    private DBI dbi;
    private UserPersistence userDAO;

    // Define the BCrypt workload to use when generating password hashes.
    private int workload = 12;

    public UserService() throws SQLException {
        util.DbConnector.getInstance();
        dbi = util.DbConnector.getDBI();
    }
    public UserModel createNewUser(NewUserModel user, int contact_naw_id){
        UserPersistence userDAO = dbi.open(UserPersistence.class);
        UserModel foundUser = userDAO.getUserByEmail(user.getEmail());

        if(foundUser == null){
            String hashedPassword = this.hashPassword(user.getPassword());
            int row = userDAO.createNewUser(user.getEmail(), hashedPassword, contact_naw_id);
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

    private  String hashPassword(String password_plaintext) {
        String salt = BCrypt.gensalt(this.workload);
        String hashed_password = BCrypt.hashpw(password_plaintext, salt);

        return(hashed_password);
    }

}
