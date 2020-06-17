package com.example.stocksystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
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

public class manageUser extends Fragment
{
    DatabaseHelper dbh;
    Spinner spin;
    EditText passWord, userName;
    Button add, delete;
    String pass, user, condition;
    int defaultColor;
    View view;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        dbh = new DatabaseHelper(getActivity());
        spin = view.findViewById(R.id.spinner);
        add = view.findViewById(R.id.buttonAdd);
        delete = view.findViewById(R.id.buttonDelete);
        passWord = view.findViewById(R.id.editTextPassword);
        userName = view.findViewById(R.id.editTextUsername);
        addData();
        populateListView();
        DeleteData();
        return view;
    }

    public void addData()
    {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                pass = passWord.getText().toString();
                user = userName.getText().toString();
                defaultColor = Color.TRANSPARENT;

                if(pass.trim().isEmpty()==true || user.trim().isEmpty()==true)
                {
                    AlertDialog.Builder deleteBox = new AlertDialog.Builder(getActivity());
                    deleteBox.setTitle("Insert");
                    deleteBox.setMessage("Please enter data in each field");
                    deleteBox.setIcon(R.drawable.ic_delete_icon);
                    deleteBox.create();
                    deleteBox.show();
                    if(pass.trim().isEmpty() ==true )
                    {
                        passWord.setBackgroundColor(Color.RED);
                    }

                    if(user.trim().isEmpty()==true )
                    {
                        userName.setBackgroundColor(Color.RED);
                    }
                }
                else
                {
                    Boolean chkEmail = dbh.checkEmail(user);
                    if(chkEmail==true)
                    {
                        boolean isInserted = dbh.insertUser(user, pass);
                        if (isInserted = true) {
                            Toast.makeText(getActivity(), "Data Inserted Successfully", Toast.LENGTH_LONG).show();
                            passWord.setText("");
                            userName.setText("");
                            populateListView();
                        }
                        else {
                            Toast.makeText(getActivity(), "Insert Not Successful", Toast.LENGTH_LONG).show();
                            passWord.setText("");
                            userName.setText("");
                        }
                    }
                    else
                    {
                        Toast.makeText(getContext(),"Username already exist",Toast.LENGTH_LONG).show();
                    }
                }

                userName.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s)
                    {
                        userName.setBackgroundColor(defaultColor);
                    }
                });

                passWord.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s)
                    {
                        passWord.setBackgroundColor(defaultColor);
                    }
                });
            }
        });



    }

    private void populateListView()
    {
        Cursor data = dbh.getUserData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext())
        {
            listData.add(data.getString(data.getColumnIndex("admin_ID")));
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
                Cursor res = dbh.getUserUpdate(condition);
                if(res.getCount()==0)
                {
                    // showMessage("Error", "Nothing Found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext())
                {
                    userName.setText(res.getString(1));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }

    public void DeleteData()
    {
        delete.setOnClickListener(new View.OnClickListener() {
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
                        Integer deletedRows = dbh.deleteUser(spin.getSelectedItem().toString());
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


}
