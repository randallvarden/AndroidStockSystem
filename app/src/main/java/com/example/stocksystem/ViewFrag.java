/*  Author: Randall Varden
    Student Number: M5LBW6YV6
    Module Code: ITSD411
 */
package com.example.stocksystem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewFrag extends Fragment //extends fragment
{
    DatabaseHelper dbh; // declares UI components
    private ListView listView;
    String condition;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view, container, false);
        dbh = new DatabaseHelper(getActivity());
        listView = (ListView)view.findViewById(R.id.listView);

        populateListView();
        viewAll();
        return view;
    }

private void populateListView() //method that populates the list view
{
    Cursor data = dbh.getData();
    ArrayList<String> listData = new ArrayList<>();
    while(data.moveToNext())
    {
        listData.add(data.getString(1));
    }
    ListAdapter adapter = new ArrayAdapter<>(this.getActivity(),android.R.layout.simple_list_item_1,listData); //declares an Array Dapter and sets layout to simple list
    listView.setAdapter(adapter); //sets adapter
}

    public void viewAll()
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                condition = listView.getItemAtPosition(position)+"";
                Cursor res = dbh.getAllData(condition);
                if(res.getCount()==0)
                {
                    showMessage("Error", "Nothing Found");
                    return;
                }
                StringBuffer buffer = new StringBuffer(); // creates a new instance of a String Buffer
                while(res.moveToNext()){
                    buffer.append("Product ID: " +res.getString(0)+"\n"+ "Product Name: "+res.getString(1)+"\n"+"Quantity: " +
                            res.getString(2) + "\n" + "Cost Price: R" + res.getString(3)
                            +"\n"+"Selling Price: R"+res.getString(4)); // appends Cursor information to the string buffer
                }
                showMessage("Item",buffer.toString());
            }
        });

    }
    public void showMessage(String title, String Message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); //  creates a new instance of Alert Dialog
        builder.setCancelable(true);
        builder.setTitle(title); // sets title of the alert dialog
        builder.setMessage(Message); //sets message of alert dialog
        builder.show(); // makes alert dialog visible
    }
}

