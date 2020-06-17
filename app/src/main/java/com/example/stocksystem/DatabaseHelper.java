/*  Author: Randall Varden
    Student Number: M5LBW6YV6
    Module Code: ITSD411
 */
package com.example.stocksystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME ="inventory.db"; //declaration of variables
    public static final String TABLE_NAME ="admin_table";
    public static final String adminId ="admin_ID";
    public static final String username ="admin_username";
    public static final String password ="admin_password";
    public static final String TABLE_NAME2 = "stock_table";
    public static final String stockID ="stock_ID";
    public static final String stockName ="stock_name";
    public static final String stockQuantity ="stock_quantity";
    public static final String stockPrice = "stock_price";
    public static final String stockSellingPrice = "stock_selling_price";

    public DatabaseHelper(Context context) { // constructor method

        super(context,DATABASE_NAME, null, 4);
       // SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) { // onCreate method that creates the tables within the database
        String createTable = "CREATE TABLE " + TABLE_NAME + " ( "+adminId+" INTEGER PRIMARY KEY AUTOINCREMENT," +username+" VARCHAR, "+password+" VARCHAR)";
        String createTable2 = "CREATE TABLE " + TABLE_NAME2 + " ( "+stockID+" INTEGER PRIMARY KEY AUTOINCREMENT," +stockName+" VARCHAR, "+stockQuantity+" INTEGER,"+stockPrice+" INTEGER,"+ stockSellingPrice+" INTEGER)";
        db.execSQL(createTable);
        db.execSQL(createTable2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME); //checks if the table exists and drops them if they do
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME2);
        onCreate(db); //calls the onCreate method
    }

    public boolean insertData(String productName, int quantity, int price, int sellingPrice) // A method to insert data into the database. It takes in 4 parameters
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(stockName, productName); //inserts value into specified column
        contentValues.put(stockQuantity, quantity);
        contentValues.put(stockPrice, price);
        contentValues.put(stockSellingPrice, sellingPrice);
        long result = db.insert(TABLE_NAME2,null,contentValues);
        if(result==-1)

            return false;
        else
            return true;

    }

    public Cursor getData() // creates a cursor to get all of the information within the database
    {
        SQLiteDatabase db = this.getWritableDatabase(); // creates an instance of the SQLite database and makes it writable
        String query = "SELECT * FROM " + TABLE_NAME2;
        Cursor data = db.rawQuery(query,null);
        return data; //returns the cursor data
    }

    public Cursor search(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " +stockName+" FROM " + TABLE_NAME2+" WHERE "+stockName+" = "+name; // a query that selects stockname under a specified condition
        Cursor data = db.rawQuery(query,null);
        data.moveToFirst(); //moves cursor to first entry
        return data;
    }

    public Integer deleteData(String id) //a delete method to delete data within the database
    {
        SQLiteDatabase db = this.getWritableDatabase();
         return db.delete(TABLE_NAME2, "stock_ID = ?", new String[]{id});
    }

    public boolean updateData (String id, String pName, int quantity, int price, int sellingPrice) // a method to update data within the database
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(stockName, pName);
        contentValues.put(stockQuantity, quantity);
        contentValues.put(stockPrice, price);
        contentValues.put(stockSellingPrice,sellingPrice);
        db.update(TABLE_NAME2, contentValues,"stock_ID = ?", new String[] {id} ); //updates the information within the database under the condition of the stock Id the user selects
        return true;
    }

    public Cursor getAllData(String condition)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME2 + " WHERE stock_name = '"+condition+"'", null);
        return res;
    }

    public Cursor getDataUpdate(String condition)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME2 + " WHERE stock_ID = '"+condition+"'", null);
        return res;
    }

    public boolean insertUser(String email, String Password) //adds username and password within the database
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(username, email);
        contentValues.put(password, Password);
        long ins = db.insert(TABLE_NAME, null, contentValues);

        if(ins==-1) return false;
        else return true;
    }

    public Boolean checkEmail (String email) //checks if email exists within the database
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE admin_username = ?",new String[]{email});
        if(cursor.getCount()>0)return false;
        else return true;
    }

    public Boolean emailpassword(String email, String password) //checks if username and password matches the one in the database
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " +TABLE_NAME+" WHERE admin_username = ? AND admin_password=?", new String[] {email,password});
        if(cursor.getCount()>0)return true;
        else return false;
    }

    public Integer deleteUser(String ID) //deletes user based on the ID given
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "admin_ID = ?", new String[]{ID});
    }

    public Cursor getUserData() // creates a cursor to get all of the information within the database
    {
        SQLiteDatabase db = this.getWritableDatabase(); // creates an instance of the SQLite database and makes it writable
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query,null);
        return data; //returns the cursor data
    }

    public Cursor getUserUpdate(String condition) //gets all the user data from the admin table
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME + " WHERE admin_ID = '"+condition+"'", null);
        return res;
    }
}
