package com.android.operatorcourier.Model;

public class OrderDateHistory {
    private String id;
    private String customer_order_unique_id;
    private String order_date;
    private String order_time;
    private String user_unique_id;
    private String created_at;
    private String updated_at;
    private String reason;

    public OrderDateHistory(String id, String customer_order_unique_id, String order_date, String order_time, String user_unique_id, String created_at, String updated_at, String reason) {
        this.id = id;
        this.customer_order_unique_id = customer_order_unique_id;
        this.order_date = order_date;
        this.order_time = order_time;
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

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
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
