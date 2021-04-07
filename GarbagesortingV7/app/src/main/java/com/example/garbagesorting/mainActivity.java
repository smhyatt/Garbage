package com.example.garbagesorting;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static java.lang.Thread.sleep;

public class mainActivity extends AppCompatActivity {

    private FragmentManager fm;
    Fragment fragmentUI, fragmentList;

    // Database containing items
    private ItemsDB itemsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.garbage);
        // Fetch garbage database
        itemsDB = ItemsDB.get(mainActivity.this);

        fm = getSupportFragmentManager();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            /* LANDSCAPE orientation */
            fragmentUI = fm.findFragmentById(R.id.container_ui_landscape);
            fragmentList = fm.findFragmentById(R.id.container_list);
            if ((fragmentUI == null) && (fragmentList == null)) {
                fragmentUI   = new UIFragment(); // A / B
                /* Needs time for the local DB to be< instantiated, so there is something to show in
                 * the list fragment. Otherwise it is empty at app launch in landscape mode */
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                fragmentList = new ListFragment(); // C
                fm.beginTransaction()
                        .add(R.id.container_ui_landscape, fragmentUI)  // connecting fragment and the frame in the layout
                        .add(R.id.container_list, fragmentList) // connecting fragmentList to the frame container_list
                        .commit();
            }
        } else {
            /* PORTRAIT orientation */
            fragmentUI = fm.findFragmentById(R.id.container_ui_portrait);
            if (fragmentUI == null) {
                fragmentUI = new UIFragment(); // A
                fm.beginTransaction()
                        // kobler rammen i et layout med et fragment
                        .add(R.id.container_ui_portrait, fragmentUI)
                        .commit();
            }
        }
    }
}
