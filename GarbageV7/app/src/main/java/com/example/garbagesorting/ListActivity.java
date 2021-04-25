package com.example.garbagesorting;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

// Activity Y
public class ListActivity extends AppCompatActivity {
    private FragmentManager fm;
    Fragment fragmentList;

    // Model: Database of items
    private static ItemsDB itemsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        itemsDB = ItemsDB.get(this);

        fm = getSupportFragmentManager();

        //Orientation portrait
        fragmentList = fm.findFragmentById(R.id.container_list);
        if (fragmentList == null) {
            fragmentList = new ListFragment(); // C
            fm.beginTransaction()
                    // kobler rammen container list med faktisk fragment
                    .add(R.id.container_list, fragmentList)
                    .commit();
        }
    }
}