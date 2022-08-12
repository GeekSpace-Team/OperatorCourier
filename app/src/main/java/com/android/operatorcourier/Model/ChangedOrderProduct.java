package com.android.operatorcourier.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ChangedOrderProduct {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "customer_order_unique_id")
    private String customer_order_unique_id;

    @ColumnInfo(name = "product_name")
    private String product_name;

    @ColumnInfo(name = "product_brand")
    private String product_brand;

    @ColumnInfo(name = "product_model")
    private String product_model;

    @ColumnInfo(name = "product_artikul_code")
    private String product_artikul_code;

    @ColumnInfo(name = "product_debt_price")
    private String product_debt_price;

    @ColumnInfo(name = "product_cash_price")
    private String product_cash_price;

    @ColumnInfo(name = "product_discount")
    private String product_discount;

    @ColumnInfo(name = "product_size")
    private String product_size;

    @ColumnInfo(name = "product_color")
    private String product_color;

    @ColumnInfo(name = "product_count")
    private String product_count;

    @ColumnInfo(name = "unique_id")
    private String unique_id;

    @ColumnInfo(name = "created_at")
    private String created_at;

    @ColumnInfo(name = "updated_at")
    private String updated_at;

    @ColumnInfo(name = "reason")
    private String reason;

    @ColumnInfo(name = "operator_unique_id")
    private String operator_unique_id;

    @ColumnInfo(name = "order_product_status")
    private String order_product_status;

    public ChangedOrderProduct(String id, String customer_order_unique_id, String product_name, String product_brand, String product_model, String product_artikul_code, String product_debt_price, String product_cash_price, String product_discount, String product_size, String product_color, String product_count, String unique_id, String created_at, String updated_at, String reason, String operator_unique_id, String order_product_status) {
        this.id = id;
        this.customer_order_unique_id = customer_order_unique_id;
        this.product_name = product_name;
        this.product_brand = product_brand;
        this.product_model = product_model;
        this.product_artikul_code = product_artikul_code;
        this.product_debt_price = product_debt_price;
        this.product_cash_price = product_cash_price;
        this.product_discount = product_discount;
        this.product_size = product_size;
        this.product_color = product_color;
        this.product_count = product_count;
        this.unique_id = unique_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.reason = reason;
        this.operator_unique_id = operator_unique_id;
        this.order_product_status = order_product_status;
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

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_brand() {
        return product_brand;
    }

    public void setProduct_brand(String product_brand) {
        this.product_brand = product_brand;
    }

    public String getProduct_model() {
        return product_model;
    }

    public void setProduct_model(String product_model) {
        this.product_model = product_model;
    }

    public String getProduct_artikul_code() {
        return product_artikul_code;
    }

    public void setProduct_artikul_code(String product_artikul_code) {
        this.product_artikul_code = product_artikul_code;
    }

    public String getProduct_debt_price() {
        return product_debt_price;
    }

    public void setProduct_debt_price(String product_debt_price) {
        this.product_debt_price = product_debt_price;
    }

    public String getProduct_cash_price() {
        return product_cash_price;
    }

    public void setProduct_cash_price(String product_cash_price) {
        this.product_cash_price = product_cash_price;
    }

    public String getProduct_discount() {
        return product_discount;
    }

    public void setProduct_discount(String product_discount) {
        this.product_discount = product_discount;
    }

    public String getProduct_size() {
        return product_size;
    }

    public void setProduct_size(String product_size) {
        this.product_size = product_size;
    }

    public String getProduct_color() {
        return product_color;
    }

    public void setProduct_color(String product_color) {
        this.product_color = product_color;
    }

    public String getProduct_count() {
        return product_count;
    }

    public void setProduct_count(String product_count) {
        this.product_count = product_count;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
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

    public String getOperator_unique_id() {
        return operator_unique_id;
    }

    public void setOperator_unique_id(String operator_unique_id) {
        this.operator_unique_id = operator_unique_id;
    }

    public String getOrder_product_status() {
        return order_product_status;
    }

    public void setOrder_product_status(String order_product_status) {
        this.order_product_status = order_product_status;
    }
}
