package com.example.garbage;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


public class UIFragment extends Fragment {
    //GUI variables
    private Button listItems;
    private TextView newWhat, newWhere;

    // Model: Database of items
    private static ItemsDB itemsDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemsDB = ItemsDB.get(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_ui, container, false);

        //Text Fields
//        newWhat = v.findViewById(R.id.what_text);
//        newWhere = v.findViewById(R.id.where_text);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // The search button for looking up items
            listItems = v.findViewById(R.id.list_button);
            listItems.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ListActivity.class);
                    startActivity(intent);
                }
            });
        };





/*        addItem = v.findViewById(R.id.add_button);
        // adding a new thing
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String whatS = newWhat.getText().toString().trim();
                String whereS = newWhere.getText().toString().trim();
                if ((whatS.length() > 0) && (whereS.length() > 0)) {
                    itemsDB.addItem(whatS, whereS);
                    newWhat.setText("");
                    newWhere.setText("");
                } else
                    Toast.makeText(getActivity(), R.string.empty_toast, Toast.LENGTH_LONG).show();
            }
        });*/
        return v;
    }
}



