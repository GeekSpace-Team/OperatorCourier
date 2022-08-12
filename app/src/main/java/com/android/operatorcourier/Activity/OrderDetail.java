package com.android.operatorcourier.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.operatorcourier.Adapter.OrderProductAdapter;
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
import com.android.operatorcourier.Model.Order;
import com.android.operatorcourier.Model.OrderDeliveryPriceHistory;
import com.android.operatorcourier.Model.OrderProduct;
import com.android.operatorcourier.R;
import com.android.operatorcourier.Room.AppDatabase;
import com.android.operatorcourier.Room.ChangedOrderProductDao;
import com.android.operatorcourier.Room.OrderDao;
import com.android.operatorcourier.Room.OrderDeliveryPriceDao;
import com.android.operatorcourier.Room.OrderProductDao;
import com.android.operatorcourier.databinding.ActivityOrderDetailBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetail extends AppCompatActivity {
    public static Order order = null;
    public static String status = "";
    private ActivityOrderDetailBinding binding;
    private Double cashPrice = 0.0;
    private Double debtPrice = 0.0;
    private Double totalPrice = 0.0;
    private Context context = this;
    private ArrayList<OrderProduct> products = new ArrayList<>();
    public static ArrayList<OrderProduct> delivered = new ArrayList<>();
    private AppDatabase db;
    private OrderProductDao orderProductDao;
    private ChangedOrderProductDao changedOrderProductDao;
    private OrderDao orderDao;
    private OrderDeliveryPriceDao orderDeliveryPriceDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = Room.databaseBuilder(context,
                AppDatabase.class, "order").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        orderProductDao=db.orderProductDao();
        changedOrderProductDao=db.changedOrderProductDao();
        orderDao=db.userDao();
        orderDeliveryPriceDao=db.orderDeliveryPriceDao();
        delivered.clear();
        setViews();
        setListener();
        request();
        editPrice();
    }

    private void editPrice() {
        if(status.equals(Constant.COURIER_ACCEPTED)){
            binding.save.setVisibility(View.VISIBLE);
        } else {
            binding.save.setVisibility(View.GONE);
        }
        if (status.equals(Constant.DELIVERED) || status.equals(Constant.COURIER_DELIVERED) || status.equals(Constant.REJECTED)) {
            binding.editPriceButton.setVisibility(View.GONE);
            binding.editDeliveryPriceContainer.setVisibility(View.GONE);

        }
        if (order.getOrder_price_history() != null && order.getOrder_price_history().size() > 0) {
            for (int i = order.getOrder_price_history().size() - 1; i >= 0; i--) {
                if (order.getOrder_price_history().get(i).getUser_unique_id().equals(Utils.getSharedPreference(context, "unique_id"))) {
                    binding.newDeliveryPrice.setText(order.getOrder_price_history().get(i).getDelivery_price());
                    binding.reason.setText(order.getOrder_price_history().get(i).getReason());
                    binding.edit.setEnabled(false);
                    binding.newDeliveryPrice.setEnabled(false);
                    binding.reason.setEnabled(false);
                    binding.editDeliveryPriceContainer.setVisibility(View.VISIBLE);
                    binding.editPriceButton.setVisibility(View.GONE);
                    binding.edit.setVisibility(View.GONE);
                    binding.newPriceTitle.setText("Üýtgedilen baha:");
                    break;
                }
            }
        }
    }

    private void request() {
        if(Constant.isConnected){
            binding.loading.setVisibility(View.VISIBLE);
            ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
            Call<GBody<ArrayList<OrderProduct>>> call = apiInterface.getOrderProducts("Bearer " + Utils.getSharedPreference(this, "token"), order.getUnique_id());
            call.enqueue(new Callback<GBody<ArrayList<OrderProduct>>>() {
                @Override
                public void onResponse(Call<GBody<ArrayList<OrderProduct>>> call, Response<GBody<ArrayList<OrderProduct>>> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getBody() != null && response.body().getBody().size() > 0) {
                        setAdapter(response.body().getBody());
                        calculatePrice(response.body().getBody());
                        products = response.body().getBody();
                        orderProductDao.truncateTable(order.getUnique_id());
                        orderProductDao.insertAll(products);
                    } else {
                        Toast.makeText(OrderDetail.this, "Sargyda degishli haryt tapylmady", Toast.LENGTH_SHORT).show();
                    }
                    binding.loading.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<GBody<ArrayList<OrderProduct>>> call, Throwable t) {
                    binding.loading.setVisibility(View.GONE);
                    Toast.makeText(OrderDetail.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            binding.loading.setVisibility(View.GONE);
            products= (ArrayList<OrderProduct>) orderProductDao.getAll(order.getUnique_id());
            setAdapter(products);
            calculatePrice(products);
        }
    }

    private void calculatePrice(ArrayList<OrderProduct> body) {
        totalPrice = 0.0;
        if (body != null && body.size() > 0) {
            for (OrderProduct product : body) {
                try {
                    double a = Double.parseDouble(product.getProduct_debt_price()) + Double.parseDouble(product.getProduct_cash_price());
                    if (product.getProduct_discount() != null && !product.getProduct_discount().isEmpty()) {
                        double discount = Double.parseDouble(product.getProduct_discount());
                        double b = (discount / 100) * a;
                        a = a - b;
                    }
                    totalPrice += a;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (order.getOrder_price_history() != null && order.getOrder_price_history().size() > 0)
                totalPrice += Double.parseDouble(order.getOrder_price_history().get(order.getOrder_price_history().size() - 1).getDelivery_price());
            binding.total.setText(totalPrice + " TMT");
        }
    }

    private void setAdapter(ArrayList<OrderProduct> body) {
        binding.rec.setAdapter(new OrderProductAdapter(body, this,status));
        binding.rec.setLayoutManager(new LinearLayoutManager(this));
        binding.rec.setNestedScrollingEnabled(false);
        binding.rec.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    private void setListener() {
        binding.editPriceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.editDeliveryPriceContainer.getVisibility() == View.VISIBLE)
                    binding.editDeliveryPriceContainer.setVisibility(View.GONE);
                else
                    binding.editDeliveryPriceContainer.setVisibility(View.VISIBLE);
            }
        });

        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (delivered.size() == 0) {
                    // Rejected all
                    showAlert(Constant.REJECTED);
                } else {
                    if (delivered.size() != products.size()) {
                        // Rejected some but delivered
                        showAlert(Constant.COURIER_DELIVERED);
                    } else {
                        // Delivered all
                        changeStatus("", Constant.COURIER_DELIVERED);
                    }
                }

            }
        });

        binding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.get().checkConnection();
                if (binding.newDeliveryPrice.getText().toString().trim().isEmpty()
                        || binding.reason.getText().toString().trim().isEmpty()) {
                    Toast.makeText(context, "Gerekli maglumatlary girizin!", Toast.LENGTH_SHORT).show();
                } else {


                    if(Constant.isConnected){
                        Dialog dialog = Utils.getDialogProgressBar(context);
                        dialog.show();
                        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
                        Call<GBody<OrderDeliveryPriceHistory>> call = apiInterface.changeOrderDeliveryPrice("Bearer " + Utils.getSharedPreference(context, "token"),
                                new ChangeOrderPrice(order.getUnique_id(), binding.newDeliveryPrice.getText().toString(), binding.reason.getText().toString()));
                        call.enqueue(new Callback<GBody<OrderDeliveryPriceHistory>>() {
                            @Override
                            public void onResponse(Call<GBody<OrderDeliveryPriceHistory>> call, Response<GBody<OrderDeliveryPriceHistory>> response) {
                                if (response.isSuccessful() && response.body() != null && response.body().getBody() != null) {
                                    doneEdit(response.body().getBody());
                                } else {
                                    Toast.makeText(OrderDetail.this, "Yalnyshlyk yuze cykdy!", Toast.LENGTH_SHORT).show();
                                }
                                dialog.dismiss();
                            }

                            @Override
                            public void onFailure(Call<GBody<OrderDeliveryPriceHistory>> call, Throwable t) {
                                Toast.makeText(OrderDetail.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                    } else {
                        OrderDeliveryPriceHistory history=new OrderDeliveryPriceHistory("",order.getUnique_id(),
                                Utils.getSharedPreference(context,"unique_id"),
                                binding.newDeliveryPrice.getText().toString(),
                                binding.reason.getText().toString(),
                                "",
                                "");
                        orderDeliveryPriceDao.insert(history);

                        Order newOrder=order;
                        ArrayList<OrderDeliveryPriceHistory> hs=new ArrayList<>();
                        if(newOrder.getOrder_price_history()!=null && newOrder.getOrder_price_history().size()>0){
                            hs.addAll(newOrder.getOrder_price_history());
                        }
                        hs.add(history);
                        newOrder.setOrder_price_history(hs);
                        orderDao.deleteById(order.getUnique_id());
                        orderDao.insert(newOrder);
                        PendingPage.get().request();
                        doneEdit(history);
                    }
                }
            }
        });
    }

    private void doneEdit(OrderDeliveryPriceHistory body) {
        binding.edit.setEnabled(false);
        binding.newDeliveryPrice.setEnabled(false);
        binding.reason.setEnabled(false);
        binding.editDeliveryPriceContainer.setVisibility(View.VISIBLE);
        binding.editPriceButton.setVisibility(View.GONE);
        Toast.makeText(OrderDetail.this, "Eltip bermek bahasy uytgedi", Toast.LENGTH_SHORT).show();
        order.getOrder_price_history().add(body);
        binding.newPriceTitle.setText("Üýtgedilen baha:");
        calculatePrice(products);
        binding.deliveryPrice.setText(binding.newDeliveryPrice.getText().toString() + " TMT");
        binding.edit.setVisibility(View.GONE);
    }

    private void showAlert(String status) {
        final EditText input = new EditText(context);
        input.setHint("Sebabi...");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        new AlertDialog.Builder(context)
                .setTitle("Sargyt doly eltip berilmedi! Sebäbini giriziň!")
                .setPositiveButton("Ugrat", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(input.getText().toString().trim().isEmpty()){
                            Toast.makeText(OrderDetail.this, "Sebabini girizin!", Toast.LENGTH_SHORT).show();
                        } else {
                            changeStatus(input.getText().toString(),status);
                        }
                    }
                })
                .setNegativeButton("Aýyr", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setIcon(R.drawable.ic_baseline_warning_24)
                .setView(input)
                .show();

    }

    private void changeStatus(String reason, String status) {
        MainActivity.get().checkConnection();
        ArrayList<ChangedOrderProduct> ps = new ArrayList<>();
        ArrayList<OrderProduct> ps1 = new ArrayList<>();
        for (OrderProduct pr : products) {
            if (!delivered.contains(pr)) {
                pr.setOrder_product_status(Constant.REJECTED);
            } else {
                pr.setOrder_product_status(Constant.COURIER_DELIVERED);
            }
            ps1.add(pr);
            ps.add(new ChangedOrderProduct(
                    pr.getId(),
                    pr.getCustomer_order_unique_id(),
                    pr.getProduct_name(),
                    pr.getProduct_brand(),
                    pr.getProduct_model(),
                    pr.getProduct_artikul_code(),
                    pr.getProduct_debt_price(),
                    pr.getProduct_cash_price(),
                    pr.getProduct_discount(),
                    pr.getProduct_size(),
                    pr.getProduct_color(),
                    pr.getProduct_count(),
                    pr.getUnique_id(),
                    pr.getCreated_at(),
                    pr.getUpdated_at(),
                    reason,
                    pr.getOperator_unique_id(),
                    pr.getOrder_product_status()));
        }
        if(Constant.isConnected){
            Dialog dialog=Utils.getDialogProgressBar(context);
            dialog.show();
            ApiInterface apiInterface=APIClient.getClient().create(ApiInterface.class);
            Call<GBody<String>> call=apiInterface.deliveryOrderProducts("Bearer "+Utils.getSharedPreference(context,"token"),
                    new ChangeOrderProductStatus(ps,reason,order.getUnique_id()));
            call.enqueue(new Callback<GBody<String>>() {
                @Override
                public void onResponse(Call<GBody<String>> call, Response<GBody<String>> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(OrderDetail.this, "Ustunlikli uytgedildi", Toast.LENGTH_SHORT).show();
                        PendingPage.get().request();
                        request();
                    } else {
                        Toast.makeText(OrderDetail.this, "Yalnyshlyk yuze cykdy", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }

                @Override
                public void onFailure(Call<GBody<String>> call, Throwable t) {
                    Toast.makeText(OrderDetail.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
        } else {
            Order newOrder=order;
            newOrder.setCurrent_status(status);
            orderDao.delete(order);
            orderDao.insert(newOrder);
            changedOrderProductDao.truncateTable(order.getUnique_id());
            changedOrderProductDao.insertAll(ps);
            orderProductDao.truncateTable(order.getUnique_id());
            orderProductDao.insertAll(ps1);
            PendingPage.get().request();
        }
    }

    private void setViews() {
        if (order != null) {
            binding.titlePhone.setText(order.getPhone_number());
            binding.fullname.setText(order.getFullname());
            if (order.getOrder_address_history() != null && order.getOrder_address_history().size() > 0)
                binding.address.setText(order.getOrder_address_history().get(order.getOrder_address_history().size() - 1).getAddress());
            if (order.getOrder_date_history() != null && order.getOrder_date_history().size() > 0)
                binding.time.setText(order.getOrder_date_history().get(order.getOrder_date_history().size() - 1).getOrder_date() + " / " + order.getOrder_date_history().get(order.getOrder_date_history().size() - 1).getOrder_time());
            binding.info.setText(order.getAdditional_information());
            binding.status.setText(Utils.translateStatus(status));
            if (order.getOrder_price_history() != null && order.getOrder_price_history().size() > 0)
                binding.deliveryPrice.setText(order.getOrder_price_history().get(order.getOrder_price_history().size() - 1).getDelivery_price() + " TMT");
        } else {
            finish();
            Toast.makeText(this, "Yalnyshlyk yuze cykdy!", Toast.LENGTH_SHORT).show();
        }
    }
}