package com.android.operatorcourier.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.operatorcourier.Activity.Login;
import com.android.operatorcourier.Activity.MainActivity;
import com.android.operatorcourier.Activity.Sync;
import com.android.operatorcourier.Common.Constant;
import com.android.operatorcourier.Common.Utils;
import com.android.operatorcourier.R;
import com.android.operatorcourier.databinding.FragmentProfileBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;


public class Profile extends Fragment {

    private Context context;
    private FragmentProfileBinding binding;

    public Profile() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentProfileBinding.inflate(inflater,container,false);
        context=getContext();
        setViews();
        setListener();
        return binding.getRoot();
    }

    private void setListener() {
        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder alertDialogBuilder=new MaterialAlertDialogBuilder(context);
                alertDialogBuilder.setTitle("Ulgamdan çykmakjymy?");
                alertDialogBuilder.setPositiveButton("Hawa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Utils.setPreference("token","",context);
                        getActivity().finish();
                        startActivity(new Intent(context, Login.class));
                    }
                });
                alertDialogBuilder.setNegativeButton("Ýok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialogBuilder.show();
            }
        });

        binding.sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.get().checkConnection();
                if(Constant.isConnected){
                    Intent intent=new Intent(context, Sync.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(context, "Internet yok", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setViews() {
        binding.fullname.setText(Utils.getSharedPreference(context,"fullname"));
        binding.phoneNumber.setText(Utils.getSharedPreference(context,"phone_number"));
    }
}