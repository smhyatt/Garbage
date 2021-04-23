package com.example.garbage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    // GUI variables
    private Button button;
    private TextView items, newWhat, newWhere;
    private EditText mEdit;

    // Model: Database of items
    private static ItemsDB itemsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialise the Singleton database
        itemsDB= ItemsDB.get(MainActivity.this);
        items= findViewById(R.id.items);

        // Text Fields
//        newWhat=  findViewById(R.id.what_text);
//        newWhere= findViewById(R.id.where_text);

        // The search button for looking up items
        button = (Button) findViewById(R.id.search_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // On click read the user input and run a search of the item
                mEdit = (EditText) findViewById(R.id.user_input);
                mEdit.setText(itemsDB.getWhere(mEdit.getText().toString()));
            }
        });

    }
}
