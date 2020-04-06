package models;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderModel {

    private int orderId;
    private int userId;
    private int contactNawId;
    private Date orderDate;
    private ContactModel contact;
    private List<ProductModel> products;
    private boolean delivered;

    /**
     * Basic model so we can return our projects to our frond-end
     */
    public OrderModel() {
        // Jackson deserialization
    }

    public OrderModel(int orderId, int userId, int contactNawId, Date orderDate, ContactModel contact, List<ProductModel> products, boolean delivered){
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.contact = contact;
        this.products = products;
        this.contactNawId = contactNawId;
        this.delivered = delivered;
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

    public ContactModel getContact() {
        return contact;
    }

    public void setContact(ContactModel contact) {
        this.contact = contact;
    }

    public boolean getDelivered() {
        return delivered;
    }

    public void setDeliverd(boolean delivered) {
        this.delivered = delivered;
    }
}
