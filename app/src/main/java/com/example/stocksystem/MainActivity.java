/*  Author: Randall Varden
    Student Number: M5LBW6YV6
    Module Code: ITSD411
 */
package com.example.stocksystem;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button loginButton; //declares UI components
    EditText txtUsername;
    EditText txtPassword;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(getApplicationContext());
        loginButton = (Button) findViewById(R.id.loginButton); //initializes components
        txtUsername = (EditText) findViewById(R.id.emailAdressField);
        txtPassword = (EditText) findViewById(R.id.passwordField);
        onLogin();
    }

    private void onLogin()
    {
        final Intent intent = new Intent(this, LoginActivity.class);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String uName = txtUsername.getText().toString(); //gets the username from the text field called txtUsername and assigns it to a String variable called uName
                String pass = txtPassword.getText().toString(); //gets the password from the text field called txtPassword and assigns it to a String variable called pass
                Boolean checkemailpassword = myDb.emailpassword(uName, pass);
                if (uName.equals("admin")&&pass.equals("admin") || checkemailpassword ==true) // checks if the variable pass and uName is equal to admin
                {
                    startActivity(intent); //starts the activity
                    txtPassword.setText(""); //Sets the text fields back to null
                    txtUsername.setText("");
                    Toast.makeText(getApplicationContext(), "Login Successful",Toast.LENGTH_SHORT).show(); // toast popup notifying the user that login is successful
                }
                else //executes if username and password is incorrect
                {
                    Toast.makeText(getApplicationContext(), "Incorrect Username and Password",Toast.LENGTH_SHORT).show(); // toast popup notifying the user that login is unsuccessful
                    txtPassword.setText(""); //resets fields back to null
                    txtUsername.setText("");
                }

            }
        });
    }
}