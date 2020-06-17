/*  Author: Randall Varden
    Student Number: M5LBW6YV6
    Module Code: ITSD411
 */
package com.example.stocksystem;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InsertFrag extends Fragment //extends fragment
{
    DatabaseHelper myDb; //declares variables
    EditText editProductName, editQuantity, editPrice, editSPrice;
    TextView labelProductName, labelQuantity, labelPrice, labelSPrice;
    Button btnAddData;
    int intQ = 1;
    int intP = 10;
    int intSP = 0;
    int defaultColor;
    View view;
  //  @Override
 //   public void onCreate(Bundle savedInstanceState) {
      //  super.onCreate(savedInstanceState);
    //    myDb = new DatabaseHelper(getActivity());
        //editProductName = (EditText)findView

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
                view = inflater.inflate(R.layout.fragment_insert, container, false);
                myDb = new DatabaseHelper(getActivity());
                labelPrice =(TextView)view.findViewById(R.id.textViewProductName); //initializes variables
           // editQuantity = (EditText)view.findViewById(R.id.editTextQuantity);
           // editPrice = (EditText)view.findViewById(R.id.editTextPrice);
            btnAddData = (Button)view.findViewById(R.id.buttonAdd);
            addData(); // calls method addData()
            watch(); //calls method watch()
            return view;
        }

        public void addData() //method that adds data
        {
            btnAddData.setOnClickListener(new View.OnClickListener() { // button listener
                @Override
                public void onClick(View v)
                {
                    editProductName = (EditText)view.findViewById(R.id.editTextProductName);
                    editQuantity = (EditText)view.findViewById(R.id.editTextQuantity); //fetches component in XML and stores it in the java variable
                    editPrice = (EditText)view.findViewById(R.id.editTextPrice);
                    editSPrice = (EditText)view.findViewById(R.id.editTextSellingPrice);
                    defaultColor = Color.TRANSPARENT;

                    if(editProductName.getText().toString().trim().isEmpty() ==true || editQuantity.getText().toString().trim().isEmpty()==true || editPrice.getText().toString().trim().isEmpty()==true || editSPrice.getText().toString().trim().isEmpty()==true)
                    {//checks if the variables are empty
                        AlertDialog.Builder deleteBox = new AlertDialog.Builder(getActivity());
                        deleteBox.setTitle("Insert");
                        deleteBox.setMessage("Please enter data in each field");
                        deleteBox.setIcon(R.drawable.ic_delete_icon);
                        deleteBox.create();
                        deleteBox.show(); //displays an error message
                        if(editProductName.getText().toString().trim().isEmpty() ==true )
                        {
                            editProductName.setBackgroundColor(Color.RED); //if the field is empty, it changes the background color to red
                        }

                        if(editQuantity.getText().toString().trim().isEmpty()==true )
                        {
                            editQuantity.setBackgroundColor(Color.RED);
                        }
                        if(editPrice.getText().toString().trim().isEmpty()==true )
                        {
                            editPrice.setBackgroundColor(Color.RED);
                        }
                        if(editSPrice.getText().toString().trim().isEmpty()==true)
                        {
                            editSPrice.setBackgroundColor(Color.RED);
                        }
                    }
                    else
                        {
                            intQ = Integer.parseInt(editQuantity.getText().toString()); //converts string value into an integer
                            intP = Integer.parseInt(editPrice.getText().toString());
                            intSP = Integer.parseInt(editSPrice.getText().toString());
                            boolean isInserted = myDb.insertData(editProductName.getText().toString(), intQ, intP, intSP); //calls insert method from database helper class
                            if (isInserted = true) {
                                Toast.makeText(getActivity(), "Data Inserted Successfully", Toast.LENGTH_LONG).show(); //displays notification to user
                                editProductName.setText(""); //resets the field values
                                editQuantity.setText("");
                                editSPrice.setText("");
                                editPrice.setText("");
                            } else {
                                Toast.makeText(getActivity(), "Insert Not Successful", Toast.LENGTH_LONG).show(); //alerts the user that inserting data into the database was not successful
                                editProductName.setText("");
                                editQuantity.setText("");
                                editSPrice.setText("");
                                editPrice.setText("");
                            }
                        }
                    editProductName.addTextChangedListener(new TextWatcher() { //adds a text change lister to the edit text product name
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s)
                        {
                            editProductName.setBackgroundColor(defaultColor); //changes the background color to the default color once text is entered
                        }
                    });

                    editQuantity.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s)
                        {
                            editQuantity.setBackgroundColor(defaultColor);
                        }
                    });

                    editPrice.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s)
                        {
                            editPrice.setBackgroundColor(defaultColor);
                        }
                    });

                    editSPrice.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s)
                        {
                            editSPrice.setBackgroundColor(defaultColor);
                        }
                    });
                }
            });
        }

    public void watch()
    {

    }
}
