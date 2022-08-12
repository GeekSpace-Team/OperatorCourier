package com.android.operatorcourier.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class ChangeOrderStatus {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "orders")
    private ArrayList<SelectedOrder> orders=new ArrayList<>();

    @ColumnInfo(name = "reason")
    private String reason;

    @ColumnInfo(name = "status")
    private String status;

    public ChangeOrderStatus(ArrayList<SelectedOrder> orders, String reason, String status) {
        this.orders = orders;
        this.reason = reason;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
