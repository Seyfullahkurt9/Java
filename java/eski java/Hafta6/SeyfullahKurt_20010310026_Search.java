package Hafta6;

import java.util.*;

public class SeyfullahKurt_20010310026_Search {
    public static void main(String[] args) {

        useBinarySearch1();
        useBinarySearch2();
        int[] numbers = {11,18,29,37,42,49,51,63,69,72,77,82,88,91,98};
        System.out.println(binarySearch(numbers,45));
    }
    
    public static void useBinarySearch1() {

        // binary search on an array
        int[] numbers = {-3, 2, 8, 12, 17, 29, 44, 58, 79 };
        int index = Arrays.binarySearch(numbers, 29);
        System.out.println(index);
    }
    
    public static void useBinarySearch2() {

        int[] numbers = {-3, 2, 8 ,12 ,17 ,29 ,44 ,58 ,79 };
        ArrayList<Integer> list = new ArrayList<Integer>();
        
        for (int i : numbers) {

            list.add(i);
        }

        int index = Collections.binarySearch(list,29);
        System.out.println(index);
    }

    public static int binarySearch(int[] numbers,int target){
        int min=0;
        int max=numbers.length -1;

        while(min<=max){
            int mid=(max+min)/2;
            if(numbers[mid]==target)
                return mid;
            else if (numbers[mid]<target)
                min=mid+1;
            else //if(numbers[mid]>target)
                max=mid -1;
        }
        return -1;
    }
}
