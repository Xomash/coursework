package com.khomandiak.courseWork;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;
import static java.lang.Math.min;

public class App {
    private static int[][] data = new int[][]{
            {-2, 1, 2, 3, 4, 5, -3},
            {1, -1, 7, 60, 220, 55, 0},
            {2, 22, -1, 40, 80, 100, 0},
            {3, 140, 150, -1, 14, 70, 0},
            {4, 440, 120, 70, -1, 7, 0},
            {5, 21, 17, 28, 90, -1, 0},
            {-4, 0, 0, 0, 0, 0, -1}};

    private static int iMax = 0;
    private static int jMax = 0;

    private static List<Integer> fineA = new ArrayList<Integer>();
    private static List<Integer> fineB = new ArrayList<Integer>();
    private static Map<Integer, Integer> path = new TreeMap<Integer, Integer>();

    public static void main(String[] args) {
        int n = data.length - 1;
        System.out.println("Дано: ");
        printData(data);
        System.out.println("Етап 1:");
        for(int i = 0; i < n-1; i++) {
            computeA(n);
            computeB(n);
            computeFine(n);
            findMaxFine(n);
            path.put(iMax, jMax);
            deleteObsolete(n);
            printData(data);
            System.out.println(path);
        }
    }

    private static void deleteObsolete(int n) {
        for (int j = 1; j <= n; j++) {
            data[iMax][j] = -1;
            data[j][jMax] = -1;
        }
    }

    private static void findMaxFine(int n) {
        int fineMax = MIN_VALUE;
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n; j++) {
                if (data[i][j] == 0) {
                    int fine = fineA.get(i - 1) + fineB.get(j - 1);
                    System.out.printf("(%d, %d) = %d\n", i, j, fine);
                    if(fine > fineMax && (!isCycle(i, j) || path.size() == n-2)) {
                        iMax = i;
                        jMax = j;
                        fineMax =  fine;
                    }
                }
            }
        }
        if(fineMax == MIN_VALUE){
            for (int i = 1; i < n; i++) {
                for(int j = 1; j < n; j++){
                    if (data[i][j] == 0) {
                        data[i][j] = -1;
                    }
                }
            }

            computeFine(n);
            for (int i = 1; i < n; i++) {
                for(int j = 1; j < n; j++){
                    if (data[i][j] > 0) {
                        data[i][j] = 0;
                    }
                }
            }
            findMaxFine(n);
        }
        System.out.println(fineMax);
    }

    private static void computeFine(int n) {
        fineA.clear();
        fineB.clear();
        for (int i = 1; i < n; i++) {
            fineA.add(getMin(n, data[i], true));
            int[] col = getCol(n, i);
            fineB.add(getMin(n, col, true));
        }
        System.out.println(fineA);
        System.out.println(fineB);
    }

    private static void computeB(int n) {
        for (int i = 1; i < n; i++) {
            int[] col = getCol(n, i);
            data[n][i] = getMin(n, col, false);
        }
        //printData(data);
        for (int i = 1; i < n; i++) {
            int b = data[n][i];
            for (int j = 1; j < n; j++) {
                data[j][i] = data[j][i] > 0 ? (data[j][i] - b) : data[j][i];
            }
        }
        printData(data);
    }

    private static int[] getCol(int n, int i) {
        int[] col = new int[n + 1];
        for (int j = 0; j <= n; j++) {
            col[j] = data[j][i];
        }
        return col;
    }

    private static void computeA(int n) {
        for (int i = 1; i < n; i++) {
            data[i][n] = getMin(n, data[i], false);
        }
        //printData(data);
        for (int i = 1; i < n; i++) {
            int a = data[i][n];
            for (int j = 1; j < n; j++) {
                data[i][j] = data[i][j] > 0 ? (data[i][j] - a) : data[i][j];
            }
        }
        printData(data);
    }

    private static int getMin(int n, int[] datum, boolean fine) {
        int min = MAX_VALUE;
        boolean zero = false;
        for (int j = 1; j < n; j++) {
            int next = datum[j];
            if (((next > 0) && !zero) || ((next >= 0) && zero || !fine))
                min = next >= 0 ? min(min, next) : min;
            else if (next == 0)
                zero = true;
        }
        return min == MAX_VALUE ? -1 : min;
    }

    private static void printData(int[][] data) {
        StringBuilder sb = new StringBuilder();
        sb.append(" |————————————————————————|\n");
        for (int[] row : data) {
            for (int aData : row) {
                sb.append(" | ");
                if (aData == -5)
                    sb.append("Штр");
                else if (aData == -4)
                    sb.append(" B ");
                else if (aData == -3)
                    sb.append(" A ");
                else if (aData == -2)
                    sb.append(" n ");
                else if (aData == -1)
                    sb.append("---");
                else if (aData < 10)
                    sb.append(" ").append(aData).append(" ");
                else if (aData < 100)
                    sb.append(aData).append(" ");
                else
                    sb.append(aData);
            }
            sb.append(" |\n |————————————————————————|\n");
        }
        sb.append("\n");
        System.out.println(sb.toString());
    }

    private static boolean isCycle(int key, int value){
        int pathK = value;
        while(key!=pathK){
            if(path.get(pathK) == null)
                return false;
            pathK = path.get(pathK);
        }
        return true;
    }
}