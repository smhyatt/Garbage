package com.example.shopnshare;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


public class ListEditorActivity extends AppCompatActivity {

    int noteID;
    private TextView listName;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_editor);


        EditText editText = (EditText)findViewById(R.id.editText);
        Intent intent = getIntent();
        noteID = intent.getIntExtra("noteID", -1);

        //listName = findViewById(R.id.name_input);

        if(noteID != -1)
        {
            editText.setText(MainActivity.notes.get(noteID));
        }

        else
        {
            //MainActivity.notes.add(listName.getText().toString().trim());                // as initially, the note is empty
            MainActivity.notes.add("");                // as initially, the note is empty
            noteID = MainActivity.notes.size() - 1;
            MainActivity.arrayAdapter.notifyDataSetChanged();
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence cs, int i, int i1, int i2) {
                // add your code here
                //adapter.getFilter().filter(cs);
            }

            @Override
            public void onTextChanged(CharSequence cs, int i, int i1, int i2) {
                MainActivity.notes.set(noteID, String.valueOf(cs));
                MainActivity.arrayAdapter.notifyDataSetChanged();
                // Creating Object of SharedPreferences to store data in the phone
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet(MainActivity.notes);
                sharedPreferences.edit().putStringSet("notes", set).apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // add your code here
            }
        });

        final TextView textView = findViewById(R.id.txtView);
        Button showDialogBtn = findViewById(R.id.showsnackbarbtn);
        showDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ListEditorActivity.this);
                // String array for alert dialog multi choice items
                String[] colorsArray = new String[]{"Black", "Orange", "Green", "Yellow", "White", "Purple"};
                // Boolean array for initial selected items
                final boolean[] checkedColorsArray = new boolean[]{
                        true, // Black checked
                        false, // Orange
                        false, // Green
                        true, // Yellow checked
                        false, // White
                        false  //Purple
                };
                // Convert the color array to list
                final List<String> colorsList = Arrays.asList(colorsArray);
                //setTitle
                builder.setTitle("Select colors");
                //set multichoice
                builder.setMultiChoiceItems(colorsArray, checkedColorsArray, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        // Update the current focused item's checked status
                        checkedColorsArray[which] = isChecked;
                        // Get the current focused item
                        String currentItem = colorsList.get(which);
                        // Notify the current action
                        Toast.makeText(getApplicationContext(), currentItem + " " + isChecked, Toast.LENGTH_SHORT).show();
                    }
                });
                // Set the positive/yes button click listener
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when click positive button
                        textView.setText("Your preferred colors..... \n");
                        for (int i = 0; i<checkedColorsArray.length; i++){
                            boolean checked = checkedColorsArray[i];
                            if (checked) {
                                textView.setText(textView.getText() + colorsList.get(i) + "\n");
                            }
                        }
                    }
                });
                // Set the neutral/cancel button click listener
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when click the neutral button
                    }
                });
                AlertDialog dialog = builder.create();
                // Display the alert dialog on interface
                dialog.show();

            }


        });

    };


}