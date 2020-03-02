import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.UnsupportedEncodingException;

/**
 * @author Ali Rezaa Ghariebiyan
 */
public class MainConfiguration extends Configuration {
    @NotEmpty
    private String jwtTokenSecret;
    
    @JsonProperty("jwt-secret")
    public byte[] getJwtTokenSecret() throws UnsupportedEncodingException {
        return jwtTokenSecret.getBytes("UTF-8");
    }

    @JsonProperty("jwt-secret")
    public void setJwtTokenSecret(String jwtTokenSecret) {
        this.jwtTokenSecret = jwtTokenSecret;
    }

	@Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @NotNull
    private String apiKey;

    @NotNull
    private String userId;
    
	@JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory factory) {
        this.database = factory;
    }

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    public void setApiKey(String apiKey){
        this.apiKey = apiKey;
    }

    public String getApiKey(){
        return this.apiKey;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getUserId(){
        return this.userId;
    }

}
