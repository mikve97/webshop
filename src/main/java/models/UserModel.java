package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.Date;


public class UserModel implements Principal {

    @JsonProperty
    @NotNull
    private int userId;
    @JsonProperty
    @NotNull
    private String email;
    @JsonProperty
    @NotNull
    private String password;
    @JsonProperty
    @NotNull
    private String authToken;
    @JsonProperty
    @NotNull
    private boolean superUser;

    @JsonProperty
    @NotNull
    private Date createdAt;

    //constructor
    @JsonCreator
    public UserModel(@JsonProperty("userId") Integer userId,
                     @JsonProperty("email") String email,
                     @JsonProperty("password") String password,
                     @JsonProperty("superUser") boolean superUser,
                     @JsonProperty("createdAt") Date createdAt){

        this.userId = userId;
        this.email = email;
        this.password = password;
        this.superUser = superUser;
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String username) {
        this.email = email;
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
    public boolean getSuperUser(){
        return this.superUser;
    }
    @Override
    public String getName() {
        return this.email;
    }
}