package com.khomandiak.courseWork;

/**
 * Created by o.khomandiak on 03.05.2017.
 */
public class MyFindArray implements FindArray {
    public int findArray(int[] array, int[] subArray) {

        int lastIndex = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == subArray[0]) {
                for (int k = 0; k < subArray.length; k++) {
                    if ((i+k)<array.length&&array[i + k] == subArray[k]) {
                        if (k == subArray.length - 1) {
                            lastIndex = i;
                        }
                    } else {
                        break;
                    }
                }
            }
        }

        return lastIndex;
    }

    public static void main(String[] args) {
        int[] array = {4,9,3,7,8};
        int[] subArray = {3,7};
        FindArray findArray = new MyFindArray();
        System.out.println(findArray.findArray(array,subArray));
    }
}
