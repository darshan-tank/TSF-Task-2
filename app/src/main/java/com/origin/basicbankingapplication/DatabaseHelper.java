package com.origin.basicbankingapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**

 * Created by Arjun on 4/2/2017.

 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final  String DATABASE_NAME = "Banking.db";

    public static final  String CUSTOMER_TABLE_NAME = "Customer_table";

    public static final  String TRANSACTION_TABLE_NAME = "Transaction_table";



    public static final  String CUSTOMER_COL_1 = "ID";

    public static final  String CUSTOMER_COL_2 = "NAME";

    public static final  String CUSTOMER_COL_3 = "Email";

    public static final  String CUSTOMER_COL_4 = "Balance";

    public static final  String TRANSACTION_COL_1 = "ID";

    public static final  String TRANSACTION_COL_2 = "Sender";

    public static final  String TRANSACTION_COL_3 = "Reciever";

    public static final  String TRANSACTION_COL_4 = "Amount";

    public static final  String TRANSACTION_COL_5 = "DateTime";


    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, 1);

    }



    @Override

    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + CUSTOMER_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,Email TEXT,Balance INTEGER)");
        db.execSQL("CREATE TABLE " + TRANSACTION_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,Sender TEXT,Reciever TEXT,Amount INTEGER,DateTime TEXT)");

    }


    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+CUSTOMER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TRANSACTION_TABLE_NAME);

    }

    public boolean insertTransactionData(String sender,String reciever,int amount,String datetime){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(TRANSACTION_COL_2,sender);

        contentValues.put(TRANSACTION_COL_3,reciever);

        contentValues.put(TRANSACTION_COL_4,amount);

        contentValues.put(TRANSACTION_COL_5,datetime);

        long result = db.insert(TRANSACTION_TABLE_NAME,null,contentValues);

        db.close();



        //To Check Whether Data is Inserted in DataBase

        if(result==-1){

            return false;

        }else{

            return true;

        }

    }

    public boolean updateBalance(int amount,String id){

        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(TRANSACTION_COL_4,amount);

        String[] Cid = {id};

        db.execSQL("UPDATE "+CUSTOMER_TABLE_NAME+" SET Balance = "+"'"+amount+"' "+ "WHERE ID = "+"'"+id+"'");

        db.close();



        //To Check Whether Data is Inserted in DataBase
        return true;
    }

    public void insertCustomerData(String name,String email,int balance){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(CUSTOMER_COL_2,name);

        contentValues.put(CUSTOMER_COL_3,email);

        contentValues.put(CUSTOMER_COL_4,balance);

        long result = db.insert(CUSTOMER_TABLE_NAME,null,contentValues);

        db.close();



        //To Check Whether Data is Inserted in DataBase

       // if(result==-1){

        //    return false;

       // }else{

        //    return true;

        //}

    }

    public Cursor getAllCustomerData(){

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("Select * from " + CUSTOMER_TABLE_NAME,null);

        return  res;

    }

    public Cursor getBalance(String name){

        SQLiteDatabase db = this.getReadableDatabase();



        Cursor res = db.rawQuery("Select Balance from " + CUSTOMER_TABLE_NAME + " WHERE NAME = '" + name + "'",null);

        return  res;

    }

    public String getId(String name){

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("Select ID from " + CUSTOMER_TABLE_NAME + " WHERE " + CUSTOMER_COL_2 + " = '" + name + "'",null);

        return  res.getString(0);

    }

    public Cursor getAllTransactionData(){

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("Select * from " + TRANSACTION_TABLE_NAME,null);

        return  res;

    }

}