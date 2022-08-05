package com.android.operatorcourier.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.operatorcourier.Common.Constant;
import com.android.operatorcourier.Fragment.PendingPage;
import com.android.operatorcourier.Model.Order;
import com.android.operatorcourier.Model.OrderDateHistory;
import com.android.operatorcourier.Model.SelectedOrder;
import com.android.operatorcourier.R;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private ArrayList<Order> arrayList = new ArrayList<>();
    private Context context;
    private boolean isAvailableCheck=false;
    private RecyclerView rec;
    private int pos=-1;
    private String status;

    public OrderAdapter(ArrayList<Order> arrayList, Context context, boolean isAvailableCheck, RecyclerView rec, int pos, String status) {
        this.arrayList = arrayList;
        this.context = context;
        this.isAvailableCheck = isAvailableCheck;
        this.rec = rec;
        this.pos = pos;
        this.status = status;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order item = arrayList.get(holder.getAdapterPosition());
        holder.address.setText(item.getOrder_address_history().get(item.getOrder_address_history().size()-1).getAddress());
        holder.phone_number.setText(item.getPhone_number());
        OrderDateHistory dateHistory=item.getOrder_date_history().get(item.getOrder_date_history().size()-1);
        holder.time.setText(dateHistory.getOrder_date()+" / "+dateHistory.getOrder_time());

        SelectedOrder selectedOrder=new SelectedOrder(item.getUnique_id());
        if(PendingPage.selectedOrders.contains(selectedOrder)){
            holder.checkbox.setChecked(true);
        }

        switch (status){
            case Constant.COURIER_PENDING:
                holder.phone_number.setTextColor(context.getResources().getColor(R.color.green_2));
                break;
            case Constant.COURIER_ACCEPTED:
                holder.phone_number.setTextColor(context.getResources().getColor(R.color.blue));
                break;
            case Constant.COURIER_DELIVERED:
                holder.phone_number.setTextColor(context.getResources().getColor(R.color.delivered));
                break;
            case Constant.REJECTED:
                holder.phone_number.setTextColor(context.getResources().getColor(R.color.red));
                break;
        }

        if(isAvailableCheck){
            holder.checkbox.setVisibility(View.VISIBLE);
        } else {
            holder.checkbox.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isAvailableCheck){
                    holder.checkbox.setChecked(!holder.checkbox.isChecked());
                }
            }
        });

        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SelectedOrder selectedOrder2=new SelectedOrder(item.getUnique_id());
                if(b){
                    if(!PendingPage.selectedOrders.contains(selectedOrder2)){
                        PendingPage.selectedOrders.add(selectedOrder2);
                    }
                } else {
                    PendingPage.selectedOrders.remove(selectedOrder2);
                }
            }
        });

        if(holder.getAdapterPosition()==pos){
            holder.checkbox.setChecked(true);
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(status.equals(Constant.COURIER_PENDING) || status.equals(Constant.COURIER_ACCEPTED)){
                    PendingPage.isAvailableCheck=true;
                    PendingPage.get().getBinding().hiddenToolbar.setVisibility(View.VISIBLE);
                    PendingPage.get().getBinding().toolbar.setVisibility(View.GONE);
                    rec.setAdapter(new OrderAdapter(arrayList,context, true,rec,holder.getAdapterPosition(),status));
                    rec.setLayoutManager(new LinearLayoutManager(context));
                    if(pos!=-1){
                        rec.scrollToPosition(pos);
                    }
                }
                return true;
            }
        });

        holder.setIsRecyclable(false);


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private MaterialCheckBox checkbox;
        private TextView phone_number,address,time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkbox=itemView.findViewById(R.id.checkbox);
            phone_number=itemView.findViewById(R.id.phone_number);
            address=itemView.findViewById(R.id.address);
            time=itemView.findViewById(R.id.time);
        }
    }
}
