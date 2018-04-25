package com.khomandiak.courseWork;

class Solution {


    public static int removeDuplicates(int[] nums) {
        int repeats = 0;
        int index = 1;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (i > 0) {
                if (nums[i] < nums[i - 1]) {
                    break;
                } else if (nums[i] == nums[i - 1]) {
                    repeats++;
                } else {
                    nums[index] = nums[i];
                    index++;
                }
            }
        }
        return n - repeats;
    }

    public static int removeElement(int[] nums, int val) {
        int inst = 0;
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == val)
                inst++;
            else
                nums[index++] = nums[i];
        }
        return nums.length - inst;
    }
}