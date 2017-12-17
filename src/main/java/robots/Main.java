package robots;

import java.util.Arrays;

public class Main {

    private static Robot r(double x, double y) {
        return new Robot(x, y);
    }

    public static void main(String[] args) {
        SortingAlgorithm sortingAlgorithm = new SortingAlgorithmFactory().getSortingAlgorithm();

        Robot[] input = new Robot[]{r(3,4), r(156,33), r(3,3), r(-23,12), r(3,3)};
        Robot[] sorted = sortingAlgorithm.sortRobots(r(0,0), input);
        System.out.println(Arrays.toString(sorted));

        // Expected: [R(3.00,3.00), R(3.00,3.00), R(3.00,4.00), R(-23.00,12.00), R(156.00,33.00)]
    }

}