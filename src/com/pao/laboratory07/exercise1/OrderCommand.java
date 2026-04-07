package com.pao.laboratory07.exercise1;

public enum OrderCommand {
    NEXT("NEXT"),
    CANCEL("CANCEL"),
    UNDO( "UNDO"),
    QUIT( "QUIT");

    private OrderCommand(String name) {}
}
