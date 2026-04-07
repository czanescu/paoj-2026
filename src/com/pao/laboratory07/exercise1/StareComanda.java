package com.pao.laboratory07.exercise1;

public enum StareComanda {
    PLACED("PLACED"),
    PROCESSED("PROCESSED"),
    SHIPPED("SHIPPED"),
    DELIVERED("DELIVERED"),
    CANCELLED("CANCELLED");

    private StareComanda (String name) {}

    public StareComanda next() {
        StareComanda[] all = values();
        int i = this.ordinal();
        if (i >= all.length - 1) {
            return this;
        }
        return all[i + 1];
    }

    public String getName() {return this.name();}
}
