/*  Author: Randall Varden
    Student Number: M5LBW6YV6
    Module Code: ITSD411
 */
package com.example.stocksystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class LoginActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt; //declaration of variables

    @Override
    protected void onCreate(Bundle savedInstanceState) // code that maps the xml layout on the onCreate View
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_layout);
        dl = (DrawerLayout)findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(this,dl,R.string.Open, R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);

        dl.addDrawerListener(abdt);
        abdt.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView nav_view = (NavigationView)findViewById(R.id.nav_view);

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() { // adds item selected listener
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) { //a method to check which item is selected in the menu
                int id = menuItem.getItemId();

                if(id == R.id.iviewStock)
                {
                    Toast.makeText(LoginActivity.this, "View Stock", Toast.LENGTH_SHORT).show(); // displays toast message alerting the user on selection
                    setFragment(new ViewFrag()); //sets fragment according to the user's selection
                }
                if(id == R.id.addStock)
                {
                    setFragment(new InsertFrag());
                    Toast.makeText(LoginActivity.this, "Add Stock", Toast.LENGTH_SHORT).show();
                }
                if(id == R.id.deleteStock)
                {
                    Toast.makeText(LoginActivity.this, "Delete Stock", Toast.LENGTH_SHORT).show();
                    setFragment(new DeleteFrag());
                }
                if(id == R.id.updateStock)
                {
                    Toast.makeText(LoginActivity.this, "Update Stock", Toast.LENGTH_SHORT).show();
                    setFragment(new UpdateFrag());
                }
                if(id == R.id.logout)
                {
                    Toast.makeText(LoginActivity.this, "Logging Out", Toast.LENGTH_SHORT).show();
                    finish();
                }
                if(id == R.id.home)
                {
                    Toast.makeText(LoginActivity.this, "Home", Toast.LENGTH_SHORT).show();
                    setFragment(new homeFrag());
                }
                if(id == R.id.manageUser)
                {
                    Toast.makeText(LoginActivity.this, "Manage User", Toast.LENGTH_SHORT).show();
                    setFragment(new manageUser());
                }

                return true;
            }
        });
    }
    public void setFragment(Fragment f) //a method setFragment to replace the frame on the homePage with the desired fragments created
    {
        FragmentManager fManager = getSupportFragmentManager();
        FragmentTransaction ft = fManager.beginTransaction();
        ft.replace(R.id.frame, f);
        ft.commit();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return abdt.onOptionsItemSelected(item)|| super.onOptionsItemSelected(item);
    }
}
