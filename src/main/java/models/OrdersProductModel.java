package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class OrdersProductModel
{
    @NotNull
    private int id;
    @NotNull
    private int orderId;
    @NotNull
    private int productId;
    @NotNull
    private int productAmount;
    @NotNull
    private ProductModel pm;

    public OrdersProductModel(int id, int orderId, int productId, int productAmount, ProductModel pm)
    {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.productAmount = productAmount;
        this.pm = pm;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return this.orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return this.productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductAmount() {
        return this.productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    public ProductModel getPm() {
        return this.pm;
    }

    public void setPm(ProductModel pm) {
        this.pm = pm;
    }
}