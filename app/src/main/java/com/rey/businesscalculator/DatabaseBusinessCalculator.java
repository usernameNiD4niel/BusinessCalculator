package com.rey.businesscalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseBusinessCalculator extends SQLiteOpenHelper {


    // below variable is for our id column.
    private static final String ID_COL = "id";

    public DatabaseBusinessCalculator(Context context) {
        super(context, "BusinessCalculatorDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE BusinessCalculator ("+ID_COL+" INTEGER PRIMARY KEY AUTOINCREMENT, pName TEXT, oPrice INTEGER, eIncome INTEGER, quantity INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS BusinessCalculator");
        onCreate(sqLiteDatabase);
    }

    public Boolean insertUserData(String productName, float originalPrice, float expectedPrice, float quantity) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("pName",productName);
        contentValues.put("oPrice",originalPrice);
        contentValues.put("eIncome",expectedPrice);
        contentValues.put("quantity",quantity);
        long result = sqLiteDatabase.insert("BusinessCalculator", null, contentValues);

        sqLiteDatabase.close();
        if(result == -1) return false;
        return true;
    }

    public void deleteUserData(String productName){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete("BusinessCalculator", "pName=?", new String[]{productName});
        sqLiteDatabase.close();
    }


    public Cursor getUserData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM BusinessCalculator", null);
        return cursor;
    }
}
