package com.khomandiak.courseWork;

public class Main {

    public static void main(String[] args) {
        Maze maze = Maze.create(MazeStringGenerator.getMazeString1());
        Maze solution = MazeSolver.solve(maze);
        System.out.println(solution);
    }

}