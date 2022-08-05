package com.android.operatorcourier.Model;

import com.google.gson.annotations.SerializedName;

public class CancelReason {
    private String id;
    private String unique_id;
    private String sell_point_id;
    @SerializedName("reason")
    private String text;

    public CancelReason(String id, String unique_id, String sell_point_id, String text) {
        this.id = id;
        this.unique_id = unique_id;
        this.sell_point_id = sell_point_id;
        this.text = text;
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

    public String getSell_point_id() {
        return sell_point_id;
    }

    public void setSell_point_id(String sell_point_id) {
        this.sell_point_id = sell_point_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
