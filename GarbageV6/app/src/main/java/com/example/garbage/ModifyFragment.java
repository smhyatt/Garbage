package com.example.garbage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


public class ModifyFragment extends Fragment {
    //GUI variables
    private Button addItem;
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
        final View v = inflater.inflate(R.layout.fragment_modify, container, false);

        //Text Fields
        newWhat  = v.findViewById(R.id.what_text);
        newWhere = v.findViewById(R.id.where_text);

        addItem = v.findViewById(R.id.add_button);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // extracting the user input text
                String whatS  = newWhat.getText().toString().trim();
                String whereS = newWhere.getText().toString().trim();

                // if the user has input a text we add it and tell the user it is added,
                // otherwise tell the user to write something in the input fields
                if ((whatS.length() > 0) && (whereS.length() > 0)) {
                    int exists = itemsDB.addItem(whatS, whereS);
                    newWhat.setText("");
                    newWhere.setText("");
                    int toast = exists == 1
                            ? R.string.added_toast
                            : R.string.exist_toast;
                    Toast.makeText(getActivity(), toast, Toast.LENGTH_LONG).show();

                } else
                    Toast.makeText(getActivity(),
                            R.string.empty_toast,
                            Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }
}




