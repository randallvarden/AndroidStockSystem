/*  Author: Randall Varden
    Student Number: M5LBW6YV6
    Module Code: ITSD411
 */
package com.example.stocksystem;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class homeFrag extends Fragment
{
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.homepage_layout, container, false);
        return view;
    }
}
