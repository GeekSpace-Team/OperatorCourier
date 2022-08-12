package com.android.operatorcourier.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.android.operatorcourier.Model.OrderProduct;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface OrderProductDao {
    @Query("SELECT * FROM `orderproduct` WHERE customer_order_unique_id=:customer_order_unique_id ORDER BY uid DESC")
    List<OrderProduct> getAll(String customer_order_unique_id);

    @Insert
    void insertAll(ArrayList<OrderProduct> products);

    @Delete
    void delete(OrderProduct product);

    @Query("DELETE FROM `orderproduct` WHERE customer_order_unique_id = :customer_order_unique_id")
    void truncateTable(String customer_order_unique_id);
}
