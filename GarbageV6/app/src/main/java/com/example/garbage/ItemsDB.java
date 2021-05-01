package com.example.garbage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Observable;
import com.example.garbage.database.ItemBaseHelper;
import com.example.garbage.database.ItemsDbSchema;
import com.example.garbage.database.ItemCursorWrapper;


public class ItemsDB extends Observable {
    private static ItemsDB sItemsDB;
    private static SQLiteDatabase mDatabase;
    private static Context sContext;


    // Setup the database with the initial garbage sorting knowledge
    private ItemsDB(Context context)  {
        if (listAll().size() == 0) fillItemsDBFromFile("garbage.txt");
    }

    // Singleton method to initialise the database
    public static ItemsDB get(Context context) {
        if (sItemsDB == null) {
            sContext = context;
            mDatabase= new ItemBaseHelper(context.getApplicationContext()).getWritableDatabase();
            sItemsDB = new ItemsDB(context);
        }
        return sItemsDB;
    }


    public ArrayList<Item> listAll() {
        ArrayList<Item> items= new ArrayList<Item>();
        ItemCursorWrapper cursor= queryItems(null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            items.add(cursor.getItem());
            cursor.moveToNext();
        }
        cursor.close();
        return items;
    }


    public String getWhere(String what) {
        String selection= ItemsDbSchema.ItemTable.Cols.WHAT + " = ?";
        Item newItem= new Item(what, "");
        String[] whereArgs = new String[]{newItem.getWhat()};
        ItemCursorWrapper cursor= queryItems(selection, whereArgs);
        cursor.moveToFirst();
        String result = null;
        if (!cursor.isAfterLast()) {
            Item item = cursor.getItem();
            cursor.close();
            result = item.getWhere();
        }
        return result;
    }


    public void addHelper(String what, String where){
        Item newItem= new Item(what, where);
        ContentValues values= getContentValues(newItem);
        mDatabase.insert(ItemsDbSchema.ItemTable.NAME, null, values);
        this.setChanged(); notifyObservers();
    }


    public int addItem(String what, String where){
        String whereInDB = getWhere(what);

        // null result, thus it doesn't exist already
        if (whereInDB == null) {
            addHelper(what, where);
            return 1;
        }

        // a complete duplicate, so do nothing
        else if (whereInDB.trim().equals(where.trim())) {
            return 0;
        }

        // otherwise, there is a different where
        else {
            addHelper(what, where);
            return 1;
        }
    }

    public void removeItem(String what){
        Item newItem= new Item(what, "");
        String selection= ItemsDbSchema.ItemTable.Cols.WHAT + " LIKE ?";
        int changed= mDatabase.delete(ItemsDbSchema.ItemTable.NAME, selection, new String[]{newItem.getWhat()});
        if (changed > 0) { this.setChanged(); notifyObservers();  }
    }


    // Database helper methods to convert between Items and database rows
    private static ContentValues getContentValues(Item item) {
        ContentValues values=  new ContentValues();
        values.put(ItemsDbSchema.ItemTable.Cols.WHAT, item.getWhat());
        values.put(ItemsDbSchema.ItemTable.Cols.WHERE, item.getWhere());
        return values;
    }


    static private ItemCursorWrapper queryItems(String whereClause, String[] whereArgs) {
        Cursor cursor= mDatabase.query(
                ItemsDbSchema.ItemTable.NAME,
                null, // Columns - null selects all columns
                whereClause, whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new ItemCursorWrapper(cursor);
    }

    public void close() {   mDatabase.close();   }


    // Method for reading a file from the Assets folder and loading its content into a HashMap.
    private void fillItemsDBFromFile(String filename) {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(sContext.getAssets().open(filename)));
            String line = reader.readLine();

            while (line != null) {
                String[] gItem = line.split(",");
                addItem(gItem[0], gItem[1]);
                line = reader.readLine();
            }
        } catch (IOException e) {
            Log.e(filename, "fillItemsDB: error reading file", e);
        }
    }
}
