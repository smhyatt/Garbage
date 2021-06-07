package com.example.shopnshare.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.example.shopnshare.List;
import com.example.shopnshare.database.ItemsDbSchema.ItemTable;

public class ItemCursorWrapper extends CursorWrapper {
    public ItemCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public List getItem() {
        String what = getString(getColumnIndex(ItemTable.Cols.NAME));
        //String where = getString(getColumnIndex(ItemTable.Cols.WHERE));
        return new List(what);
        //return new List(what, where);
    }

}