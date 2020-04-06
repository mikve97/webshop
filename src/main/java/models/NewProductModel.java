package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NewProductModel {
    @JsonProperty
    private String name;
    @JsonProperty
    private String productDescription;
    @JsonProperty
    private double price;
    @JsonProperty
    private int productCatId;

    @JsonCreator
    public NewProductModel( @JsonProperty("name")String name,  @JsonProperty("productDescription")String productDescription,
                            @JsonProperty("productPrice")double price, @JsonProperty("productCatId")int productCatId) {
        this.name = name;
        this.productDescription = productDescription;
        this.price = price;
        this.productCatId = productCatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getProductCatId() {
        return productCatId;
    }

    public void setProductCatId(int productCatId) {
        this.productCatId = productCatId;
    }
}
