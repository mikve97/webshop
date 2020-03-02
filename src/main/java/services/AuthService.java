package services;

import static org.jose4j.jws.AlgorithmIdentifiers.HMAC_SHA256;

import java.sql.SQLException;

import javax.ws.rs.core.Response;

import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;
import org.skife.jdbi.v2.DBI;

import models.CredentialModel;
import models.UserModel;
import persistences.UserPersistence;
import nl.dfbackend.git.util.DbConnector;

/**
 * All functions are performed in this service.
 * This also opens the database connection in order to subsequently access the DAO querys.
 * Authorization is also performed in this Service and the JWT token will be generated.
 *
 * @author Ali Rezaa Ghariebiyan
 * @version 08-11-2019
 */

public class AuthService {
    private final byte[] tokenSecret;
    private DBI dbi;
    private UserPersistence loginDAO;

    public AuthService(byte[] token) throws SQLException{
        this.tokenSecret = token;
        DbConnector.getInstance();
		dbi = DbConnector.getDBI();
    }

    /**
     * In this method it is checked whether the user exists,
     * and then it is checked whether the provided password actually fits the user.
     * If the user data is incorrect. Then a status response becomes UNAUTHORIZED and otherwise it returns OK.
     * If the data is correct then a token is also generated and it is stored in the User model.
     *
     * @author Ali Rezaa Ghariebiyan
     * @version 08-11-2019
     * @return Response status
     * @throws SQLException 
     */
    public Response onLogin(CredentialModel credential) throws SQLException {
        loginDAO = dbi.open(UserPersistence.class);
        
        UserModel user = loginDAO.getUserByUsername(credential.getUsername());

        loginDAO.close();

        if(user == null) {
            //unauthorized unknown username
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        // if the password of the user model does not match that of the password of the credential model,
        // a response UNAUTHORIZED is returned.
        if(!user.getPassword().equals(credential.getPassword())) {
            //unauthorized password does not match
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        // create token
        String token = generateToken(user.getUserId());
        user.setAuthToken(token);

        // user is returned. next to the response.
        return Response.status(Response.Status.OK).entity(user).build();
    }


    /**
     * In this method the JWT token is generated.
     *
     * @author Ali Rezaa Ghariebiyan
     * @version 08-11-2019
     * @param userId
     */

    private String generateToken(int userId) {
        final JwtClaims claims = new JwtClaims();
        claims.setExpirationTimeMinutesInTheFuture(-20);
        claims.setClaim("id", userId);

        final JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setAlgorithmHeaderValue(HMAC_SHA256);
        jws.setKey(new HmacKey(tokenSecret));

        try {
            return jws.getCompactSerialization();
        } catch (JoseException e) {
            e.printStackTrace();
        }
        return null;
    }
}