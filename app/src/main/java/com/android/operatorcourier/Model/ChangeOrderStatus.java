package com.android.operatorcourier.Model;

import java.util.ArrayList;

public class ChangeOrderStatus {
    private ArrayList<SelectedOrder> orders=new ArrayList<>();
    private String reason;

    public ChangeOrderStatus(ArrayList<SelectedOrder> orders, String reason) {
        this.orders = orders;
        this.reason = reason;
    }

    public ArrayList<SelectedOrder> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<SelectedOrder> orders) {
        this.orders = orders;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
