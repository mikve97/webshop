package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NewOrder {

    @JsonProperty
    String contactNaw;

    @JsonProperty String products;
    @JsonProperty String newUser;




    @JsonCreator
    public NewOrder(@JsonProperty("contactNaw") String contactNaw,
                            @JsonProperty("products") String products,
                    @JsonProperty("newAccount") String newUser){
        this.contactNaw = contactNaw;
        this.products = products;
        this.newUser = newUser;
    }

    public String getContactNaw() {
        return this.contactNaw;
    }

    public void setContactNaw(String contactNaw) {
        this.contactNaw = contactNaw;
    }

    public String getProducts() {
        return this.products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getNewUser() {
        return newUser;
    }

    public void setNewUser(String newUser) {
        this.newUser = newUser;
    }

}
