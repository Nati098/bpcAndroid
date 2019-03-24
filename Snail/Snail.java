package com.example.app1;

import java.util.ArrayList;
import java.util.LinkedList;

public class Snail {


    private static LinkedList<Integer> fillArr(int n){
        LinkedList<Integer> res = new LinkedList<>();
        for (int i=0; i < n; i++){
            res.add(i);
        }
        return res;
    }


    public static void main(String[] args) {
        // enter a length of input array into brackets
        LinkedList<Integer> in_arr = fillArr(15);

        // calculate dimention and start point
        int dim = (int) Math.ceil(Math.sqrt(in_arr.size()));
        int sp ;
        if (dim % 2 == 0){
            sp = (int) Math.floor(dim / 2) - 1;
        }
        else{
            sp = (int) Math.floor(dim / 2);
        }

        int i = sp;
        int j = sp;

        int[][] res_array = new int[dim][dim];

        try {
            // fill first ceil
            res_array[sp][sp] = in_arr.pop();

            // fill other
            for (int k = 1; k < dim; k++) {

                for (int xx = 2 * k - 1; xx > 0; xx--) {
                    j = k;
                    i = j - xx;
                    res_array[sp + i][sp + j] = in_arr.pop();
                }

                for (int xx = 0; xx < 2 * k; xx++) {
                    i = k;
                    j = i - xx;
                    res_array[sp + i][sp + j] = in_arr.pop();
                }

                for (int xx = 2 * k; xx > 0; xx--) {
                    j = -k;
                    i = j + xx;
                    res_array[sp + i][sp + j] = in_arr.pop();
                }

                for (int xx = 0; xx < 2 * k + 1; xx++) {
                    i = -k;
                    j = i + xx;
                    res_array[sp + i][sp + j] = in_arr.pop();

                }

            }
        } catch (java.util.NoSuchElementException e) {

        }
        finally {
            for (int x = 0; x < res_array.length; x++){
                for (int y=0; y < res_array.length; y++){
                    System.out.print(res_array[x][y] + "\t");
                }
                System.out.print("\n");
            }
        }
    }
}
