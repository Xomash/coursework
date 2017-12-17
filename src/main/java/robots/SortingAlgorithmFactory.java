package robots;

public class SortingAlgorithmFactory {

    public SortingAlgorithmFactory() {
    }

    public SortingAlgorithm getSortingAlgorithm() {
        return new MergeSort();
    }

}