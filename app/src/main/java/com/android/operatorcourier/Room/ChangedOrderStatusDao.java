package com.android.operatorcourier.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.android.operatorcourier.Model.ChangeOrderStatus;
import com.android.operatorcourier.Model.ChangedOrderProduct;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ChangedOrderStatusDao {
    @Query("SELECT * FROM `changeorderstatus`")
    List<ChangeOrderStatus> get();

    @Insert
    void insert(ChangeOrderStatus orderStatus);

    @Delete
    void delete(ChangeOrderStatus orderStatus);

    @Query("DELETE FROM `changeorderstatus` WHERE uid = :uuid")
    void deleteById(int uuid);

}
