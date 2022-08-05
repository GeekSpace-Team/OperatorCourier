package com.android.operatorcourier.Model;

public class OrderAddressHistory {
    private String id;
    private String customer_order_unique_id;
    private String address;
    private String user_unique_id;
    private String created_at;
    private String updated_at;
    private String reason;

    public OrderAddressHistory(String id, String customer_order_unique_id, String address, String user_unique_id, String created_at, String updated_at, String reason) {
        this.id = id;
        this.customer_order_unique_id = customer_order_unique_id;
        this.address = address;
        this.user_unique_id = user_unique_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.reason = reason;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUser_unique_id() {
        return user_unique_id;
    }

    public void setUser_unique_id(String user_unique_id) {
        this.user_unique_id = user_unique_id;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
