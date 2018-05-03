package com.khomandiak.courseWork;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.khomandiak.courseWork.App.printData;

/**
 * Created by o.khomandiak on 01.05.2018.
 */
public class Genetic {
    private static int[][] data = new int[][]{
            {-2,  1 , 2  , 3  , 4  , 5  , 6  , 7},
            { 1, -1 , 7  , 60 , 220, 55 , 117, 190},
            { 2, 22 , -1 , 40 , 80 , 100, 217, 5},
            { 3, 140, 150, -1 , 14 , 70 , 134, 200},
            { 4, 440, 120, 70 , -1 , 7  , 150, 217},
            { 5, 21 , 17 , 28 , 90 , -1 , 111, 79},
            { 6, 35 , 17 , 93 , 133, 2  , -1 , 200},
            { 7, 95 , 236, 119, 3  , 49 , 300, -1}};
    private static final int BOUND = 3;
    private static final int SELECTION_SIZE = 3;
    private static final double CHILD_PERCENT = 0.4;
    static int answer = 0;

    public static void main(String[] args) {
        //printData(data);
        List<List<Integer>> selection = getSelection();
        //System.out.println("------------------------------------------------------------------------------");
        //System.out.println(selection);
        //selection.forEach(o -> System.out.print(getSum(o) + ", "));
        //System.out.println("------------------------------------------------------------------------------");
        selection.sort(Genetic::compare);
        //System.out.println(selection);
        //selection.forEach(o -> System.out.print(getSum(o) + ", "));
        System.out.println("------------------------------------------------------------------------------");
        for (int i = 0; i < 30; i++) {
            int n = selection.size();
            crossing(selection, n);
            //n = selection.size();
            selection = selection.stream().distinct().collect(Collectors.toList());
            //int percentOfSurvivors = ((n) * 2) / 3;
            //selection = selection.subList(0, selection.size() < percentOfSurvivors ? selection.size() : percentOfSurvivors);
            //System.out.println(selection);
            //selection.forEach(o -> System.out.print(getSum(o) + ", "));
            //System.out.println("------------------------------------------------------------------------------");
        }
        System.out.println(selection);
        selection.forEach(o -> System.out.print(getSum(o) + ", "));
        System.out.println("------------------------------------------------------------------------------");
        answer = getSum(selection.get(0));
    }

    private static void crossing(List<List<Integer>> selection, int n) {
        for (int i = 0; i < n * CHILD_PERCENT; i++) {
            List<Integer> ancestor1 = selection.get(i++);
            List<Integer> ancestor2 = selection.get(i);
            List<Integer> middle1 = ancestor1.subList(BOUND, ancestor1.size() - BOUND);
            List<Integer> middle2 = ancestor2.subList(BOUND, ancestor2.size() - BOUND);
            List<Integer> child1 = new ArrayList<>();
            List<Integer> child2 = new ArrayList<>();
            child1.addAll(middle2);
            child2.addAll(middle1);
            for (int j = 0; j < BOUND; j++) {
                addStableDNA(ancestor1, ancestor2, child1, j);
                addStableDNA(ancestor2, ancestor1, child2, j);
            }
            for (int j = ancestor1.size() - BOUND; j < ancestor1.size(); j++) {
                addStableDNA(ancestor1, ancestor2, child1, j);
                addStableDNA(ancestor2, ancestor1, child2, j);
            }
            if (selection.contains(child1) || child1.equals(child2)) {
                mutation(child1);
            }
            if (selection.contains(child2) || child2.equals(child1)) {
                mutation(child2);
            }
            selection.add(selection.size()-2, child1);
            if (i <= n * CHILD_PERCENT)
                selection.add(selection.size()-1, child2);
        }
        selection.sort(Genetic::compare);
        //System.out.println(selection);
    }

    private static void mutation(List<Integer> child) {
        Random r = new Random();
        int a1 = r.nextInt(child.size() - 2) + 1;
        int a2 = r.nextInt(child.size() - 2) + 1;
        if (a1 != a2) {
            int temp = child.get(a1);
            child.set(a1, child.get(a2));
            child.set(a2, temp);
        } else {
            mutation(child);
        }
    }

    private static void addStableDNA(List<Integer> directAncestor, List<Integer> pairAncestor, List<Integer> child, int j) {
        if (!child.contains(directAncestor.get(j)) || directAncestor.get(j) == 1) {
            child.add(j, directAncestor.get(j));
        } else {
            if (!child.contains(pairAncestor.get(j)))
                child.add(j, pairAncestor.get(j));
            else {
                List<Integer> notCounted = findNotCounted(pairAncestor, child);
                child.add(j, notCounted.get(0));
            }
        }
    }

    private static List<Integer> findNotCounted(List<Integer> ancestor, List<Integer> child) {
        List<Integer> notCounted = new ArrayList<>();
        notCounted.addAll(ancestor);
        notCounted.removeAll(child);
        return notCounted;
    }

    private static int compare(List<Integer> o1, List<Integer> o2) {
        int sum1 = getSum(o1);
        int sum2 = getSum(o2);
        return Integer.compare(sum1, sum2);
    }

    private static int getSum(List<Integer> o) {
        int sum = 0;
        for (int i = 0; i < o.size() - 1; i++) {
            sum += data[o.get(i)][o.get(i + 1)];
        }
        return sum;
    }

    private static List<List<Integer>> getSelection() {
        List<List<Integer>> selection = new ArrayList<>();
        int n = data.length - 1;
        for (int i = 0; i < SELECTION_SIZE; i++) {
            Random r = new Random();
            List<Integer> oneWay = new ArrayList<>();
            oneWay.add(1);
            for (int j = 1; j < n; j++) {
                int rand = 1;
                while (oneWay.contains(rand)) {
                    rand = r.nextInt(n - 1) + 2;
                }
                oneWay.add(rand);
            }
            oneWay.add(1);
            selection.add(oneWay);
        }
        return selection;
    }
}
