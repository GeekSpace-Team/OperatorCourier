package com.android.operatorcourier.Activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.operatorcourier.Common.Constant;
import com.android.operatorcourier.Fragment.PendingPage;
import com.android.operatorcourier.Fragment.Profile;
import com.android.operatorcourier.R;
import com.android.operatorcourier.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListener();
    }

    private void setListener() {
        getSupportFragmentManager().beginTransaction().replace(R.id.content,new PendingPage(Constant.COURIER_PENDING)).commit();
        binding.bottom.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.pending:
                        getSupportFragmentManager().beginTransaction().replace(R.id.content,new PendingPage(Constant.COURIER_PENDING)).commit();
                        break;
                    case R.id.accepted:
                        getSupportFragmentManager().beginTransaction().replace(R.id.content,new PendingPage(Constant.COURIER_ACCEPTED)).commit();
                        break;
                    case R.id.delivered:
                        getSupportFragmentManager().beginTransaction().replace(R.id.content,new PendingPage(Constant.COURIER_DELIVERED)).commit();
                        break;
                    case R.id.rejected:
                        getSupportFragmentManager().beginTransaction().replace(R.id.content,new PendingPage(Constant.REJECTED)).commit();
                        break;
                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.content,new Profile()).commit();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(PendingPage.isAvailableCheck){
            PendingPage.isAvailableCheck=false;
            PendingPage.get().setAdapter();
        } else {
            super.onBackPressed();
        }
    }
}