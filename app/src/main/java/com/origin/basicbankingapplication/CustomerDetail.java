package com.origin.basicbankingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomerDetail extends AppCompatActivity {

    String customer_id, customer_name, customer_email, customer_balance;
    Intent intent;
    TextView tv_name, tv_email, tv_balance;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);

        intent = getIntent();
        customer_id = intent.getStringExtra("id");
        customer_name = intent.getStringExtra("name");
        customer_email = intent.getStringExtra("email");
        customer_balance = intent.getStringExtra("balance");

        tv_name = findViewById(R.id.name);
        tv_email = findViewById(R.id.email);
        tv_balance = findViewById(R.id.balance);
        submit = findViewById(R.id.button);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CustomerDetail.this, TransactionActivity.class);
                i.putExtra("id", String.valueOf(customer_id));
                i.putExtra("balance",intent.getStringExtra("balance"));
                i.putExtra("name",intent.getStringExtra("name"));
                i.putStringArrayListExtra("balance_array" , intent.getStringArrayListExtra("balance_array"));
                i.putStringArrayListExtra("name_array" , intent.getStringArrayListExtra("name_array"));
                i.putStringArrayListExtra("id_array" , intent.getStringArrayListExtra("id_array"));
                startActivity(i);
            }
        });

        tv_name.setText(customer_name);
        tv_email.setText(customer_email);
        tv_balance.setText("₹ "+customer_balance);
    }
}