package robots;

public class MergeSort implements SortingAlgorithm {

    @Override
    public Robot[] sortRobots(Robot myPosition, Robot[] input) {
        if(input == null || input.length<1 || myPosition == null)
            return input;
        int size = input.length;
        Robot[] output = input;
        double [] distances = new double[size];

        for(int i = 0; i<size; i++)
            distances[i] = myPosition.getDistance(output[i]);

        sort(output, myPosition);
        return output;
    }

    public void sort(Robot [] array, Robot main) {
        if (array.length <= 1)
            return;
        boolean odd = array.length%2!=0;

        int sizeFirst = odd ? array.length/2 +1 : array.length/2;


        Robot [] first = new Robot [sizeFirst];
        Robot [] second = new Robot [array.length/2];

        for (int i = 0; i < array.length; i++)
            if (i < sizeFirst)
                first[i] = array[i];
            else
                second[i - sizeFirst] = array[i];

        sort(first,main);
        sort(second, main);

        merge(first, second, array, main);

    }

    private void merge(Robot [] first, Robot [] second, Robot [] array, Robot main) {

        int firstCount = 0;
        int secondCount = 0;

        for (int i = 0; i < array.length; i++) {
            if (firstCount == first.length) {
                array[i] = second[secondCount];
                ++secondCount;
                continue;
            } else if (secondCount == second.length) {
                array[i] = first[firstCount];
                ++firstCount;
                continue;
            }

            if (first[firstCount].getDistance(main)<second[secondCount].getDistance(main)) {
                array[i] = first[firstCount];
                ++firstCount;
            } else {
                array[i] = second[secondCount];
                ++secondCount;
            }
        }

    }

}
