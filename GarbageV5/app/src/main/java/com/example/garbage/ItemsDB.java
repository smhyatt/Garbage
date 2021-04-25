package com.example.garbage;

import android.content.Context;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;


public class ItemsDB {
    private static ItemsDB sItemsDB;
    private static Context sContext;
    private final HashMap<String, String> itemsMap= new HashMap<String, String>();

    // Singleton method to initialise the database
    public static ItemsDB get(Context context) {
        if (sItemsDB == null) {
            sContext = context;
            sItemsDB = new ItemsDB(context);
        }
        return sItemsDB;
    }

    // Setup the database with the initial garbage sorting knowledge
    private ItemsDB(Context context)  {
        fillItemsDBFromFile("garbage.txt");
    }

    public String listItems() {
        String r= "";
        for (HashMap.Entry <String, String> item: itemsMap.entrySet())
            r= r+"\n Buy "+item.getKey() + " in: "  + item.getValue();
        return r;
    }

    // A property for getting the database
    public HashMap<String, String> getItemsDB() {
        return itemsMap;
    }

    // A property for getting the current size of the database
    public int size() {
        return itemsMap.size();
    }

    // A method for looking up an item in the database
    public String getWhere(String what) {
        String item = itemsMap.get(what);
        if (item == null) {
            return "not found";
        }
        return item;
    }

    // Method for adding a new item to the database
    public void addItem(String what, String where){
        itemsMap.put(what, where);
    }


    // Method for reading a file from the Assets folder and loading its content into a HashMap.
    private void fillItemsDBFromFile(String filename) {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(sContext.getAssets().open(filename)));
            String line = reader.readLine();

            while (line != null) {
                String[] gItem = line.split(",");
                itemsMap.put(gItem[0], gItem[1]);
                line = reader.readLine();
            }
        } catch (IOException e) {
            Log.e(filename, "fillItemsDB: error reading file", e);
        }
    }

}













