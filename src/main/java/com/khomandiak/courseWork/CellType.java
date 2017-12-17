package com.khomandiak.courseWork;

public enum CellType {

    FREE(" "), WALL("#"), START("S"), GOAL("G"), PATH("*");

    private String stringRepresentation;

    private CellType(String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
    }

    public static CellType fromChar(char c) {
        if (c == ' ') {
            return FREE;
        }
        else if (c == 'S') {
            return START;
        }
        else if (c == 'G') {
            return GOAL;
        }
        else if (c == '*'){
            return PATH;
        }
        else {
            return WALL;
        }
    }

    @Override
    public String toString() {
        return this.stringRepresentation;
    }

}
