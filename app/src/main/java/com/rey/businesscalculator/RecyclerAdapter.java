package com.rey.businesscalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolderClass> {

    Context context;
    ArrayList<ProductItems> productItemsList;
    private DatabaseBusinessCalculator databaseBusinessCalculator;

    public RecyclerAdapter(Context context, ArrayList<ProductItems> productItemsList) {
        this.context = context;
        this.productItemsList = productItemsList;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new ViewHolderClass(view);
    }
    
    float originalPrice = 0;
    float expectedIncome = 0;
    int quantity = 0;
    String productName;
    @Override
    public void onBindViewHolder(@NonNull ViewHolderClass holder, int position) {

        ProductItems productItems = productItemsList.get(position);
        holder.products.setText(productItems.productItem);
        holder.productNameET.requestFocus();

        //                Database
        databaseBusinessCalculator = new DatabaseBusinessCalculator(context);

        holder.addBtn.setOnClickListener(view -> {
            try {
                originalPrice = Float.parseFloat(holder.originalPriceET.getText().toString());
                expectedIncome = Float.parseFloat(holder.expectedIncomeET.getText().toString());
                quantity = Integer.parseInt(holder.quantityET.getText().toString());
                productName = holder.productNameET.getText().toString();
            }catch (NumberFormatException error){
                Toast.makeText(context, "patal ang lado na ini, lagyan mo muna", Toast.LENGTH_SHORT).show();
            }
            
            if(originalPrice > 0 && expectedIncome > 0 && quantity > 0 && !productName.isEmpty()) {
                Boolean checkingInsertData = databaseBusinessCalculator.insertUserData(productName,originalPrice,expectedIncome,quantity);
                
                if(checkingInsertData == true)
                    Toast.makeText(context, "The product has been added to the table", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context, "Error: Product has not been added to the table", Toast.LENGTH_SHORT).show();
                        
                holder.originalPriceET.setText("");
                holder.expectedIncomeET.setText("");
                holder.quantityET.setText("");
                holder.productNameET.setText("");
                holder.productNameET.requestFocus();


            }else {
                holder.originalPriceET.setError("Paki ayos po ng fill up");
                holder.expectedIncomeET.setError("Paki ayos po ng fill up din dito");
                holder.quantityET.setError("Please dito din po ang pagal ay");
                holder.productNameET.setError("Please dito din po ang pagal ay");
            }
        });


        holder.clearBtn.setOnClickListener(view -> {
            holder.originalPriceET.setText("");
            holder.expectedIncomeET.setText("");
            holder.quantityET.setText("");
            holder.productNameET.setText("");
        });
    }

    @Override
    public int getItemCount() {
        return productItemsList.size();
    }

    public static class ViewHolderClass extends RecyclerView.ViewHolder{

        TextView products;
        EditText originalPriceET,expectedIncomeET,quantityET,productNameET;
        Button addBtn,clearBtn;


        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            products = itemView.findViewById(R.id.productItem);
            productNameET = itemView.findViewById(R.id.productNameET);
            originalPriceET = itemView.findViewById(R.id.originalPriceET);
            expectedIncomeET = itemView.findViewById(R.id.expectedIncomeET);
            quantityET = itemView.findViewById(R.id.quantityET);
            clearBtn = itemView.findViewById(R.id.clearBtn);
            addBtn = itemView.findViewById(R.id.addButton);
        }
    }
}
