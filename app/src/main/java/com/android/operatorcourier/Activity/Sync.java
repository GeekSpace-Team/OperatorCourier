package com.android.operatorcourier.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.android.operatorcourier.Api.APIClient;
import com.android.operatorcourier.Api.ApiInterface;
import com.android.operatorcourier.Common.Constant;
import com.android.operatorcourier.Common.Utils;
import com.android.operatorcourier.Fragment.PendingPage;
import com.android.operatorcourier.Model.ChangeOrderPrice;
import com.android.operatorcourier.Model.ChangeOrderProductStatus;
import com.android.operatorcourier.Model.ChangeOrderStatus;
import com.android.operatorcourier.Model.ChangedOrderProduct;
import com.android.operatorcourier.Model.GBody;
import com.android.operatorcourier.Model.OrderDeliveryPriceHistory;
import com.android.operatorcourier.Room.AppDatabase;
import com.android.operatorcourier.Room.ChangedOrderProductDao;
import com.android.operatorcourier.Room.ChangedOrderStatusDao;
import com.android.operatorcourier.Room.OrderDao;
import com.android.operatorcourier.Room.OrderDeliveryPriceDao;
import com.android.operatorcourier.Room.OrderProductDao;
import com.android.operatorcourier.databinding.ActivitySyncBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sync extends AppCompatActivity {
    private Context context = this;
    private ActivitySyncBinding binding;
    private boolean isDone = false;
    private ApiInterface apiInterface;
    private List<ChangeOrderStatus> r1;
    private List<ChangedOrderProduct> r2;
    private List<OrderDeliveryPriceHistory> r3;
    private AppDatabase db;
    private OrderProductDao orderProductDao;
    private ChangedOrderProductDao changedOrderProductDao;
    private OrderDao orderDao;
    private OrderDeliveryPriceDao orderDeliveryPriceDao;
    private ChangedOrderStatusDao changedOrderStatusDao;
    private int r1Index = 0;

    //    To request
    private List<ChangeOrderStatus> orderStatuses = new ArrayList<>();
    private List<String> puids = new ArrayList<>();
    private List<String> reasons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySyncBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = Room.databaseBuilder(context,
                AppDatabase.class, "order").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        orderProductDao = db.orderProductDao();
        changedOrderProductDao = db.changedOrderProductDao();
        orderDao = db.userDao();
        changedOrderStatusDao = db.changedOrderStatusDao();
        orderDeliveryPriceDao = db.orderDeliveryPriceDao();
        apiInterface = APIClient.getClient().create(ApiInterface.class);
        start();
        setListener();
    }

    private void setListener() {
        binding.retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start();
            }
        });
    }

    private void start() {
        MainActivity.get().checkConnection();
        if(Constant.isConnected) {
            r1 = changedOrderStatusDao.get();
            r2 = changedOrderProductDao.getAllWithoutFilter();
            r3 = orderDeliveryPriceDao.getAll();
            binding.retry.setVisibility(View.GONE);
            binding.progress.setProgress(0);
            binding.percent.setText("0%");
            if (r1 != null && r1.size() > 0) {
                startOrderStatus();
            } else if (r2 != null && r2.size() > 0) {
                startOrderProduct();
            } else if (r3 != null && r3.size() > 0) {
                startDeliveryPrice();
            } else {
                done();
            }
        } else {
            finish();
            Toast.makeText(context, "Internet yok!", Toast.LENGTH_SHORT).show();
        }
    }

    private void startOrderStatus() {
        changeOrderStatus();
    }

    private void changeOrderStatus() {
        orderStatuses = getByStatus(Constant.REJECTED);
        changeText("Ýatyrylan sargytlar ugradylýar...");
        sendRejectedOrder(r1Index);
    }

    private void changeText(String text) {
        binding.title.setText(text);
    }

    private void sendRejectedOrder(int i) {
        if (orderStatuses != null && orderStatuses.size() > 0 && i < orderStatuses.size()) {
            setProgressText(orderStatuses, i);
            ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
            ChangeOrderStatus changeOrderStatus = new ChangeOrderStatus(orderStatuses.get(i).getOrders(), orderStatuses.get(i).getReason(), Constant.REJECTED);
            Call<GBody<String>> call = apiInterface.cancelOrder("Bearer " + Utils.getSharedPreference(context, "token"), changeOrderStatus);
            call.enqueue(new Callback<GBody<String>>() {
                @Override
                public void onResponse(Call<GBody<String>> call, Response<GBody<String>> response) {
                    if (response.isSuccessful()) {
                        changedOrderStatusDao.deleteById(orderStatuses.get(i).uid);
                        r1Index++;
                        sendRejectedOrder(r1Index);
                    } else {
                        Toast.makeText(context, "Ýalňyşlyk ýüze çykdy!", Toast.LENGTH_SHORT).show();
                        showError();
                    }
                }

                @Override
                public void onFailure(Call<GBody<String>> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    showError();
                }
            });
        } else {
            orderStatuses = getByStatus(Constant.COURIER_ACCEPTED);
            r1Index = 0;
            changeText("Kabul edilen sargytlar ugradylýar...");
            sendAcceptedOrder(r1Index);
        }
    }

    private <T> void setProgressText(List<T> list, int i) {
        binding.progress.setProgress(getPercentage(list, i));
        binding.percent.setText(getPercentage(list, i) + "%");
    }

    private void showError() {
        isDone = true;
        binding.retry.setVisibility(View.VISIBLE);
    }

    private void sendAcceptedOrder(int i) {
        if (orderStatuses != null && orderStatuses.size() > 0 && i < orderStatuses.size()) {
            setProgressText(orderStatuses, i);
            ChangeOrderStatus changeOrderStatus = new ChangeOrderStatus(orderStatuses.get(i).getOrders(), orderStatuses.get(i).getReason(), Constant.COURIER_ACCEPTED);
            ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
            Call<GBody<String>> call = apiInterface.acceptOrder("Bearer " + Utils.getSharedPreference(context, "token"), changeOrderStatus);
            call.enqueue(new Callback<GBody<String>>() {
                @Override
                public void onResponse(Call<GBody<String>> call, Response<GBody<String>> response) {
                    if (response.isSuccessful()) {
                        changedOrderStatusDao.deleteById(orderStatuses.get(i).uid);
                        r1Index++;
                        sendAcceptedOrder(r1Index);
                    } else {
                        Toast.makeText(context, "Ýalňyşlyk ýüze çykdy!", Toast.LENGTH_SHORT).show();
                        showError();
                    }
                }

                @Override
                public void onFailure(Call<GBody<String>> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    showError();
                }
            });

        } else {
            orderStatuses = getByStatus(Constant.COURIER_DELIVERED);
            r1Index = 0;
            changeText("Eltip berilen sargytlar ugradylýar...");
            sendDeliveredOrder(r1Index);
        }
    }

    private void sendDeliveredOrder(int i) {
        if (orderStatuses != null && orderStatuses.size() > 0 && i < orderStatuses.size()) {
            setProgressText(orderStatuses, i);
            ChangeOrderStatus changeOrderStatus = new ChangeOrderStatus(orderStatuses.get(i).getOrders(), orderStatuses.get(i).getReason(), Constant.COURIER_DELIVERED);
            ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
            Call<GBody<String>> call = apiInterface.deliveryOrder("Bearer " + Utils.getSharedPreference(context, "token"), changeOrderStatus);
            call.enqueue(new Callback<GBody<String>>() {
                @Override
                public void onResponse(Call<GBody<String>> call, Response<GBody<String>> response) {
                    if (response.isSuccessful()) {
                        changedOrderStatusDao.deleteById(orderStatuses.get(i).uid);
                        r1Index++;
                        sendDeliveredOrder(r1Index);
                    } else {
                        Toast.makeText(context, "Ýalňyşlyk ýüze çykdy!", Toast.LENGTH_SHORT).show();
                        showError();
                    }
                }

                @Override
                public void onFailure(Call<GBody<String>> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    showError();
                }
            });
        } else {
            // Send order products changes
            startOrderProduct();
        }
    }

    private void startOrderProduct() {
        puids = getUUIDS(r2);
        changeText("Sargyt harytlarynyň üýtgeşmeleri ugradylýar...");
        r1Index = 0;
        sendProductChanges(r1Index);
    }

    private void sendProductChanges(int i) {
        if (puids != null && puids.size() > 0 && i < puids.size()) {
            setProgressText(puids, r1Index);
            ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
            Call<GBody<String>> call = apiInterface.deliveryOrderProducts("Bearer " + Utils.getSharedPreference(context, "token"),
                    new ChangeOrderProductStatus((ArrayList<ChangedOrderProduct>) getChangedProducts(puids.get(i)), reasons.get(i), puids.get(i)));
            call.enqueue(new Callback<GBody<String>>() {
                @Override
                public void onResponse(Call<GBody<String>> call, Response<GBody<String>> response) {
                    if (response.isSuccessful()) {
                        changedOrderProductDao.truncateTable(puids.get(i));
                        r1Index++;
                        sendProductChanges(r1Index);
                    } else {
                        Toast.makeText(context, "Yalnyshlyk yuze cykdy", Toast.LENGTH_SHORT).show();
                        showError();
                    }
                }

                @Override
                public void onFailure(Call<GBody<String>> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    showError();
                }
            });
        } else {
            startDeliveryPrice();
        }
    }

    private void startDeliveryPrice() {
        changeText("Sargydyň eltip bermek bahalary ugradylýar...");
        r1Index = 0;
        sendOrderDeliveryPrice(r1Index);
    }

    private List<ChangedOrderProduct> getChangedProducts(String unique_id) {
        List<ChangedOrderProduct> temp = new ArrayList<>();
        for (ChangedOrderProduct product : r2) {
            if (product.getCustomer_order_unique_id().equals(unique_id))
                temp.add(product);
        }
        return temp;
    }

    private void sendOrderDeliveryPrice(int i) {
        if (r3 != null && r3.size() > 0 && i < r3.size()) {
            setProgressText(r3, r1Index);
            ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
            Call<GBody<OrderDeliveryPriceHistory>> call = apiInterface.changeOrderDeliveryPrice("Bearer " + Utils.getSharedPreference(context, "token"),
                    new ChangeOrderPrice(r3.get(i).getCustomer_order_unique_id(),
                            r3.get(i).getDelivery_price(),
                            r3.get(i).getReason()));
            call.enqueue(new Callback<GBody<OrderDeliveryPriceHistory>>() {
                @Override
                public void onResponse(Call<GBody<OrderDeliveryPriceHistory>> call, Response<GBody<OrderDeliveryPriceHistory>> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getBody() != null) {
                        orderDeliveryPriceDao.truncateTable(r3.get(i).getCustomer_order_unique_id());
                        r1Index++;
                        sendOrderDeliveryPrice(r1Index);
                    } else {
                        Toast.makeText(context, "Yalnyshlyk yuze cykdy!", Toast.LENGTH_SHORT).show();
                        showError();
                    }
                }

                @Override
                public void onFailure(Call<GBody<OrderDeliveryPriceHistory>> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    showError();
                }
            });
        } else {
            isDone = true;
            done();
        }
    }

    private void done() {
        changeText("Üstünlikli ugradyldy!");
        MaterialAlertDialogBuilder alert = new MaterialAlertDialogBuilder(context);
        alert.setTitle("Maglumatlar serwera ugradyldy!");
        alert.setMessage("Çykmak üçin yza bas");
        alert.setPositiveButton("Yza", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        alert.show();
    }

    private List<String> getUUIDS(List<ChangedOrderProduct> list) {
        reasons.clear();
        puids.clear();
        for (ChangedOrderProduct product : list) {
            if (!puids.contains(product.getCustomer_order_unique_id())) {
                puids.add(product.getCustomer_order_unique_id());
                reasons.add(getReason(product.getCustomer_order_unique_id()));
            }
        }
        return puids;
    }

    private String getReason(String uuid) {
        String reason = "";
        for (ChangedOrderProduct product : r2) {
            if (product.getCustomer_order_unique_id().equals(uuid) && !product.getReason().isEmpty()) {
                reason = product.getReason();
            }
        }
        return reason;
    }

    @Override
    public void onBackPressed() {
        if (isDone)
            super.onBackPressed();
    }

    private List<ChangeOrderStatus> getByStatus(String status) {
        List<ChangeOrderStatus> temp = new ArrayList<>();
        for (ChangeOrderStatus order : r1) {
            if (order.getStatus().equals(status))
                temp.add(order);
        }
        return temp;
    }

    private <T> int getPercentage(List<T> list, int index) {
        index += 1;
        if (list != null && list.size() > 0)
            return (index / list.size()) * 100;
        else
            return 100;
    }
}