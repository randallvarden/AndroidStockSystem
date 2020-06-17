package com.example.stocksystem;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class UpdateFrag extends Fragment
{
    DatabaseHelper dbh;
    Button updateButton;
    EditText quantity;
    EditText price;
    EditText productName;
    EditText sellingPrice;
    Spinner spin;
    String condition;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_update, container, false);
        dbh = new DatabaseHelper(getActivity());
        updateButton = view.findViewById(R.id.buttonupdate);
        quantity = view.findViewById(R.id.editTextQuantity);
        productName= view.findViewById(R.id.editTextProductName);
        price = view.findViewById(R.id.editTextPrice);
        sellingPrice = view.findViewById(R.id.editTextSPrice);
        spin = view.findViewById(R.id.spinner);
        UpdateData();
        populateListView();
        return view;
    }

    public void UpdateData()
    {
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                int intQ = Integer.parseInt(quantity.getText().toString());
                int intP = Integer.parseInt(price.getText().toString());
                int intSP = Integer.parseInt(sellingPrice.getText().toString());
                boolean isUpdate = dbh.updateData(spin.getSelectedItem().toString(),productName.getText().toString(),intQ, intP,intSP);
                if(isUpdate = true)
                {
                    Toast.makeText(getActivity(), "Data Updated", Toast.LENGTH_LONG).show();
                    productName.setText("");
                    quantity.setText("");
                    price.setText("");
                    sellingPrice.setText("");
                }
                else
                {
                    Toast.makeText(getActivity(), "Data not Updated", Toast.LENGTH_LONG).show();
                    productName.setText("");
                    quantity.setText("");
                    price.setText("");
                    sellingPrice.setText("");
                }
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
                    quantity.setText(res.getString(2));
                    price.setText(res.getString(3));
                    sellingPrice.setText(res.getString(4));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }
}
