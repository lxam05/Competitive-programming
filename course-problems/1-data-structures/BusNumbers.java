/*
 * Kattis: busnumbers
 * https://open.kattis.com/problems/busnumbers
 * Course: COMP30970, Week 1: Data Structures
 * Topic: Sorting
 * Solution: your own (from kattis/BusNumbers.java)
 *
 * Question (summary in my own words; read the full statement on Kattis):
 * Print a list of bus numbers in increasing order, writing every run of 3
 * or more consecutive numbers as "first-last".
 */
import java.util.*;

public class BusNumbers{
    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);

        String lenS = scanner.nextLine();
        String line = scanner.nextLine();
        int len = Integer.parseInt(lenS);

        String[] row = line.split(" ");

        int[] nums = new int[row.length];

        for(int i = 0; i < len; i++){
            nums[i] = Integer.parseInt(row[i]);
        }

        Arrays.sort(nums);
        int end = 0;


        for(int i = 0; i < nums.length - 1; i++){


            while(nums[end] == nums[end + 1] - 1){
                end++;
            }

            if(end - i >= 2){
                System.out.print(nums[i] + "-" + nums[end] + " ");
            }else{
                System.out.print(nums[i] + " ");
                if(i == nums.length - 2) System.out.println(nums[i + 1] + " ");
                end = 0;
            }
            i += end;
        }
        
    }
}