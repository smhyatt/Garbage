package com.example.garbagesorting;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;

import com.example.garbagesorting.database.ItemsDbSchema;
import com.example.garbagesorting.database.ItemCursorWrapper;
import com.example.garbagesorting.database.QueryBuilder;
import com.example.garbagesorting.network.httpThread;

import android.database.Cursor;


import static android.content.ContentValues.TAG;

/* ItemsDB is now a monitor:
 * The methods for altering the db is atomic (either method is fully executed or not at all) */
public class ItemsDB extends Observable {
    private static ItemsDB sItemsDB;
    private static Context context;
    private static SQLiteDatabase database;
    private final static String garbageURL = "https://garbageServer.jstaunstrup.repl.co";
    private final HashMap<String, String> itemsMap = new HashMap<>();
    private QueryBuilder queryBuilder = new QueryBuilder(garbageURL);

    // Constructor
    private ItemsDB(Context context) {
        // ultimatively fills the local copy, itemsMap, w/ all database items
        // networkDB -> thread (runs in bg) -> networkfetcher.fetcher -> parseString -> initItem
        networkDB(queryBuilder.SELECTALL(), QueryBuilder.QueryType.SELECTALL);
    }

    // Makes ItemsDB a singleton class
    public static synchronized ItemsDB get(Context inpContext) {
        if (sItemsDB == null) {
            context  = inpContext;
            sItemsDB = new ItemsDB(context);
        }
        return sItemsDB;
    }

    // start a thread that will result in a http connection to a server
    // Slow operations should be performed using a thread!
    private synchronized void networkDB(String url, QueryBuilder.QueryType queryType) {
        Runnable runnable = new httpThread(url, queryType);
        new Thread(runnable).start();
    }

    // Add entry in hashmap (local copy of db)
    public synchronized void initItem(String what, String where) {
        itemsMap.put(what, where);
    }

    // Adds an item to local and server based database
    public synchronized void addItem(String what, String where){
        // item is not already in (local) db and an empty string wasn't entered
        if (what.length() > 0 && !ItemIsInDB(what)) {
            itemsMap.put(what, where);
            objChanged_NotifyObservers();
            // insert in database located on server
            networkDB(queryBuilder.INSERT(what, where), QueryBuilder.QueryType.INSERT);
        }
    }

    // Removes item from database local as well as on server
    public synchronized void removeItem(String what){
        if (itemsMap.containsKey(what)) {
            itemsMap.remove(what);
            objChanged_NotifyObservers();
            networkDB(queryBuilder.DELETE(what), QueryBuilder.QueryType.DELETE);
        }
    }

    // Not too efficient. Ends up calling initItem for every old and new item in the db.
    public void updateLocalDB() {
        networkDB(queryBuilder.SELECTALL(), QueryBuilder.QueryType.SELECTALL);
    }

    /* >> Part of the observer pattern <<
     * This observable object changed, notify the observers */
    private void objChanged_NotifyObservers() {
        this.setChanged();
        notifyObservers();
    }

    // Returns size of local db copy
    public int size() {
        return itemsMap.size();
    }

    // Check if an item already exists in database
    public Boolean ItemIsInDB(String what) {
        return itemsMap.containsKey(what);
    }

    // Get a list w/ all Items of the database
    public ArrayList<Item> getAll() {
        ArrayList<Item> result = new ArrayList<>();
        for (HashMap.Entry<String, String> KVpair: itemsMap.entrySet()) {
            result.add(new Item(KVpair.getKey(), KVpair.getValue()));
        }
        return result;
    }

    // Build final query result as a string
    @RequiresApi(api = Build.VERSION_CODES.N)
    public String buildQueryResult(String itemGoal) {
        String queryResult = searchItem(itemGoal);
        return itemGoal.toUpperCase()
                + " should be placed in: "
                + queryResult.toUpperCase();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String searchItem(String goal) {
        String retVal = "not found";

        // Extract item that matches the goal
        Optional<Map.Entry<String, String>> item = itemsMap.entrySet().stream()
                .filter(i -> goal.equals(i.getKey()))
                .findAny();

        if (item.isPresent()) {
            retVal = item.get().getValue();
        }
        return retVal;
    }



    /* *** Unused methods *** */

    // alternative version
    public String searchItem2(String goal) {
        String goalLower = goal.toLowerCase();

        for (HashMap.Entry<String, String> i: itemsMap.entrySet()) {
            if (i.getKey().toLowerCase().equals(goalLower)) {
                return i.getValue();
            }
        }
        return "not found";
    }

    // Make a string containing all of garbage items and their placement
    public String listItems() {
        String r = "";
        for (HashMap.Entry <String, String> item: itemsMap.entrySet())
            r = r + "\n " + item.getKey() + " in "  + item.getValue();
        return r;
    }

    // Get a cursor pointing to result of query
    static private ItemCursorWrapper queryItems(String whereClause, String[] whereArgs) {
        Cursor cursor = database.query(ItemsDbSchema.ItemTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new ItemCursorWrapper(cursor);
    }

    // Database helper method to build ContentValue: From item to database rows.
    private static ContentValues getContentValues(String what, String where) {
        ContentValues values = new ContentValues();
        values.put(ItemsDbSchema.ItemTable.Columns.WHAT,  what);
        values.put(ItemsDbSchema.ItemTable.Columns.WHERE, where);
        Log.i("contentvals: ", values.toString());
        return values;
    }

    public void close() {
        database.close();
    }

}
