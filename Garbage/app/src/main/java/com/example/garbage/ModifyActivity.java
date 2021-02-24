package com.example.garbage;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ModifyActivity extends  AppCompatActivity {

    //GUI variables
    private Button addItem;
    private TextView newWhat, newWhere;

    //Model: Database of items
    private static ItemsDB itemsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify);

        itemsDB= ItemsDB.get(ModifyActivity.this);

        //Text Fields
        newWhat=  findViewById(R.id.what_text);
        newWhere= findViewById(R.id.where_text);


        addItem= findViewById(R.id.add_button);
        // adding a new thing
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // extracting the user input text
                String whatS  = newWhat.getText().toString().trim();
                String whereS = newWhere.getText().toString().trim();

                // if the user has input a text we add it and tell the user it is added,
                // otherwise tell the user to write something in the input fields
                if ((whatS.length() > 0) && (whereS.length() > 0)) {
                    itemsDB.addItem(whatS, whereS);
                    newWhat.setText("");
                    newWhere.setText("");
                    Toast.makeText(ModifyActivity.this,
                            R.string.added_toast,
                            Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(ModifyActivity.this,
                            R.string.empty_toast,
                            Toast.LENGTH_LONG).show();
            }
        });

    }
}
