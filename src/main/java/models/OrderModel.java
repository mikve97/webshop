package models;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderModel {

    private int orderId;
    private int userId;
    private Date orderDate;
    private List<ProductModel> products;

    /**
     * Basic model so we can return our projects to our frond-end
     */
    public OrderModel() {
        // Jackson deserialization
    }

    public OrderModel(int orderId, int userId, Date orderDate, List<ProductModel> products){
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.products = products;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public List<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductModel> products) {
        this.products = products;
    }
}
