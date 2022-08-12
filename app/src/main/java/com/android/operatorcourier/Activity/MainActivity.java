package com.android.operatorcourier.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.android.operatorcourier.Common.Constant;
import com.android.operatorcourier.Common.Utils;
import com.android.operatorcourier.Fragment.PendingPage;
import com.android.operatorcourier.Fragment.Profile;
import com.android.operatorcourier.Model.ChangeOrderStatus;
import com.android.operatorcourier.Model.ChangedOrderProduct;
import com.android.operatorcourier.Model.OrderDeliveryPriceHistory;
import com.android.operatorcourier.R;
import com.android.operatorcourier.Room.AppDatabase;
import com.android.operatorcourier.Room.ChangedOrderProductDao;
import com.android.operatorcourier.Room.ChangedOrderStatusDao;
import com.android.operatorcourier.Room.OrderDao;
import com.android.operatorcourier.Room.OrderDeliveryPriceDao;
import com.android.operatorcourier.Room.OrderProductDao;
import com.android.operatorcourier.Service.ConnectionReceiver;
import com.android.operatorcourier.databinding.ActivityMainBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ConnectionReceiver.ReceiverListener {
    private ActivityMainBinding binding;
    private static MainActivity INSTANCE;
    private AppDatabase db;
    private OrderProductDao orderProductDao;
    private ChangedOrderProductDao changedOrderProductDao;
    private OrderDao orderDao;
    private OrderDeliveryPriceDao orderDeliveryPriceDao;
    private ChangedOrderStatusDao changedOrderStatusDao;
    private Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        INSTANCE=this;
        db = Room.databaseBuilder(context,
                AppDatabase.class, "order").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        orderProductDao=db.orderProductDao();
        changedOrderProductDao=db.changedOrderProductDao();
        orderDao=db.userDao();
        changedOrderStatusDao=db.changedOrderStatusDao();
        orderDeliveryPriceDao=db.orderDeliveryPriceDao();
        checkConnection();
        setListener();
    }

    public static MainActivity get(){
        return INSTANCE;
    }

    private void setListener() {
        getSupportFragmentManager().beginTransaction().replace(R.id.content, new PendingPage(Constant.COURIER_PENDING)).commit();
        binding.bottom.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                checkConnection();
                switch (item.getItemId()) {
                    case R.id.pending:
                        getSupportFragmentManager().beginTransaction().replace(R.id.content, new PendingPage(Constant.COURIER_PENDING)).commit();
                        break;
                    case R.id.accepted:
                        getSupportFragmentManager().beginTransaction().replace(R.id.content, new PendingPage(Constant.COURIER_ACCEPTED)).commit();
                        break;
                    case R.id.delivered:
                        getSupportFragmentManager().beginTransaction().replace(R.id.content, new PendingPage(Constant.COURIER_DELIVERED)).commit();
                        break;
                    case R.id.rejected:
                        getSupportFragmentManager().beginTransaction().replace(R.id.content, new PendingPage(Constant.REJECTED)).commit();
                        break;
                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.content, new Profile()).commit();
                        break;
                }
                return true;
            }
        });
    }

    public void checkConnection() {

        // initialize intent filter
        IntentFilter intentFilter = new IntentFilter();

        // add action
        intentFilter.addAction("android.new.conn.CONNECTIVITY_CHANGE");

        // register receiver
        registerReceiver(new ConnectionReceiver(), intentFilter);

        // Initialize listener
        ConnectionReceiver.Listener = this;

        // Initialize connectivity manager
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Initialize network info
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        // get connection status
        boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();

        Constant.isConnected=isConnected;

        if(isConnected){
            checkOfflineChanges();
        }

        // display snack bar
//        Utils.showSnackBar(isConnected, binding.getRoot());
    }

    @Override
    public void onBackPressed() {
        if (PendingPage.isAvailableCheck) {
            PendingPage.isAvailableCheck = false;
            PendingPage.get().setAdapter();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onNetworkChange(boolean isConnected) {
        Constant.isConnected=isConnected;
        Utils.showSnackBar(isConnected, binding.getRoot());
        if(isConnected){
            checkOfflineChanges();
        }
    }

    private void checkOfflineChanges() {
        List<ChangeOrderStatus> r1=changedOrderStatusDao.get();
        List<ChangedOrderProduct> r2=changedOrderProductDao.getAllWithoutFilter();
        List<OrderDeliveryPriceHistory> r3=orderDeliveryPriceDao.getAll();
        if((r1!=null && r1.size()>0)
            || (r2!=null && r2.size()>0)
            || (r3!=null && r3.size()>0)){
            MaterialAlertDialogBuilder alert=new MaterialAlertDialogBuilder(context);
            alert.setTitle("Üns beriň!!!");
            alert.setMessage("Internetsiz edilen üýtgeşmeler bar!");
            alert.setPositiveButton("Serwera ugratmak", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    Intent intent=new Intent(context,Sync.class);
                    startActivity(intent);
                }
            });
            alert.setCancelable(false);
            alert.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkConnection();
    }

    @Override
    protected void onPause() {
        super.onPause();
        checkConnection();
    }
}