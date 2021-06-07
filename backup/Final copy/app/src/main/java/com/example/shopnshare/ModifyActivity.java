package com.example.shopnshare;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class ModifyActivity extends AppCompatActivity {
    private FragmentManager fm;
    Fragment fragmentAdd;

    // Model: Database of items
    private static ListDB listDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding);
        listDB = ListDB.get(this);

        fm= getSupportFragmentManager();
        //Orientation portrait
        fragmentAdd = fm.findFragmentById(R.id.container_add);
        if (fragmentAdd == null) {
            fragmentAdd = new ModifyFragment();
            fm.beginTransaction()
                    .add(R.id.container_add, fragmentAdd)
                    .commit();
        }
    }

}


