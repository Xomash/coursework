package com.khomandiak.courseWork;

import java.util.*;

public class MazeSolver {

    private static final int MOVE_COST = 1;
    static boolean exit = false;
    static boolean[][] checked ;

    private MazeSolver() {
    }

    private static Maze drawPath(final Maze maze, List<Position> positions) {

        int cols = maze.toString().indexOf("\n") + 1;
        int rows = maze.toString().length() / cols;
        StringBuilder mazeBuilder = new StringBuilder();
        char[][] row = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                row[i][j] = maze.toString().toCharArray()[i * cols + j];
            }
        }
        for (Position position : positions)
           row[position.getRow()][position.getCol()] = '*';
        for (int k = 0; k < rows; k++)
            mazeBuilder.append(row[k]);

        return Maze.create(mazeBuilder.toString());
    }

    public static Maze solve(final Maze maze) {
        if (maze.getStart() == maze.getGoal())
            return maze;
        Position start = maze.getStart();
        Position end = maze.getGoal();
        int cols = maze.toString().indexOf("\n") + 1;
        int rows = maze.toString().length() / cols;
        checked = new boolean[rows][cols-2];
        List<Position> positions = new ArrayList<>();
        next(start, end, maze, positions);
        return drawPath(maze, positions);
    }

    public static void next(Position current, Position goal, Maze maze, List<Position> chain) {
        if (current.equals(goal)) {
            exit = true;
            return;
        }
        checked[current.getRow()][current.getCol()] = true;
        List<Boolean> can = new ArrayList<>();
        List<Position> positions = maze.getMoves(current);
        for (Position position : positions)
            can.add(checked[position.getRow()][position.getCol()]);
        if (!can.contains(false))
            return;

        for (Position position : positions) {
            if (exit) {
                chain.add(current);
                return;
            }
            if (!checked[position.getRow()][position.getCol()])
                next(position, goal, maze, chain);
        }
        if (exit && !current.equals(maze.getStart()))
            chain.add(current);
        return;
    }

}

