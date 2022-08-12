package com.android.operatorcourier.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class Order {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "unique_id")
    private String unique_id;

    @ColumnInfo(name = "is_express")
    private Boolean is_express;

    @ColumnInfo(name = "created_at")
    private String created_at;

    @ColumnInfo(name = "updated_at")
    private String updated_at;

    @ColumnInfo(name = "additional_information")
    private String additional_information;

    @ColumnInfo(name = "customer_unique_id")
    private String customer_unique_id;

    @ColumnInfo(name = "operator_unique_id")
    private String operator_unique_id;

    @ColumnInfo(name = "fullname")
    private String fullname;

    @ColumnInfo(name = "phone_number")
    private String phone_number;

    @ColumnInfo(name = "order_address_history")
    private ArrayList<OrderAddressHistory> order_address_history=new ArrayList<>();

    @ColumnInfo(name = "order_date_history")
    private ArrayList<OrderDateHistory> order_date_history=new ArrayList<>();

    @ColumnInfo(name = "order_location_history")
    private ArrayList<OrderLocationHistory> order_location_history=new ArrayList<>();

    @ColumnInfo(name = "order_price_history")
    private ArrayList<OrderDeliveryPriceHistory> order_price_history=new ArrayList<>();

    @ColumnInfo(name = "current_status")
    private String current_status;

    public Order(String id, String unique_id, Boolean is_express, String created_at, String updated_at, String additional_information, String customer_unique_id, String operator_unique_id, String fullname, String phone_number, ArrayList<OrderAddressHistory> order_address_history, ArrayList<OrderDateHistory> order_date_history, ArrayList<OrderLocationHistory> order_location_history, ArrayList<OrderDeliveryPriceHistory> order_price_history, String current_status) {
        this.id = id;
        this.unique_id = unique_id;
        this.is_express = is_express;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.additional_information = additional_information;
        this.customer_unique_id = customer_unique_id;
        this.operator_unique_id = operator_unique_id;
        this.fullname = fullname;
        this.phone_number = phone_number;
        this.order_address_history = order_address_history;
        this.order_date_history = order_date_history;
        this.order_location_history = order_location_history;
        this.order_price_history = order_price_history;
        this.current_status = current_status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public Boolean getIs_express() {
        return is_express;
    }

    public void setIs_express(Boolean is_express) {
        this.is_express = is_express;
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

    public String getAdditional_information() {
        return additional_information;
    }

    public void setAdditional_information(String additional_information) {
        this.additional_information = additional_information;
    }

    public String getCustomer_unique_id() {
        return customer_unique_id;
    }

    public void setCustomer_unique_id(String customer_unique_id) {
        this.customer_unique_id = customer_unique_id;
    }

    public String getOperator_unique_id() {
        return operator_unique_id;
    }

    public void setOperator_unique_id(String operator_unique_id) {
        this.operator_unique_id = operator_unique_id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public ArrayList<OrderAddressHistory> getOrder_address_history() {
        return order_address_history;
    }

    public void setOrder_address_history(ArrayList<OrderAddressHistory> order_address_history) {
        this.order_address_history = order_address_history;
    }

    public ArrayList<OrderDateHistory> getOrder_date_history() {
        return order_date_history;
    }

    public void setOrder_date_history(ArrayList<OrderDateHistory> order_date_history) {
        this.order_date_history = order_date_history;
    }

    public ArrayList<OrderLocationHistory> getOrder_location_history() {
        return order_location_history;
    }

    public void setOrder_location_history(ArrayList<OrderLocationHistory> order_location_history) {
        this.order_location_history = order_location_history;
    }

    public ArrayList<OrderDeliveryPriceHistory> getOrder_price_history() {
        return order_price_history;
    }

    public void setOrder_price_history(ArrayList<OrderDeliveryPriceHistory> order_price_history) {
        this.order_price_history = order_price_history;
    }

    public String getCurrent_status() {
        return current_status;
    }

    public void setCurrent_status(String current_status) {
        this.current_status = current_status;
    }
}
