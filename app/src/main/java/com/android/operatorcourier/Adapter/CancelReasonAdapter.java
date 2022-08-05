package com.android.operatorcourier.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.operatorcourier.Model.CancelReason;
import com.android.operatorcourier.R;

import java.util.ArrayList;

public class
CancelReasonAdapter extends RecyclerView.Adapter<CancelReasonAdapter.ViewHolder> {
    private ArrayList<CancelReason> arrayList = new ArrayList<>();
    private Context context;
    private EditText reason;

    public CancelReasonAdapter(ArrayList<CancelReason> arrayList, Context context, EditText reason) {
        this.arrayList = arrayList;
        this.context = context;
        this.reason = reason;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cancel_reason_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CancelReason item = arrayList.get(position);
        holder.text.setText(item.getText());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reason.setText(item.getText());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text=itemView.findViewById(R.id.text);
        }
    }
}
