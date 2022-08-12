package com.android.operatorcourier.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class OrderDeliveryPriceHistory {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "customer_order_unique_id")
    private String customer_order_unique_id;

    @ColumnInfo(name = "user_unique_id")
    private String user_unique_id;

    @ColumnInfo(name = "delivery_price")
    private String delivery_price;

    @ColumnInfo(name = "reason")
    private String reason;

    @ColumnInfo(name = "created_at")
    private String created_at;

    @ColumnInfo(name = "updated_at")
    private String updated_at;

    public OrderDeliveryPriceHistory(String id, String customer_order_unique_id, String user_unique_id, String delivery_price, String reason, String created_at, String updated_at) {
        this.id = id;
        this.customer_order_unique_id = customer_order_unique_id;
        this.user_unique_id = user_unique_id;
        this.delivery_price = delivery_price;
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

    public String getDelivery_price() {
        return delivery_price;
    }

    public void setDelivery_price(String delivery_price) {
        this.delivery_price = delivery_price;
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
