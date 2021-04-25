package com.example.garbagesorting;

import android.content.Context;
import android.util.Log;

import com.example.garbagesorting.database.ItemsDbSchema;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static android.content.ContentValues.TAG;

public class JsonToItems {
   // public JsonToItems() {}


    // Read a files content
    public String readFile(String filename, Context context) {
        try {
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(context.getAssets().open(filename)));

            String allData = "";
            String line = reader.readLine();
            while (line != null) {
                allData += line;
                line = reader.readLine();
            }
            return allData;

        } catch (IOException e) {
            Log.e(TAG, "Failed to read file: " + filename, e);
            return null;
        }
    }

    // if reading from local json file it should be "whatg" and "whereg" for the entries.
    // Should change the json file to also say "what" and "whereC"
    public void parseItems(String jsonString) {
        ItemsDB itemsDB = ItemsDB.get(null); // Singleton has already been created
        try {
            JSONArray itemsA = new JSONArray(jsonString);
            int len = itemsA.length();

            if (len > 0) {
                if (ItemsDbSchema.dbIsOnServer) {
                    for (int i = 0; i < len; i++) {
                        // only fills local db
                        itemsDB.initItem(
                                itemsA.getJSONObject(i).getString(
                                        ItemsDbSchema.ItemTable.Columns.WHAT),
                                itemsA.getJSONObject(i).getString(
                                        ItemsDbSchema.ItemTable.Columns.WHERE));
                    }
                } else {
                    for (int i = 0; i < len; i++) {
                        // calls addItem instead which would add items to local and server side db
                        itemsDB.addItem(
                                itemsA.getJSONObject(i).getString("whatg"),
                                itemsA.getJSONObject(i).getString("whereg"));
                    }
                }
            }
        }
        catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        }
    }
}
