package com.origin.basicbankingapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    DatabaseHelper myDB;
    ArrayList<String> customer_id, customer_name, customer_email, customer_balance;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myDB = new DatabaseHelper(MainActivity.this);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("firstTime", false)) {

            //Inserting 10 Dummy Data
            myDB.insertCustomerData("Aakash","aakash@gmail.com",1000);
            myDB.insertCustomerData("Anil","anil@gmail.com",10000);
            myDB.insertCustomerData("Vinayak","vinayak@gmail.com",20000);
            myDB.insertCustomerData("Pradip","pradip@gmail.com",2000);
            myDB.insertCustomerData("Jay","jay@gmail.com",40000);
            myDB.insertCustomerData("Vishal","vishal@gmail.com",5000);
            myDB.insertCustomerData("Dhaval","dhaval@gmail.com",50000);
            myDB.insertCustomerData("Divyesh","divyesh@gmail.com",60000);
            myDB.insertCustomerData("Jatin","jatin@gmail.com",20000);
            myDB.insertCustomerData("Viraj","viraj@gmail.com",15000);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }

        recyclerView = findViewById(R.id.recyclerView);

        customer_id = new ArrayList<>();
        customer_name = new ArrayList<>();
        customer_email = new ArrayList<>();
        customer_balance = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(MainActivity.this,this, customer_id, customer_name, customer_email,
                customer_balance);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    void storeDataInArrays(){
        Cursor cursor = myDB.getAllCustomerData();

            while (cursor.moveToNext()){
                customer_id.add(cursor.getString(0));
                customer_name.add(cursor.getString(1));
                customer_email.add(cursor.getString(2));
                customer_balance.add(cursor.getString(3));
            }
    }
}