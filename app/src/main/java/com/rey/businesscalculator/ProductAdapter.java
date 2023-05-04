package com.rey.businesscalculator;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    Context context;
    List<ProductModel> productModelList;
    DatabaseBusinessCalculator databaseBusinessCalculator;

    public ProductAdapter(Context context, List<ProductModel> productModelList) {
        this.context = context;
        this.productModelList = productModelList;
        databaseBusinessCalculator = new DatabaseBusinessCalculator(context);
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {

        if(productModelList != null && productModelList.size() > 0) {
            ProductModel productModel = productModelList.get(position);

            holder.productItemTV.setText(productModel.getProductName());
            holder.productItemTV.setWidth(100);
            holder.costTV.setText("P" + productModel.getCost());
            holder.costTV.setWidth(100);
            holder.priceToSellTV.setText("P"+productModel.getPriceToSell()+"/pcs");
            holder.priceToSellTV.setWidth(100);
        }else {
            return;
        }
    }

    @Override
    public int getItemCount() {
        return productModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView productItemTV, priceToSellTV, costTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productItemTV = itemView.findViewById(R.id.productItemTV);
            priceToSellTV = itemView.findViewById(R.id.priceToSellTV);
            costTV = itemView.findViewById(R.id.costTV);
        }
    }
}
