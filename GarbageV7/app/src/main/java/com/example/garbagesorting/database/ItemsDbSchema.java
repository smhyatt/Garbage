package com.example.garbagesorting.database;

public class ItemsDbSchema {
    public static final boolean dbIsOnServer = true;

    public static final class ItemTable {
        public static final String NAME = "Items";

        public static final class Columns {
            public static final String WHAT  = "what";
            public static final String WHERE = "whereC";  //where is a keyword in SQL
        }
    }
}
