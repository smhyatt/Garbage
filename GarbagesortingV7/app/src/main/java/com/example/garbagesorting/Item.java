package com.example.garbagesorting;

public class Item {
    private String name;
    private String where;

    public Item(String name, String where) {
        this.name = name;
        this.where = where;
    }

    @Override
    public String toString() {
        return oneLine(" in: ");
    }

    public String oneLine(String between) {
        return name + between + where;
    }

    // Getters and Setters
    public String getWhat() { return name; }
    public void setWhat(String what) { name = what; }

    public String getWhere() { return where; }
    public void setWhere(String where) { this.where = where; }


}
