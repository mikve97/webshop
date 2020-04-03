package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;

/**
 * If credentials are given in JSON format from the front-end, through the given link /{username}/{password}
 * a response will be returned from the onLogin method.
 * The given parameters will be saved in the model 'Credential'.
 * This can be OK or UNAUTHORIZED.
 *
 * @author Ali Rezaa Ghariebiyan
 * @version 08-11-2019
 */

public class CredentialModel {
    @JsonProperty
    @NotNull
    private String email;

    @JsonProperty
    @NotNull
    private String password;

    @JsonCreator
    public CredentialModel(@JsonProperty("email") String email,
                           @JsonProperty("password") String password) {
        System.out.println(email);
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}