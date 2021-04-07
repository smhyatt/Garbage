package com.example.garbagesorting.network;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.garbagesorting.ItemsDB;
import com.example.garbagesorting.JsonToItems;
import com.example.garbagesorting.database.QueryBuilder;

/* Most of this class is copied from:
 * Android Programming: The Big Nerd Ranch Guide
 * by Bill Phillips, Chris Stewart and Kristin Marsicano Chapter 25 */

public class NetworkFetcher {
    private static final String TAG = "NetworkFetcher";
    private static final JsonToItems parser = new JsonToItems();

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " +  urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    /* ### For INSERT or DELETE queries ###
     * The server does not give anything to read in these cases.
     * So this avoids throwing exceptions every time we insert or delete an item */
    public void connectURL(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new IOException(connection.getResponseMessage() +
                    ": with " +  urlSpec);
        }
        connection.disconnect();
    }

    /* Connect w/ the server containing database and execute query defined in the url *
     * If server gives a result back (GETALL select), then it is parsed and added to local db */
    public void connect(String url, QueryBuilder.QueryType queryType) {
        try {
            switch (queryType) {
                case INSERT:
                case DELETE:
                    connectURL(url);
                    break;
                case SELECTALL:
                    String jsonStr = new String(getUrlBytes(url));
                    parser.parseItems(jsonStr);
                    break;
            }
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        }
    }


}
