package com.khomandiak.courseWork;

public class Position {

    private final int row;
    private final int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public double getDistance(Position other) {
        double rowDistance = this.row - other.row;
        double colDistance = this.col - other.col;
        return Math.sqrt(rowDistance * rowDistance + colDistance * colDistance);
    }

    public boolean equals(Position other) {
        return this.row == other.row && this.col == other.col;
    }

    @Override
    public String toString() {
        return "(" + row + "," + col + ")";
    }

}

