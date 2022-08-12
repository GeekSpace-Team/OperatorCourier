package com.android.operatorcourier.Adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.operatorcourier.Activity.OrderDetail;
import com.android.operatorcourier.Common.Constant;
import com.android.operatorcourier.Common.Utils;
import com.android.operatorcourier.Model.OrderProduct;
import com.android.operatorcourier.R;
import com.google.android.material.button.MaterialButton;

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.ViewHolder> {
    private ArrayList<OrderProduct> arrayList = new ArrayList<>();
    private Context context;
    private String statusOrder;

    public OrderProductAdapter(ArrayList<OrderProduct> arrayList, Context context, String statusOrder) {
        this.arrayList = arrayList;
        this.context = context;
        this.statusOrder = statusOrder;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderProduct item = arrayList.get(position);
        holder.articul.setText(item.getProduct_artikul_code());
        holder.discount.setText("-"+item.getProduct_discount()+"%");
        holder.marka.setText(item.getProduct_brand());
        holder.model.setText(item.getProduct_model());
        holder.type.setText(item.getProduct_name());
        holder.price_1.setText(item.getProduct_debt_price()+" TMT");
        holder.price_2.setText(item.getProduct_cash_price()+" TMT");

        if(statusOrder.equals(Constant.COURIER_ACCEPTED)){
            holder.cancel.setVisibility(View.VISIBLE);
            holder.success.setVisibility(View.VISIBLE);
        } else {
            holder.cancel.setVisibility(View.GONE);
            holder.success.setVisibility(View.GONE);
        }

        if(item.getOrder_product_status()!=null){
            holder.status.setText(Utils.translateStatus(item.getOrder_product_status()));
            if(item.getOrder_product_status().equals(Constant.REJECTED) || item.getOrder_product_status().equals(Constant.COURIER_DELIVERED) || item.getOrder_product_status().equals(Constant.DELIVERED)){
                holder.cancel.setVisibility(View.GONE);
                holder.success.setVisibility(View.GONE);
            }
            if(item.getOrder_product_status().equals(Constant.REJECTED)){
                holder.status.setTextColor(context.getResources().getColor(R.color.red));
            }
        }



        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setOrder_product_status(Constant.REJECTED);
                arrayList.set(holder.getAdapterPosition(),item);
                notifyItemChanged(holder.getAdapterPosition());
            }
        });

        holder.success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setOrder_product_status(Constant.COURIER_DELIVERED);
                arrayList.set(holder.getAdapterPosition(),item);
                if(!OrderDetail.delivered.contains(item)){
                    OrderDetail.delivered.add(item);
                }
                notifyItemChanged(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView type,marka,articul,model,price_1,price_2,discount,status;
        private MaterialButton undo,cancel,success;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            type=itemView.findViewById(R.id.type);
            marka=itemView.findViewById(R.id.marka);
            articul=itemView.findViewById(R.id.articul);
            model=itemView.findViewById(R.id.model);
            price_1=itemView.findViewById(R.id.price_1);
            price_2=itemView.findViewById(R.id.price_2);
            discount=itemView.findViewById(R.id.discount);
            undo=itemView.findViewById(R.id.undo);
            cancel=itemView.findViewById(R.id.cancel);
            success=itemView.findViewById(R.id.success);
            status=itemView.findViewById(R.id.status);
        }
    }
}
