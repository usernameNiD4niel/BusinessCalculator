package com.rey.businesscalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ProductItems> productItemsArrayList;
    RecyclerAdapter recyclerAdapter;
    
    //Content Initialization
    private Button calculateBtn;
    private DatabaseBusinessCalculator databaseBusinessCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager((this)));
        recyclerView.setHasFixedSize(true);

        productItemsArrayList = new ArrayList<ProductItems>();

        recyclerAdapter = new RecyclerAdapter(this, productItemsArrayList);
        recyclerView.setAdapter(recyclerAdapter);


        ProductItems productItems = new ProductItems("Add Product Computation");
        productItemsArrayList.add(productItems);

        recyclerAdapter.notifyDataSetChanged();

        calculateBtn = findViewById(R.id.calculateBtn);
        
        databaseBusinessCalculator = new DatabaseBusinessCalculator(this);

        calculateBtn.setOnClickListener(view -> {
            Cursor cursor = databaseBusinessCalculator.getUserData();
            if(cursor.getCount() > 0) {
                Toast.makeText(this, "I got the data boss", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, ProductTable.class));

            }
            else Toast.makeText(this, "Pa input po lahat ng field", Toast.LENGTH_SHORT).show();
        });

    }

}