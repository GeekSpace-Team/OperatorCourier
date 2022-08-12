package com.android.operatorcourier.Common;

import androidx.room.TypeConverter;

import com.android.operatorcourier.Model.OrderAddressHistory;
import com.android.operatorcourier.Model.OrderDateHistory;
import com.android.operatorcourier.Model.OrderDeliveryPriceHistory;
import com.android.operatorcourier.Model.OrderLocationHistory;
import com.android.operatorcourier.Model.SelectedOrder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Converters {
    @TypeConverter
    public static ArrayList<OrderAddressHistory> fromString(String value) {
        Type listType = new TypeToken<ArrayList<OrderAddressHistory>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<OrderAddressHistory> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    //    Order date
    @TypeConverter
    public static ArrayList<OrderDateHistory> fromStringDate(String value) {
        Type listType = new TypeToken<ArrayList<OrderDateHistory>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayListDate(ArrayList<OrderDateHistory> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    //    Order location
    @TypeConverter
    public static ArrayList<OrderLocationHistory> fromStringLocation(String value) {
        Type listType = new TypeToken<ArrayList<OrderLocationHistory>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayListLocation(ArrayList<OrderLocationHistory> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    //    Order delivery price
    @TypeConverter
    public static ArrayList<OrderDeliveryPriceHistory> fromStringPrice(String value) {
        Type listType = new TypeToken<ArrayList<OrderDeliveryPriceHistory>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayListDeliveryPrice(ArrayList<OrderDeliveryPriceHistory> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    //   Selected orders
    @TypeConverter
    public static ArrayList<SelectedOrder> fromStringSelected(String value) {
        Type listType = new TypeToken<ArrayList<SelectedOrder>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayListSelected(ArrayList<SelectedOrder> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
