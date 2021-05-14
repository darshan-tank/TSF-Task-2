package com.origin.basicbankingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TransactionActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    ArrayList<String> names,balances,ids;
    EditText amount;
    Intent intent;
    Spinner spin;
    String id,ToName,balance,ToId;
    int fromBalance,toBalance;
    DatabaseHelper myDB;
    TextView nameFrom;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        intent = getIntent();
        names = intent.getStringArrayListExtra("name_array");
        balances = intent.getStringArrayListExtra("balance_array");
        ids = intent.getStringArrayListExtra("id_array");
        myDB = new DatabaseHelper(TransactionActivity.this);
        id = intent.getStringExtra("id");

        spin = (Spinner) findViewById(R.id.spinner);
        nameFrom = findViewById(R.id.name);
        submit = findViewById(R.id.button);
        amount = findViewById(R.id.amount);
        spin.setOnItemSelectedListener(this);
        balance = intent.getStringExtra("balance");
        nameFrom.setText(intent.getStringExtra("name"));

        fromBalance = Integer.parseInt(balance);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,names);
        spin.setAdapter(arrayAdapter);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amount.getText().toString().isEmpty()) {
                    amount.setError("Required");
                } else {
                    if (fromBalance == Integer.parseInt(amount.getText().toString())) {
                        Toast.makeText(getApplicationContext(),"Minimum Balance should be 100.",Toast.LENGTH_LONG).show();
                    } else if ((fromBalance-100) < Integer.parseInt(amount.getText().toString())) {
                        Toast.makeText(getApplicationContext(),"Insufficient balance.",Toast.LENGTH_LONG).show();
                    } else {
                        //Deduct Amount From Sender Account
                        if (myDB.updateBalance((fromBalance-Integer.parseInt(amount.getText().toString())),id)) {
                            if (myDB.updateBalance((toBalance+Integer.parseInt(amount.getText().toString())),ToId)) {
                                showCustomDialog();
                            } else {
                                Boolean Result = myDB.updateBalance((fromBalance+Integer.parseInt(amount.getText().toString())),myDB.getId(ToName));
                            }
                        } else {
                            Toast.makeText(getApplicationContext(),"Something went wrong.",Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //String toName = names.get(position);
            //ToName = names.get(position);
            //Cursor cursor = myDB.getBalance(toName);
            toBalance = Integer.parseInt(balances.get(position));
            ToId = ids.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void showCustomDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.custom_dialog, (ViewGroup) findViewById(R.id.root));

        Button button1 = (Button) view.findViewById(R.id.button);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TransactionActivity.this,MainActivity.class));
            }
        });
        alert.setView(view);
        AlertDialog alertDialog = alert.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.getWindow().setGravity(Gravity.CENTER);
        alertDialog.show();
    }
}