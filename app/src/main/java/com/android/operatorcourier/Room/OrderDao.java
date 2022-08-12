package com.android.operatorcourier.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.android.operatorcourier.Model.Order;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface OrderDao {
    @Query("SELECT * FROM `order` WHERE current_status = :status ORDER BY uid DESC")
    List<Order> getAll(String status);

    @Insert
    void insertAll(ArrayList<Order> users);

    @Insert
    void insert(Order order);

    @Delete
    void delete(Order user);


    @Query("DELETE FROM `order` WHERE current_status = :status OR current_status IS NULL")
    void truncateTable(String status);

    @Query("UPDATE `order` SET current_status=:status WHERE unique_id IN (:ids)")
    void updateSelected(String[] ids,String status);


    @Query("DELETE FROM `order` WHERE unique_id = :unique_id")
    void deleteById(String unique_id);



}
