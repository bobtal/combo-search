package com.bobantalevski.combosearch.helpers;

// Java program to loop over all combinations of size r in an array of size n
import java.util.*;

public class Permutation {

    /* arr[]  ---> Input Array
    data[] ---> Temporary array to store current combination
    start & end ---> Staring and Ending indexes in arr[]
    index  ---> Current index in data[]
    comboSize ---> Size of a combination to be printed */
    private static void combinationUtil(String[] arr, String[] data, int start,
                                        int end, int index, int comboSize, List<String> searchCombos)
    {
        // Build a searchCombo from scratch by just adding the required number
        // of search terms in a single space separated String
        if (index == comboSize)
        {
            StringBuilder searchCombo = new StringBuilder();
            for (int j=0; j<comboSize; j++) {
                searchCombo.append(" ").append(data[j]);
            }
            // After the searchCombo is constructed, add it (as a single String) to the searchCombos collection
            searchCombos.add(searchCombo.toString().trim());
            return;
        }

        // replace index with all possible elements. The condition
        // "end-i+1 >= comboSize-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        for (int i=start; i<=end && end-i+1 >= comboSize-index; i++)
        {
            data[index] = arr[i];
            combinationUtil(arr, data, i+1, end, index+1, comboSize, searchCombos);
        }
    }

    // The main function that gets all combinations of size comboSize
    // in arr[] of size n and stores them as a single String in searchCombos.
    // This function mainly uses combinationUtil()
    public static void getCombination(String[] arr, int n, int comboSize, List<String> searchCombos)
    {
        // A temporary array to store all combination one by one
        String data[] = new String[comboSize];

        // Print all combination using temporary array 'data[]'
        combinationUtil(arr, data, 0, n-1, 0, comboSize, searchCombos);
    }

    /* original code taken from geeksforgeeks.org, contributed by Devesh Agrawal */
    /* http://www.geeksforgeeks.org/print-all-possible-combinations-of-r-elements-in-a-given-array-of-size-n/ */
    /* done some modifications for this particular use case */
}