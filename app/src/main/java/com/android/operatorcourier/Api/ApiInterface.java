package com.android.operatorcourier.Api;

import com.android.operatorcourier.Model.CancelReason;
import com.android.operatorcourier.Model.ChangeOrderPrice;
import com.android.operatorcourier.Model.ChangeOrderProductStatus;
import com.android.operatorcourier.Model.ChangeOrderStatus;
import com.android.operatorcourier.Model.GBody;
import com.android.operatorcourier.Model.Login;
import com.android.operatorcourier.Model.LoginBody;
import com.android.operatorcourier.Model.Order;
import com.android.operatorcourier.Model.OrderDeliveryPriceHistory;
import com.android.operatorcourier.Model.OrderProduct;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("get-order-by-status?")
    Call<GBody<ArrayList<Order>>> getOrderByStatus(@Header("Authorization") String token, @Query("status") String status);

    @POST("auth/sign-in")
    Call<GBody<Login>> login(@Body LoginBody body);

    @POST("accept-order")
    Call<GBody<String>> acceptOrder(@Header("Authorization") String token, @Body ChangeOrderStatus body);

    @POST("delivery-order")
    Call<GBody<String>> deliveryOrder(@Header("Authorization") String token, @Body ChangeOrderStatus body);

    @POST("cancel-order")
    Call<GBody<String>> cancelOrder(@Header("Authorization") String token, @Body ChangeOrderStatus body);

    @GET("get-cancel-reason?")
    Call<GBody<ArrayList<CancelReason>>> getCancelReasons(@Header("Authorization") String token, @Query("id") String id);

    @GET("get-order-product-history?")
    Call<GBody<ArrayList<OrderProduct>>> getOrderProducts(@Header("Authorization") String token,@Query("order_unique_id") String unique_id);

    @PUT("change-order-delivery-price")
    Call<GBody<OrderDeliveryPriceHistory>> changeOrderDeliveryPrice(@Header("Authorization") String token, @Body ChangeOrderPrice body);

    @POST("delivery-order-producs")
    Call<GBody<String>> deliveryOrderProducts(@Header("Authorization") String token, @Body ChangeOrderProductStatus body);

}
