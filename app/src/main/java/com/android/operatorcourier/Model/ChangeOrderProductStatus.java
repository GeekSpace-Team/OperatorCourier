package com.android.operatorcourier.Model;

import java.util.ArrayList;

public class ChangeOrderProductStatus {
    private ArrayList<ChangedOrderProduct> order_products=new ArrayList<>();
    private String reason;
    private String order_unique_id;

    public ChangeOrderProductStatus(ArrayList<ChangedOrderProduct> order_products, String reason, String order_unique_id) {
        this.order_products = order_products;
        this.reason = reason;
        this.order_unique_id = order_unique_id;
    }

    public ArrayList<ChangedOrderProduct> getOrder_products() {
        return order_products;
    }

    public void setOrder_products(ArrayList<ChangedOrderProduct> order_products) {
        this.order_products = order_products;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getOrder_unique_id() {
        return order_unique_id;
    }

    public void setOrder_unique_id(String order_unique_id) {
        this.order_unique_id = order_unique_id;
    }
}
