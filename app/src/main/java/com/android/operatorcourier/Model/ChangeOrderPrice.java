package com.android.operatorcourier.Model;

public class ChangeOrderPrice {
    private String order_unique_id;
    private String delivery_price;
    private String reason;

    public ChangeOrderPrice(String order_unique_id, String delivery_price, String reason) {
        this.order_unique_id = order_unique_id;
        this.delivery_price = delivery_price;
        this.reason = reason;
    }

    public String getOrder_unique_id() {
        return order_unique_id;
    }

    public void setOrder_unique_id(String order_unique_id) {
        this.order_unique_id = order_unique_id;
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
}
