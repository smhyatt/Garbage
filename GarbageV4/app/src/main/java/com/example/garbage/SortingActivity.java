package com.example.garbage;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class SortingActivity extends AppCompatActivity {
    //Shopping V4 using Fragments and landscape orientation

    private FragmentManager fm;
    Fragment fragmentUI, fragmentList;

    //Model: Database of items
    private static ItemsDB itemsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sorting);
        itemsDB = ItemsDB.get(this);
        fm = getSupportFragmentManager();
        setUpFragments();
    }

    private void setUpFragments() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fragmentUI= fm.findFragmentById(R.id.container_ui_landscape);
            fragmentList= fm.findFragmentById(R.id.container_list);
            if ((fragmentUI == null) && (fragmentList == null)) {
                fragmentUI= new UIFragment();
                fragmentList= new ListFragment();
                fm.beginTransaction()
                        .add(R.id.container_ui_landscape, fragmentUI)
                        .add(R.id.container_list, fragmentList)
                        .commit();
            }
        } else {
            //Orientation portrait
            fragmentUI= fm.findFragmentById(R.id.container_ui_portrait);
            if (fragmentUI== null) {
                fragmentUI = new UIFragment();
                fm.beginTransaction()
                        .add(R.id.container_ui_portrait, fragmentUI)
                        .commit();
            }
        }
    }

}
