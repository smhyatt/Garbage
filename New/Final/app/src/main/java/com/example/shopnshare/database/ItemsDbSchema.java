package com.example.shopnshare.database;

public class ItemsDbSchema {

    //public static final class ItemTable {
    public static class ItemTable {
        //private String name_table;
        public String TABLENAME;
        //public static final String TABLENAME = "Lists";

        /*public ItemTable(String table_name) {
            TABLENAME = table_name;
        }*/

        public void setTABLENAME(String table_name) {
            TABLENAME = table_name;
        }

        public static final class Cols {
            public static final String ITEMS = "List items";
            //public static final ArrayList<String> ITEMS = new ArrayList<>();
            //public static final String WHERE = "whereC";  //where is a keyword in SQL
        }
    }
}

