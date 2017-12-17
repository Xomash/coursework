package com.khomandiak.courseWork;

public class MazeCell {

    private final CellType cellType;
    private CellInfo cellInfo;
    private double pathCost = Double.MAX_VALUE;
    private double heuristicCost = Double.MAX_VALUE;

    private MazeCell(CellType cellType, CellInfo cellInfo) {
        this.cellType = cellType;
        this.cellInfo = cellInfo;
    }

    protected static MazeCell create(CellType cellType) {
        return new MazeCell(cellType, CellInfo.NONE);
    }

    protected static MazeCell create(char c) {
        return new MazeCell(CellType.fromChar(c), CellInfo.NONE);
    }

    public void setCellInfo(CellInfo cellInfo) {
        this.cellInfo = cellInfo;
    }

    public CellType getCellType() {
        return this.cellType;
    }

    public CellInfo getCellInfo() {
        return this.cellInfo;
    }

    public double getPathCost() {
        return pathCost;
    }

    public void setPathCost(double pathCost) {
        this.pathCost = pathCost;
    }

    public double getHeuristicCost() {
        return heuristicCost;
    }

    public void setHeuristicCost(double heuristicCost) {
        this.heuristicCost = heuristicCost;
    }

    @Override
    public String toString() {
        if (this.cellType == CellType.FREE) {
            return this.cellInfo.toString();
        }
        return this.cellType.toString();
    }

}

