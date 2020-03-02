package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import java.security.Principal;

/**
 *
 * @author Ali Rezaa Ghariebiyan
 * @version 08-11-2019
 */

public class UserModel implements Principal {

    @JsonProperty
    @NotNull
    private int userId;
    @JsonProperty
    @NotNull
    private String username;
    @JsonProperty
    @NotNull
    private String password;
    @JsonProperty
    @NotNull
    private String authToken;

    //constructor
    @JsonCreator
    public UserModel(@JsonProperty("userId") Integer userId,
                     @JsonProperty("username") String username,
                     @JsonProperty("password") String password){

        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        password = password;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    @Override
    public String getName() {
        return username;
    }
}