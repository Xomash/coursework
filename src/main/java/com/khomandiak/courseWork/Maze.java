package com.khomandiak.courseWork;

import java.util.ArrayList;
import java.util.List;

public class Maze {

    private static final String NL = System.getProperty("line.separator");

    private ArrayList<ArrayList<MazeCell>> maze = new ArrayList<>();

    private Maze(String mazeStr) {

        if (mazeStr.isEmpty()) {
            return;
        }

        String[] rows = mazeStr.split(NL);
        int rowLength = rows[0].length();

        for (String row : rows) {
            if (rowLength != row.length()) {
                throw new RuntimeException("Row length is " + row.length() + ", but " + rowLength + " was expected in row [" + row + "].");
            }

            ArrayList<MazeCell> mazeRow = new ArrayList<>();
            for (char col: row.toCharArray()) {
                mazeRow.add(MazeCell.create(col));
            }

            maze.add(mazeRow);
        }
    }



    public static Maze create(String mazeStr) {
        return new Maze(mazeStr);
    }

    public Position getStart() {
        return findFirstOf(CellType.START);
    }

    public Position getGoal() {
        return findFirstOf(CellType.GOAL);
    }

    public MazeCell getCell(Position cellPosition) {
        return maze.get(cellPosition.getRow()).get(cellPosition.getCol());
    }

    private boolean isWithinBounds(Position position) {
        if (position.getRow() < 0) {
            return false;
        }
        if (position.getRow() >= maze.size()) {
            return false;
        }
        if (position.getCol() < 0) {
            return false;
        }
        if (position.getCol() >= maze.get(0).size()) {
            return false;
        }
        return true;
    }

    private boolean canBePath(Position position) {
        // check whether we are out of bounds
        if (!isWithinBounds(position)) {
            return false;
        }

        // check the cell contents
        MazeCell cell = getCell(position);
        if (cell.getCellInfo() != CellInfo.VISITED) {
            return false;
        }
        if (cell.getCellType() == CellType.WALL) {
            return false;
        }

        return true;
    }

    private boolean canBeVisited(Position position) {

        // check whether we are out of bounds
        if (!isWithinBounds(position)) {
            return false;
        }

        // check the cell contents
        MazeCell cell = getCell(position);
        if (cell.getCellInfo() != CellInfo.NONE) {
            return false;
        }
        if (cell.getCellType() == CellType.WALL) {
            return false;
        }

        return true;
    }

    public List<Position> getMoves(Position start) {
        List<Position> newMoves = new ArrayList<>();

        // up
        Position upMove = new Position(start.getRow()-1, start.getCol());
        if (canBeVisited(upMove)) {
            newMoves.add(upMove);
        }

        // down
        Position downMove = new Position(start.getRow()+1, start.getCol());
        if (canBeVisited(downMove)) {
            newMoves.add(downMove);
        }

        // left
        Position leftMove = new Position(start.getRow(), start.getCol()-1);
        if (canBeVisited(leftMove)) {
            newMoves.add(leftMove);
        }

        // right
        Position rightMove = new Position(start.getRow(), start.getCol()+1);
        if (canBeVisited(rightMove)) {
            newMoves.add(rightMove);
        }

        return newMoves;
    }

    public List<Position> getPathCandidates(Position position) {
        List<Position> pathCandidates = new ArrayList<>();

        // up
        Position upMove = new Position(position.getRow()-1, position.getCol());
        if (canBePath(upMove)) {
            pathCandidates.add(upMove);
        }

        // down
        Position downMove = new Position(position.getRow()+1, position.getCol());
        if (canBePath(downMove)) {
            pathCandidates.add(downMove);
        }

        // left
        Position leftMove = new Position(position.getRow(), position.getCol()-1);
        if (canBePath(leftMove)) {
            pathCandidates.add(leftMove);
        }

        // right
        Position rightMove = new Position(position.getRow(), position.getCol()+1);
        if (canBePath(rightMove)) {
            pathCandidates.add(rightMove);
        }

        return pathCandidates;
    }

    private Position findFirstOf(CellType cellType) {
        for (int rowIndex = 0; rowIndex < this.maze.size(); ++rowIndex) {
            ArrayList<MazeCell> row = this.maze.get(rowIndex);
            for (int colIndex = 0; colIndex < row.size(); ++colIndex) {
                MazeCell mazeCell = row.get(colIndex);
                if (mazeCell.getCellType().equals(cellType)) {
                    return new Position(rowIndex, colIndex);
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (ArrayList<MazeCell> row : this.maze) {
            for (MazeCell cell : row) {
                stringBuilder.append(cell);
            }
            stringBuilder.append(NL);
        }
        return stringBuilder.toString();
    }

}
