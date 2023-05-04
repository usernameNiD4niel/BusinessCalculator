package com.rey.businesscalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewDescription extends AppCompatActivity {

    private Button showComputation, hideComputation;
    private TextView priceToSell, totalCost, expectedIncome, quantity, marketPrice, productItem;
    private DatabaseBusinessCalculator databaseBusinessCalculator;
    private LinearLayout lowerParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_description);

        //Get the bundle
        Bundle bundle = getIntent().getExtras();

        String selectedItem = bundle.getString("selectedItem");


//        Referencing the variables
        showComputation = findViewById(R.id.showComputation);
        hideComputation = findViewById(R.id.hideComputation);
        priceToSell = findViewById(R.id.priceToSell);
        totalCost = findViewById(R.id.totalCost);
        expectedIncome = findViewById(R.id.expectedIncome);
        quantity = findViewById(R.id.quantity);
        marketPrice = findViewById(R.id.marketPrice);
        productItem = findViewById(R.id.productItem);
        lowerParent = findViewById(R.id.lowerParent);

        databaseBusinessCalculator = new DatabaseBusinessCalculator(this);

        getDataFromDatabase(selectedItem);

        showComputation.setOnClickListener(view -> {
            lowerParent.setVisibility(View.VISIBLE);
            hideComputation.setVisibility(View.VISIBLE);
            showComputation.setVisibility(View.GONE);
        });

        hideComputation.setOnClickListener(view -> {
            lowerParent.setVisibility(View.GONE);
            hideComputation.setVisibility(View.GONE);
            showComputation.setVisibility(View.VISIBLE);
        });
    }

//    Utility Variable
    private float cost = 0, priceToSellUtility = 0;

    private void getDataFromDatabase(String selectedItem) {
        Cursor cursor = databaseBusinessCalculator.getUserData();

        if(cursor.moveToFirst()){
            do {
                if(selectedItem.equals(cursor.getString(1))){
                cost = Float.parseFloat((cursor.getInt(2) * cursor.getInt(4)) + "");
                priceToSellUtility = Float.parseFloat(((cost + cursor.getInt(3))/cursor.getInt(4)) + "");

                productItem.setText(cursor.getString(1));
                marketPrice.setText("P"+cursor.getInt(2) + ".00");
                expectedIncome.setText("P"+cursor.getInt(3) + ".00");
                quantity.setText(cursor.getInt(4) + "pcs");
                priceToSell.setText("P" + priceToSellUtility + "0");
                totalCost.setText("P" + cost + "0");
                }

            }while (cursor.moveToNext());
        }
    }
}