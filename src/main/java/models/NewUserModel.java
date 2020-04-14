package models;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.mindrot.jbcrypt.BCrypt;

public class NewUserModel {

    @JsonProperty
    @NotEmpty
    String email;

    @JsonProperty
    @NotEmpty
    @Length(min=6)
    String password;

    private int workload = 12;

    @JsonCreator
    public NewUserModel(@JsonProperty("email") String email, @JsonProperty("password")String password){
        this.email = email;
        this.password = this.hashPassword(password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = this.hashPassword(password);
    }

    private  String hashPassword(String password_plaintext) {
        String salt = BCrypt.gensalt(this.workload);

        return (BCrypt.hashpw(password_plaintext, salt));
    }
}
