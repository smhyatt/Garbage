package com.example.shopnshare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import com.example.shopnshare.database.ItemBaseHelper;
import com.example.shopnshare.database.ItemsDbSchema;
import com.example.shopnshare.database.ItemCursorWrapper;


public class ListDB extends Observable {
    private static ListDB sListDB;
    private static SQLiteDatabase mDatabase;
    private static Context sContext;


    // Setup the database with the initial garbage sorting knowledge
    private ListDB(Context context, String table_name)  {
        if (listAll(table_name).size() == 0) fillDB(); //fillItemsDBFromFile("initial.txt");
    }

    // Singleton method to initialise the database
    public static ListDB get(Context context, String table_name) {
        if (sListDB == null) {
            sContext = context;
            mDatabase= new ItemBaseHelper(context.getApplicationContext()).getWritableDatabase();
            sListDB = new ListDB(context, table_name);
        }
        return sListDB;
    }


    public ArrayList<List> listAll(String table_name) {
        ArrayList<List> lists = new ArrayList<List>();
        ItemCursorWrapper cursor= queryItems(table_name, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            lists.add(cursor.getItem());
            cursor.moveToNext();
        }
        cursor.close();
        return lists;
    }


    /*
        public String getWhere(String what) {
            String selection= ItemsDbSchema.ItemTable.Cols.WHAT + " = ?";
            List newList = new List(what, "");
            String[] whereArgs = new String[]{newList.getName()};
            ItemCursorWrapper cursor= queryItems(selection, whereArgs);
            cursor.moveToFirst();
            String result = null;
            if (!cursor.isAfterLast()) {
                List list = cursor.getItem();
                cursor.close();
                result = list.getWhere();
            }
            return result;
        }
    */

    public void createTable(String table_name) {
        mDatabase.execSQL("create table " + table_name + "(" +
                ItemsDbSchema.ItemTable.Cols.ITEMS + ")"
        );
    }


    public void addItem(String table_name, String item){
        List newList = new List(item);
        ContentValues values= getContentValues(newList);
        mDatabase.insert(table_name, null, values);
        this.setChanged(); notifyObservers();
    }


/*    public void addHelper(String what, String where){
        List newList = new List(what, where);
        ContentValues values= getContentValues(newList);
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
    }*/

/*    public void removeItem(String name){
        List newList = new List(name, new ArrayList<>());
        //List newList = new List(name);
        String selection= ItemsDbSchema.ItemTable.Cols.NAME + " LIKE ?";
        int changed= mDatabase.delete(ItemsDbSchema.ItemTable.TABLENAME, selection, new String[]{newList.getName()});
        if (changed > 0) { this.setChanged(); notifyObservers();  }
    }*/


    // Database helper methods to convert between Items and database rows
    private static ContentValues getContentValues(List list) {
        ContentValues values=  new ContentValues();
        values.put(ItemsDbSchema.ItemTable.Cols.ITEMS, list.getItem());
        //values.put(ItemsDbSchema.ItemTable.Cols.WHERE, list.getWhere());
        return values;
    }


    static private ItemCursorWrapper queryItems(String table_name, String whereClause, String[] whereArgs) {
        Cursor cursor= mDatabase.query(
                table_name,
                null, // Columns - null selects all columns
                whereClause, whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new ItemCursorWrapper(cursor);
    }

    public void close() {   mDatabase.close();   }

    private void fillDB() {
        createTable("New Year's Party");
        addItem("New Year's Party", "champagne");
        addItem("New Year's Party", "cake");
        addItem("New Year's Party", "hats");
        addItem("New Year's Party", "snacks");
        addItem("New Year's Party", "beer");

        /*add("New Year's Party", new ArrayList<String>(
                Arrays.asList("champagne", "music", "cake")));
        addList("Birthday", new ArrayList<String>(
                Arrays.asList("cake", "cookie dough", "soda", "hot dogs", "burger buns", "sausages", "ketchup")));*/
    }


    // Method for reading a file from the Assets folder and loading its content into a HashMap.
/*    private void fillItemsDBFromFile(String filename) {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(sContext.getAssets().open(filename)));
            String line = reader.readLine();

            while (line != null) {
                String[] gItem = line.split(",");
                //addList(gItem[0]);
                addList(gItem[0], gItem[1]);
                line = reader.readLine();
            }
        } catch (IOException e) {
            Log.e(filename, "fillItemsDB: error reading file", e);
        }
    }*/
}
