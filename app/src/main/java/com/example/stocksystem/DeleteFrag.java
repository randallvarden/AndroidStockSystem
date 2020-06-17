/*  Author: Randall Varden
    Student Number: M5LBW6YV6
    Module Code: ITSD411
 */
package com.example.stocksystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DeleteFrag extends Fragment
{
    DatabaseHelper dbh;
    Spinner spin;
    TextView productName;
    Button deleteButton;
    String condition;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_delete, container, false);
        dbh = new DatabaseHelper(getActivity());
        spin = view.findViewById(R.id.spinner);
        deleteButton =view.findViewById(R.id.button);
        productName = view.findViewById(R.id.productName);
        populateListView();
        DeleteData();
        return view;
    }
    public void DeleteData()
    {
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder deleteBox = new AlertDialog.Builder(getActivity());
                deleteBox.setTitle("Delete");
                deleteBox.setMessage("Are you sure you want to delete?");
                deleteBox.setIcon(R.drawable.ic_delete_icon);

                deleteBox.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Integer deletedRows = dbh.deleteData(spin.getSelectedItem().toString());
                        if(deletedRows > 0)
                        {
                            Toast.makeText(getActivity(),"Data Deleted",Toast.LENGTH_LONG).show();
                            populateListView();
                        }
                        else
                        {
                            Toast.makeText(getActivity(),"Delete Unsuccessful",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                deleteBox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            deleteBox.create();
            deleteBox.show();
            }
        });
    }


    private void populateListView()
    {
        Cursor data = dbh.getData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext())
        {
            listData.add(data.getString(data.getColumnIndex("stock_ID")));
        }
        ListAdapter adapter = new ArrayAdapter<>(this.getActivity(),android.R.layout.simple_spinner_item
                ,listData);
        spin.setAdapter((SpinnerAdapter) adapter);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                condition =  spin.getItemAtPosition(position).toString();
                Cursor res = dbh.getDataUpdate(condition);
                if(res.getCount()==0)
                {
                    // showMessage("Error", "Nothing Found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext())
                {
                    productName.setText(res.getString(1));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }
}

