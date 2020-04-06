package models;

import java.util.List;

public class ProductModel {
    private int productId;
    private String name;
    private String description;
    private double price;
    private int categoryId;
    private String categoryName;
    private boolean inStock;



    /**
     * Basic model so we can return our projects to our frond-end
     */
    public ProductModel() {
        // Jackson deserialization
    }
    public ProductModel(int id, String name, String description, double price, int catId, String categoryName, boolean inStock) {
        this.productId = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryId = catId;
        this.categoryName = categoryName;
        this.inStock = inStock;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
       this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }
}
