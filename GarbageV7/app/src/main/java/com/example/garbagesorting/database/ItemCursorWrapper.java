package com.example.garbagesorting.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.garbagesorting.Item;
import com.example.garbagesorting.database.ItemsDbSchema.ItemTable;

public class ItemCursorWrapper extends CursorWrapper {
    public ItemCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Item getItem() {
        // Extract name and placement for item from db using Cursor,
        // and cast it to string, as to make an Item object.
        String what  = getString(getColumnIndex(ItemTable.Columns.WHAT));
        String where = getString(getColumnIndex(ItemTable.Columns.WHERE));
        return new Item(what, where);
    }
}
