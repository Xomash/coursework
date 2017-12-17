package com.khomandiak.courseWork;

public enum CellInfo {

    NONE(" "), VISITED(" "), PATH("*");

    private String stringRepresentation;

    private CellInfo(String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
    }

    @Override
    public String toString() {
        return this.stringRepresentation;
    }

}
