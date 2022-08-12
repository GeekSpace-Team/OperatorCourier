package com.android.operatorcourier.Common;

import static android.content.Context.MODE_PRIVATE;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.operatorcourier.R;
import com.android.operatorcourier.Service.ConnectionReceiver;
import com.google.android.material.snackbar.Snackbar;

public class Utils {


    public static void setPreference(String name, String value, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(name, MODE_PRIVATE).edit();
        editor.putString(name, value);
        editor.apply();
    }

    public static String getSharedPreference(Context context, String name) {
        SharedPreferences prefs = context.getSharedPreferences(name, MODE_PRIVATE);
        String value = prefs.getString(name, "");
        return value;
    }


    public static Dialog getDialogProgressBar(Context context) {
        Dialog dialog = new Dialog(context);

        final ProgressBar progressBar = new ProgressBar(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        progressBar.setLayoutParams(lp);
        progressBar.setBackgroundResource(android.R.color.transparent);
        dialog.setContentView(progressBar);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        dialog.setCancelable(true);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        return dialog;
    }

    public static String translateStatus(String status){
        String result="";
        if(status.equals(Constant.NONE))
            result="Täze sargyt";
        if(status.equals(Constant.PENDING))
            result="Garaşylýar";
        if(status.equals(Constant.REJECTED))
            result="Sargyt ýatyryldy";
        if(status.equals(Constant.COURIER_ACCEPTED))
            result="Tassyklanan";
        if(status.equals(Constant.COURIER_DELIVERED))
            result="Eltip berildi";
        if(status.equals(Constant.COURIER_PENDING))
            result="Eltip berijide";
        if(status.equals(Constant.DELIVERED))
            result="Eltip berildi";

        return result;
    }


    public static void showSnackBar(boolean isConnected, View v) {
        String message;
        if (!isConnected) {
            message = "Internet yok";
            Snackbar snackbar = Snackbar.make(v, message, Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }





}
