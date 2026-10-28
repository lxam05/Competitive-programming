/*
 * Kattis: bitmask
 * https://open.kattis.com/problems/bitmask
 * Course: COMP30970, Week 4: Greedy Algorithms
 * Topic: Brute force over split masks
 * Solution: your own (from kattis/Bitmask.java)
 *
 * Question (summary in my own words; read the full statement on Kattis):
 * (Summary based on your solution.) A string of digits of length n and a
 * cost for each chunk length. Split the string into chunks; each distinct
 * chunk is only paid for once. Find the minimum total cost.
 * Input: the string, then n costs (cost of a chunk of length 1..n).
 */
import java.util.*;

public class Bitmask {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String num = sc.nextLine();

        int n = num.length();

        int[] costs = new int[n];

        for (int i = 0; i < n; i++) {
            costs[i] = sc.nextInt();
        }

        int minCost = Integer.MAX_VALUE;

        // There are n - 1 possible places to split.
        // Therefore there are 2^(n - 1) possible split patterns.
        int totalPartitions = 1 << (n - 1);

        for (int mask = 0; mask < totalPartitions; mask++) {

            HashSet<String> seen = new HashSet<>();

            int currentCost = 0;
            int start = 0;

            // Go through every possible split position
            for (int j = 0; j < n - 1; j++) {

                // If bit j is 1, split after position j
                if ((mask & (1 << j)) != 0) {

                    String chunk = num.substring(start, j + 1);

                    // Only pay if we haven't used this chunk before
                    if (!seen.contains(chunk)) {

                        currentCost += costs[chunk.length() - 1];

                        seen.add(chunk);
                    }

                    // Next chunk starts after j
                    start = j + 1;
                }
            }

            // Get the final chunk after the final split
            String chunk = num.substring(start);

            if (!seen.contains(chunk)) {

                currentCost += costs[chunk.length() - 1];

                seen.add(chunk);
            }

            minCost = Math.min(minCost, currentCost);
        }

        System.out.println(minCost);
    }
}