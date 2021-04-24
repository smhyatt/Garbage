package com.example.garbage;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


public class UIFragment extends Fragment {
    //GUI variables
    private Button listItems, findItem;
    private TextView newWhat;

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
        newWhat = v.findViewById(R.id.user_input);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            listItems = v.findViewById(R.id.list_button);
            listItems.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ListActivity.class);
                    startActivity(intent);
                }
            });
        };

        findItem = v.findViewById(R.id.search_button);
        findItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String whatS = newWhat.getText().toString().trim();
                if (whatS.length() > 0) {
                    String whereS = itemsDB.getWhere(whatS);
                    newWhat.setText(whereS);
                } else
                    Toast.makeText(getActivity(), R.string.empty_toast, Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }
}



