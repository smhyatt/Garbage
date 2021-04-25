package com.example.garbagesorting.network;

import com.example.garbagesorting.database.QueryBuilder;

public class httpThread implements Runnable {
    String url;
    QueryBuilder.QueryType queryType;

    public httpThread(String url, QueryBuilder.QueryType queryType) {
        this.url = url;
        this.queryType = queryType;
    }

    /* Only method that is run, when a Runnable object is given to a Thread.
     * Runs in backGround */
    public void run() {
        new NetworkFetcher().connect(url, queryType);
    }
}