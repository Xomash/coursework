package com.khomandiak.courseWork;


import java.util.Arrays;

public class App {
    private static int[][] data = new int[][]{
            {-2, 1, 2, 3, 4, 5, -3},
            {1, -1, 7, 60, 220, 55, 0},
            {2, 22, -1, 40, 80, 100, 0},
            {3, 140, 150, -1, 14, 70, 0},
            {4, 440, 120, 70, -1, 7, 0},
            {5, 21, 17, 28, 90, -1, 0},
            {-4, 0, 0, 0, 0, 0, -1}};

    public static void main(String[] args) {
        int n = data.length-1;
        System.out.println("Дано: ");
        printData(data);
        System.out.println("Етап 1:");
        for(int i = 1; i < n; i++){
            data[i][n] = getMin(n, data[i]);
        }
        printData(data);
        for(int i = 1; i < n; i++){
            int a = data[i][n];
            for(int j = 1; j<n;j++) {
                data[i][j] = data[i][j] > 0 ? (data[i][j] - a) : data[i][j];
            }
        }
        printData(data);
        for(int i = 1; i < n; i++){
            int[] col = new int[n+1];
            for(int j = 0; j <= n; j++){
                col[j] = data[j][i];
            }
            data[n][i] = getMin(n, col);
        }
        printData(data);
        for(int i = 1; i < n; i++){
            int b = data[n][i];
            for(int j = 1; j<n;j++) {
                data[j][i] = data[j][i] > 0 ? (data[j][i] - b) : data[j][i];
            }
        }
        printData(data);
    }

    private static int getMin(int n, int[] datum) {
        int min = Integer.MAX_VALUE;
        for(int j = 1; j<n; j++) {
            int next = datum[j];
            min = next >= 0 ? Math.min(min, next) : min;
        }
        return min;
    }

    private static void printData(int[][] data) {
        System.out.println(" |————————————————————————|");
        for (int[] row : data) {
            for (int aData : row) {
                System.out.print(" | ");
                if (aData == -4)
                    System.out.print(" B ");
                else if (aData == -3)
                    System.out.print(" A ");
                else if (aData == -2)
                    System.out.print(" n ");
                else if (aData == -1)
                    System.out.print("---");
                else if (aData < 10)
                    System.out.print(" " + aData + " ");
                else if (aData < 100)
                    System.out.print(aData + " ");
                else
                    System.out.print(aData);
            }
            System.out.println(" |\n |————————————————————————|");
        }
        System.out.println("\n");
    }
}