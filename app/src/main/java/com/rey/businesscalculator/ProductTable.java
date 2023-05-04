package com.rey.businesscalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ProductTable extends AppCompatActivity {

    RecyclerView recyclerView;
    ProductAdapter productAdapter;
    DatabaseBusinessCalculator databaseBusinessCalculator;
    Spinner spinner;
    Cursor cursor;
    Button viewDetails,addMoreProduct, createNewProduct;

    private String selectedItem = "";
    private String productNames[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_table);

        recyclerView = findViewById(R.id.recyclerView);
        spinner = findViewById(R.id.spinner);
        viewDetails = findViewById(R.id.viewDetails);
        addMoreProduct = findViewById(R.id.addMoreProduct);
        createNewProduct = findViewById(R.id.createNewProduct);

        databaseBusinessCalculator = new DatabaseBusinessCalculator(this);
        cursor = databaseBusinessCalculator.getUserData();

        productNames = new String[cursor.getCount()];
        setItemToArray();
        setRecyclerView();

        ArrayAdapter<String>dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item);
//        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this, R.array.items, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        int lengthData = productNames.length;
        for (int i = 0; i < lengthData; i++) // Maximum size of i upto --> Your Array Size
        {
            dataAdapter.add(productNames[i]);
        }
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                selectedItem = productNames[i];

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        viewDetails.setOnClickListener(event -> {
            Intent intentViewDescription = new Intent(ProductTable.this, ViewDescription.class);
            //Create the bundle
            Bundle bundle = new Bundle();

            //Add your data to bundle
            bundle.putString("selectedItem", selectedItem);

            //Add the bundle to the intent
            intentViewDescription.putExtras(bundle);

            //Fire that second activity
            startActivity(intentViewDescription);
        });

        addMoreProduct.setOnClickListener(event -> {
            startActivity(new Intent(ProductTable.this, MainActivity.class));
        });

        createNewProduct.setOnClickListener(event -> {

            Cursor cursorDelete = databaseBusinessCalculator.getUserData();
            if(cursorDelete.moveToFirst()){
                do {
                    databaseBusinessCalculator.deleteUserData(cursorDelete.getString(1));
                }while (cursorDelete.moveToNext());
            }
            Toast.makeText(this, "The table is now cleared!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ProductTable.this, MainActivity.class));
        });
    }

    private void setItemToArray() {
        int i = 0;
        if(cursor.moveToFirst()){
            do {
                productNames[i] = cursor.getString(1);
                System.err.println(productNames[i]);
                i++;
            }while (cursor.moveToNext());
        }
        i = 0;
    }

    private void setRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productAdapter = new ProductAdapter(this,getList());
        recyclerView.setAdapter(productAdapter);
    }

    private float priceToSell = 0, cost = 0;
    private List<ProductModel> getList() {
        List<ProductModel> models = new ArrayList<>();

//        models.add(new ProductModel("milo",10,40));
//        models.add(new ProductModel("hatdog",10,40));
//        models.add(new ProductModel("cheese dog",10,40));
//        models.add(new ProductModel("aww",10,40));
//        models.add(new ProductModel("aww",10,40));
//        models.add(new ProductModel("aww",10,40));
//        models.add(new ProductModel("aww",10,40));
//        models.add(new ProductModel("aww",10,40));
//        models.add(new ProductModel("aww",10,40));


        if(cursor.moveToFirst()){
            do{
                cost = Float.parseFloat((cursor.getInt(2) * cursor.getInt(4)) + "");
                priceToSell = Float.parseFloat(((cost + cursor.getInt(3))/cursor.getInt(4)) + "");
                models.add(new ProductModel(cursor.getString(1),cost,priceToSell));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return models;
    }


}