/*
 * Kattis: different
 * https://open.kattis.com/problems/different
 * Course: COMP30970, Warm-up Exercises
 * Topic: Basic I/O, long
 * Solution: your own (from kattis/ADifferentProblem.java)
 *
 * Question (summary in my own words; read the full statement on Kattis):
 * Each line has two (possibly huge) non-negative integers. Print the
 * absolute difference for each line, until end of input.
 */
import java.util.*;

public class ADifferentProblem{
    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String firstRow = scanner.nextLine();
        
            String[] row = firstRow.split(" ");
        
            long firstVal = Long.parseLong(row[0]);
            long secondVal = Long.parseLong(row[1]);
        
            System.out.println(Math.abs(firstVal - secondVal));
        }
    }
}
