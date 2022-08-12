package com.android.operatorcourier.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.android.operatorcourier.Model.OrderDeliveryPriceHistory;
import com.android.operatorcourier.Model.OrderProduct;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface OrderDeliveryPriceDao {
    @Query("SELECT * FROM `orderdeliverypricehistory`")
    List<OrderDeliveryPriceHistory> getAll();

    @Insert
    void insert(OrderDeliveryPriceHistory priceHistory);

    @Delete
    void delete(OrderDeliveryPriceHistory priceHistory);

    @Query("DELETE FROM `orderdeliverypricehistory` WHERE customer_order_unique_id = :customer_order_unique_id")
    void truncateTable(String customer_order_unique_id);

}
