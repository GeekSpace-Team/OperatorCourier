package com.android.operatorcourier.Adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.operatorcourier.Model.OrderProduct;
import com.android.operatorcourier.R;
import com.google.android.material.button.MaterialButton;

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.ViewHolder> {
    private ArrayList<OrderProduct> arrayList = new ArrayList<>();
    private Context context;

    public OrderProductAdapter(ArrayList<OrderProduct> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
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
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView type,marka,articul,model,price_1,price_2,discount;
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
        }
    }
}
