package kr.hnu.ice.product.model;

public class Product {
    private String productId;
    private String sellerId;
    private String title;
    private int price;
    private String category;
    private String description;
    private String status;
    private String createdAt;
    private String imageUrl;

    public Product() {
    }

    public Product(String productId, String sellerId, String title, int price, String category, String description, String status, String createdAt) {
        this.productId = productId;
        this.sellerId = sellerId;
        this.title = title;
        this.price = price;
        this.category = category;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}