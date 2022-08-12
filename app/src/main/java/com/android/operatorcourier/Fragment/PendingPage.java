package com.android.operatorcourier.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.android.operatorcourier.Activity.MainActivity;
import com.android.operatorcourier.Adapter.CancelReasonAdapter;
import com.android.operatorcourier.Adapter.OrderAdapter;
import com.android.operatorcourier.Api.APIClient;
import com.android.operatorcourier.Api.ApiInterface;
import com.android.operatorcourier.Common.Constant;
import com.android.operatorcourier.Common.Utils;
import com.android.operatorcourier.Model.CancelReason;
import com.android.operatorcourier.Model.ChangeOrderStatus;
import com.android.operatorcourier.Model.GBody;
import com.android.operatorcourier.Model.Order;
import com.android.operatorcourier.Model.SelectedOrder;
import com.android.operatorcourier.R;
import com.android.operatorcourier.Room.AppDatabase;
import com.android.operatorcourier.Room.ChangedOrderStatusDao;
import com.android.operatorcourier.Room.OrderDao;
import com.android.operatorcourier.databinding.FragmentPendingPageBinding;
import com.android.operatorcourier.databinding.RejectDialogBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PendingPage extends Fragment {
    private FragmentPendingPageBinding binding;
    private ArrayList<Order> orders = new ArrayList<>();
    private Context context;
    private String status = "";
    public static boolean isAvailableCheck = false;
    private static PendingPage INSTANCE;
    public static ArrayList<SelectedOrder> selectedOrders = new ArrayList<>();
    private ArrayList<CancelReason> cancelReasons = new ArrayList<>();
    private AppDatabase db;
    private OrderDao orderDao;
    private ChangedOrderStatusDao changedOrderStatusDao;
    public PendingPage(String status) {
        this.status = status;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPendingPageBinding.inflate(inflater, container, false);
        context = getContext();
        INSTANCE = this;
        db = Room.databaseBuilder(context,
                AppDatabase.class, "order").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        orderDao = db.userDao();
        changedOrderStatusDao=db.changedOrderStatusDao();
        setSearchView();
        setView();
        setListener();
        request();

        return binding.getRoot();
    }

    private void setListener() {
        binding.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAvailableCheck = true;
                setAdapter();
            }
        });

        binding.errorContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request();
            }
        });

        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAvailableCheck = false;
                selectedOrders.clear();
                setAdapter();
            }
        });

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()){
                    setAdapter();
                }
                return false;
            }
        });

        binding.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog dialog = new BottomSheetDialog(context, R.style.CustomBottomSheetDialogTheme);
                RejectDialogBinding rejectDialogBinding = RejectDialogBinding.inflate(LayoutInflater.from(context));

                rejectDialogBinding.items.setAdapter(new CancelReasonAdapter(cancelReasons, context, rejectDialogBinding.editText));
                rejectDialogBinding.items.setLayoutManager(new GridLayoutManager(context, 2, RecyclerView.HORIZONTAL, false));
                rejectDialogBinding.cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                rejectDialogBinding.reject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MainActivity.get().checkConnection();
                        if(selectedOrders.size()<=0){
                            Toast.makeText(context, "Sargyt sayla!!!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (rejectDialogBinding.editText.getText().toString().trim().isEmpty()) {
                            Toast.makeText(context, "Sebäbini giriziň!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
                            ChangeOrderStatus changeOrderStatus = new ChangeOrderStatus(selectedOrders, rejectDialogBinding.editText.getText().toString(),Constant.REJECTED);
                            if(Constant.isConnected) {
                                Call<GBody<String>> call = apiInterface.cancelOrder("Bearer " + Utils.getSharedPreference(context, "token"), changeOrderStatus);
                                call.enqueue(new Callback<GBody<String>>() {
                                    @Override
                                    public void onResponse(Call<GBody<String>> call, Response<GBody<String>> response) {
                                        if (response.isSuccessful()) {
                                            selectedOrders.clear();
                                            isAvailableCheck = false;
                                            request();
                                        } else {
                                            Toast.makeText(context, "Ýalňyşlyk ýüze çykdy!", Toast.LENGTH_SHORT).show();
                                        }
                                        dialog.dismiss();
                                    }

                                    @Override
                                    public void onFailure(Call<GBody<String>> call, Throwable t) {
                                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                });
                            } else {
                                changedOrderStatusDao.delete(changeOrderStatus);
                                changedOrderStatusDao.insert(changeOrderStatus);

                                String[] ids=new String[selectedOrders.size()];
                                int k=0;
                                for(SelectedOrder sl:selectedOrders){
                                    ids[k]=sl.getUnique_id();
                                    k++;
                                }

                                dialog.dismiss();

                                orderDao.updateSelected(ids,Constant.REJECTED);
                                selectedOrders.clear();
                                isAvailableCheck = false;
                                request();
                            }
                        }
                    }
                });
                dialog.setContentView(rejectDialogBinding.getRoot());
                dialog.show();
            }
        });

        binding.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.get().checkConnection();
                String message = "Bu sargytlary kabul etmek isleýärsiňizmi?";
                String title = "ÜNS BERIŇ!!!";
                if (status.equals(Constant.COURIER_ACCEPTED)) {
                    message = "Bu sargytlary eltip berdiňizmi?";
                }
                MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(context);
                alertDialogBuilder.setMessage(message);
                alertDialogBuilder.setTitle(title);
                alertDialogBuilder.setNegativeButton("Ýok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialogBuilder.setPositiveButton("Hawa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(selectedOrders.size()<=0){
                            Toast.makeText(context, "Sargyt sayla!!!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        ChangeOrderStatus changeOrderStatus = new ChangeOrderStatus(selectedOrders, "","");
                        String newStatus="";
                        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
                        Call<GBody<String>> call = null;
                        if (status.equals(Constant.COURIER_PENDING)) {
                            // Accept orders
                            call = apiInterface.acceptOrder("Bearer " + Utils.getSharedPreference(context, "token"), changeOrderStatus);
                            newStatus=Constant.COURIER_ACCEPTED;
                        } else if (status.equals(Constant.COURIER_ACCEPTED)) {
                            // Delivered orders
                            call = apiInterface.deliveryOrder("Bearer " + Utils.getSharedPreference(context, "token"), changeOrderStatus);
                            newStatus=Constant.COURIER_DELIVERED;
                        }
                        changeOrderStatus.setStatus(newStatus);
                        if(Constant.isConnected) {
                            Dialog dialog = Utils.getDialogProgressBar(context);
                            dialog.show();

                            if (call != null) {
                                call.enqueue(new Callback<GBody<String>>() {
                                    @Override
                                    public void onResponse(Call<GBody<String>> call, Response<GBody<String>> response) {
                                        if (response.isSuccessful()) {
                                            selectedOrders.clear();
                                            isAvailableCheck = false;
                                            request();
                                        } else {
                                            Toast.makeText(context, "Ýalňyşlyk ýüze çykdy!", Toast.LENGTH_SHORT).show();
                                        }
                                        dialog.dismiss();
                                    }

                                    @Override
                                    public void onFailure(Call<GBody<String>> call, Throwable t) {
                                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                });
                            } else {
                                dialog.dismiss();
                            }
                        } else {
                            changedOrderStatusDao.delete(changeOrderStatus);
                            changedOrderStatusDao.insert(changeOrderStatus);

                            String[] ids=new String[selectedOrders.size()];
                            int k=0;
                            for(SelectedOrder sl:selectedOrders){
                                ids[k]=sl.getUnique_id();
                                k++;
                            }

                            orderDao.updateSelected(ids,newStatus);
                            selectedOrders.clear();
                            isAvailableCheck = false;
                            request();
                        }
                    }
                });
                alertDialogBuilder.show();
            }
        });


    }

    private void search(String query) {
        ArrayList<Order> searchResult = new ArrayList<>();
        for (Order order:orders){
            String addr="";
            if(order.getOrder_address_history()!=null && order.getOrder_address_history().size()>0){
                addr=order.getOrder_address_history().get(order.getOrder_address_history().size()-1).getAddress();
            }
            if(order.getFullname().toUpperCase().contains(query.toUpperCase())
                || order.getAdditional_information().toUpperCase().contains(query.toUpperCase())
                || order.getPhone_number().contains(query)
                || addr.toUpperCase().contains(query.toUpperCase())){
                searchResult.add(order);
            }
        }
        binding.rec.setAdapter(new OrderAdapter(searchResult, context, isAvailableCheck, binding.rec, -1, status));
        binding.rec.setLayoutManager(new LinearLayoutManager(context));
        if (isAvailableCheck) {
            binding.toolbar.setVisibility(View.GONE);
            binding.hiddenToolbar.setVisibility(View.VISIBLE);
        } else {
            binding.toolbar.setVisibility(View.VISIBLE);
            binding.hiddenToolbar.setVisibility(View.GONE);
        }
    }

    public static PendingPage get() {
        return INSTANCE;
    }

    public FragmentPendingPageBinding getBinding() {
        return binding;
    }

    private void setView() {
        binding.title.setText(Utils.translateStatus(status));

        if (status.equals(Constant.COURIER_PENDING) || status.equals(Constant.COURIER_ACCEPTED)) {
            binding.check.setVisibility(View.VISIBLE);
        } else {
            binding.check.setVisibility(View.GONE);
        }

        if (status.equals(Constant.COURIER_ACCEPTED)) {
            binding.accept.setText("Eltip berdim");
        }
    }

    private void setSearchView() {
        EditText searchEditText = binding.searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.text));
        searchEditText.setHintTextColor(getResources().getColor(R.color.black_2));
    }

    public void setAdapter() {
        binding.rec.setAdapter(new OrderAdapter(orders, context, isAvailableCheck, binding.rec, -1, status));
        binding.rec.setLayoutManager(new LinearLayoutManager(context));
        if (isAvailableCheck) {
            binding.toolbar.setVisibility(View.GONE);
            binding.hiddenToolbar.setVisibility(View.VISIBLE);
        } else {
            binding.toolbar.setVisibility(View.VISIBLE);
            binding.hiddenToolbar.setVisibility(View.GONE);
        }
    }

    public void request() {
        hideError();
        if(Constant.isConnected){
            Dialog dialog = Utils.getDialogProgressBar(context);
            dialog.show();
            ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
            Call<GBody<ArrayList<Order>>> call = apiInterface.getOrderByStatus("Bearer " + Utils.getSharedPreference(context, "token"), status);
            call.enqueue(new Callback<GBody<ArrayList<Order>>>() {
                @Override
                public void onResponse(Call<GBody<ArrayList<Order>>> call, Response<GBody<ArrayList<Order>>> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getBody() != null) {
                        orders = response.body().getBody();
                        if (orders.size() <= 0) {
                            binding.empty.setVisibility(View.VISIBLE);
                        }
                        getReason();
                        orderDao.truncateTable(status);
                        orderDao.insertAll(orders);
                        setAdapter();
                    } else {
                        showError("Error code: " + response.code() + " \nTap to retry");
                    }
                    dialog.dismiss();
                }

                @Override
                public void onFailure(Call<GBody<ArrayList<Order>>> call, Throwable t) {
                    showError(t.getMessage() + " \nTap to retry");
                    dialog.dismiss();
                }
            });
        } else {
            try {
                showError("Internet yok");
                orders = (ArrayList<Order>) orderDao.getAll(status);
                if (orders.size() >= 0) {
                    binding.empty.setVisibility(View.GONE);
                } else {
                    binding.empty.setVisibility(View.VISIBLE);
                }
                setAdapter();
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    private void showError(String message) {
        binding.errorText.setText(message);
        binding.errorContainer.setVisibility(View.VISIBLE);
        binding.empty.setVisibility(View.VISIBLE);
    }

    private void hideError() {
        binding.errorContainer.setVisibility(View.GONE);
        binding.empty.setVisibility(View.GONE);
    }

    private void getReason() {
        ApiInterface apiInterface=APIClient.getClient().create(ApiInterface.class);
        Call<GBody<ArrayList<CancelReason>>> call=apiInterface.getCancelReasons("Bearer " + Utils.getSharedPreference(context, "token"),Utils.getSharedPreference(context,"sell_point_id"));
        call.enqueue(new Callback<GBody<ArrayList<CancelReason>>>() {
            @Override
            public void onResponse(Call<GBody<ArrayList<CancelReason>>> call, Response<GBody<ArrayList<CancelReason>>> response) {
                if(response.isSuccessful() && response.body() != null && response.body().getBody()!=null){
                    cancelReasons=response.body().getBody();
                } else {
                    Toast.makeText(context, ""+response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GBody<ArrayList<CancelReason>>> call, Throwable t) {
                Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}