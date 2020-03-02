package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MyEntity {
	@JsonProperty String username;
	
	@JsonProperty String password;
	
	@JsonCreator
    public MyEntity(@JsonProperty("username") String username,
                     @JsonProperty("password") String password){
        this.username = username;
        this.password = password;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
