package com.example.garbagesorting;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

// Fragment containing What items? Where? and buttons ADD NEW ITEM,  LIST ALL ITEMS and DELETE ITEM
//
public class UIFragment extends Fragment {
    //GUI variables
    private Button btnListItems, btnWhere, btnAdd, btnUpdate;
    private EditText searchItemText, placementItemText;

    // Model: Database of items
    private static ItemsDB itemsDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemsDB = ItemsDB.get(getActivity()); // first, and only, instantiation of ItemsDB
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_ui, container, false);

        // Connect layout views by id with widgets
        searchItemText    = v.findViewById(R.id.enter_field);
        placementItemText = v.findViewById(R.id.placement_field);
        btnWhere          = v.findViewById(R.id.where_button);
        btnAdd            = v.findViewById(R.id.add_button);
        btnUpdate         = v.findViewById(R.id.update_local_db_btn);

        btnWhere.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                String searchKey = searchItemText.getText().toString();
                String placeItem = itemsDB.buildQueryResult(searchKey);
                searchItemText.setText(placeItem);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String garbageItem = searchItemText.getText().toString();
                String placement   = placementItemText.getText().toString();
                itemsDB.addItem(garbageItem, placement);
                Toast.makeText(getActivity(), "Garbage item added",  Toast.LENGTH_LONG).show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemsDB.updateLocalDB();
                Toast.makeText(getActivity(), "Updating Garbage Info {^_0}", Toast.LENGTH_LONG).show();
            }
        });

        // Only show "list all items" button if in portrait mode
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            btnListItems = v.findViewById(R.id.list_items_btn);
            btnListItems.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Start the ListActivity activity
                    Intent intent = new Intent(getActivity(), ListActivity.class);
                    startActivity(intent);
                }
            });
        }
        return v;
    }
}
