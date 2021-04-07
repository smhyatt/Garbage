package com.example.garbagesorting.database;

/* This class is dependent on the db being on a server:
 *   - builds the queries as urls */
public class QueryBuilder implements queries {
    private String url;

    public QueryBuilder(String url) {
        this.url = url;
    }

    public enum QueryType {
        INSERT,
        DELETE,
        SELECTALL
    }

    public String INSERT(String what, String where) {
        return url + "/?op=insert&what=" + what + "&whereC=" + where;
    }

    @Override
    public String DELETE(String what) {
        return url + "?op=remove&what=" + what;
    }

    @Override
    public String SELECTALL() {
        return url;
    }
}
