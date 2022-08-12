package com.android.operatorcourier.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.android.operatorcourier.Model.ChangedOrderProduct;
import com.android.operatorcourier.Model.OrderProduct;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ChangedOrderProductDao {
    @Query("SELECT * FROM `changedorderproduct` WHERE customer_order_unique_id=:customer_order_unique_id")
    List<ChangedOrderProduct> getAll(String customer_order_unique_id);

    @Query("SELECT * FROM `changedorderproduct`")
    List<ChangedOrderProduct> getAllWithoutFilter();

    @Insert
    void insertAll(ArrayList<ChangedOrderProduct> products);

    @Delete
    void delete(ChangedOrderProduct product);

    @Query("DELETE FROM `changedorderproduct` WHERE customer_order_unique_id = :customer_order_unique_id")
    void truncateTable(String customer_order_unique_id);
}
