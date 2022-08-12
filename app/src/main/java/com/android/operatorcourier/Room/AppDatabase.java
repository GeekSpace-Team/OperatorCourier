package com.android.operatorcourier.Room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.android.operatorcourier.Common.Converters;
import com.android.operatorcourier.Model.ChangeOrderStatus;
import com.android.operatorcourier.Model.ChangedOrderProduct;
import com.android.operatorcourier.Model.Order;
import com.android.operatorcourier.Model.OrderDeliveryPriceHistory;
import com.android.operatorcourier.Model.OrderProduct;

@Database(entities = {Order.class,OrderProduct.class, ChangedOrderProduct.class, ChangeOrderStatus.class, OrderDeliveryPriceHistory.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract OrderDao userDao();
    public abstract OrderProductDao orderProductDao();
    public abstract ChangedOrderProductDao changedOrderProductDao();
    public abstract ChangedOrderStatusDao changedOrderStatusDao();
    public abstract OrderDeliveryPriceDao orderDeliveryPriceDao();
}
