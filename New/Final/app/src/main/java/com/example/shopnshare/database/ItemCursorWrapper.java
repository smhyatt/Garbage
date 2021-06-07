package com.example.shopnshare.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.example.shopnshare.List;
import com.example.shopnshare.database.ItemsDbSchema.ItemTable;

import java.util.ArrayList;

public class ItemCursorWrapper extends CursorWrapper {
    public ItemCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public List getItem() {
        String item = getString(getColumnIndex(ItemTable.Cols.ITEMS));
        //ArrayList<String> items = getColumnIndex(ItemTable.Cols.ITEMS);
        //String where = getString(getColumnIndex(ItemTable.Cols.WHERE));
        return new List(item);
        //return new List(what, where);
    }

}