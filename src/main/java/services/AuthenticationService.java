package services;

import java.sql.SQLException;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import models.CredentialModel;
import models.UserModel;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.skife.jdbi.v2.DBI;
import persistences.OrderPersistence;
import persistences.UserPersistence;


/**
 * @author Mike van Es
 */
public class AuthenticationService implements Authenticator<String, UserModel> {
	private AuthorisationService authorisationService;
    private DBI dbi;
    private UserPersistence userDAO;
	
	public AuthenticationService() throws SQLException {
		this.authorisationService = new AuthorisationService();
		util.DbConnector.getInstance();
		dbi = util.DbConnector.getDBI();
	}

	@Override
	public Optional<UserModel> authenticate(String jwtoken) throws AuthenticationException {
		try {
			UserModel user = null;
			
			if(this.authorisationService.decodeJWToken(jwtoken)) {
				UserPersistence userDAO = dbi.open(UserPersistence.class);
		        
		        user = userDAO.getUserByEmail(this.authorisationService.retrieveClaim(jwtoken, "sub"));
		        
		        userDAO.close();
			}
			
			return Optional.of(user);
		} catch (Exception e) {
			throw new AuthenticationException("The user is not authenticated.");
		}
	}

	public String retrieveClaim(String token, String claim){
		return this.authorisationService.retrieveClaim(token, claim);
	}
	
	/**
	 * @param credential
	 * @return Optional<UserModel>
	 * @throws SQLException
	 */
	public String authenticateUser(CredentialModel credential) throws SQLException {
		UserPersistence userDAO = dbi.open(UserPersistence.class);
		UserModel user = userDAO.getUserByEmail(credential.getEmail());
		System.out.println(user);
		if(user != null && this.checkPassword(credential.getPassword(), user.getPassword())){
			user.setAuthToken(authorisationService.encodeJWToken(user.getEmail(), user.getUserId()));
			userDAO.close();
			return user.getAuthToken();
		}else{
			return null;
		}

		 

    }

	public boolean checkPassword(String password_plaintext, String stored_hash) {
		boolean password_verified = false;

		if(null == stored_hash || !stored_hash.startsWith("$2a$"))
			throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");

		password_verified = BCrypt.checkpw(password_plaintext, stored_hash);

		return(password_verified);
	}


}
