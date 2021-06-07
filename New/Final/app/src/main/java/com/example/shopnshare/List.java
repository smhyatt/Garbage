package com.example.shopnshare;


public class List {
    private String mItem = null;
    //private String mTable = ItemsDbSchema.ItemTable.TABLENAME;
    //private ArrayList<String> mList = new ArrayList<>();

    public List(String item) {
        //public List(String name) {
        mItem = item;
        //mList = list;
    }

    //@Override
    /*public String toString() {
        return oneLine("", " should be placed in: ");
    }*/

    public String getItem() {
        return mItem;
    }

/*    public String getTable() {
        return mItem;
    }

    public void setTable(String table) {
        mItem = table;
    }*/

    public void setItem(String item) {
        mItem = item;
    }


    /*public String oneLine(String pre, String post) {
        return pre + mWhat + post + mWhere;
    }*/
}