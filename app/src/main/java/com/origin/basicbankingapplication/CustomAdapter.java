package com.origin.basicbankingapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList customer_id, customer_name, customer_email, customer_balance;

    CustomAdapter(Activity activity, Context context, ArrayList customer_id, ArrayList customer_name, ArrayList customer_email,
                  ArrayList customer_balance){
        this.activity = activity;
        this.context = context;
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.customer_email = customer_email;
        this.customer_balance = customer_balance;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_list, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.c_id.setText(String.valueOf(customer_id.get(position)));
        holder.c_name.setText(String.valueOf(customer_name.get(position)));
        //Recyclerview onClickListener
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CustomerDetail.class);
                intent.putExtra("id", String.valueOf(customer_id.get(position)));
                intent.putExtra("name", String.valueOf(customer_name.get(position)));
                intent.putExtra("email", String.valueOf(customer_email.get(position)));
                intent.putExtra("balance", String.valueOf(customer_balance.get(position)));
                intent.putStringArrayListExtra("name_array",customer_name);
                intent.putStringArrayListExtra("balance_array",customer_balance);
                intent.putStringArrayListExtra("id_array",customer_id);
                activity.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return customer_id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView c_id, c_name;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            c_id = itemView.findViewById(R.id.id);
            c_name = itemView.findViewById(R.id.name);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }

    }

}