package com.android.operatorcourier.Model;

public class OrderLocationHistory {
    private String id;
    private String customer_order_unique_id;
    private String user_unique_id;
    private String latitude;
    private String longitude;
    private String reason;
    private String created_at;
    private String updated_at;

    public OrderLocationHistory(String id, String customer_order_unique_id, String user_unique_id, String latitude, String longitude, String reason, String created_at, String updated_at) {
        this.id = id;
        this.customer_order_unique_id = customer_order_unique_id;
        this.user_unique_id = user_unique_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.reason = reason;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomer_order_unique_id() {
        return customer_order_unique_id;
    }

    public void setCustomer_order_unique_id(String customer_order_unique_id) {
        this.customer_order_unique_id = customer_order_unique_id;
    }

    public String getUser_unique_id() {
        return user_unique_id;
    }

    public void setUser_unique_id(String user_unique_id) {
        this.user_unique_id = user_unique_id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
