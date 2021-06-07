package com.example.shopnshare;

import java.util.ArrayList;

public class List {
    private String mName = null;
    private ArrayList<String> mList = new ArrayList<>();;

    //public List(String name, ArrayList<String> list) {
    public List(String name) {
        mName = name;
        //mList = list;
    }

    //@Override
    /*public String toString() {
        return oneLine("", " should be placed in: ");
    }*/

    public String getName() {
        return mName;
    }

    public void setWhat(String name) {
        mName = name;
    }

    public ArrayList<String> getList() {
        return mList;
    }

    public void setList(ArrayList<String> list) {
        mList = list;
    }

    /*public String oneLine(String pre, String post) {
        return pre + mWhat + post + mWhere;
    }*/
}