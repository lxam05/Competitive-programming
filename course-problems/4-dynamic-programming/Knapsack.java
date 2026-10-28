/*
 * Kattis: knapsack
 * https://open.kattis.com/problems/knapsack
 * Course: COMP30970, Week 5: Dynamic Programming
 * Topic: 0/1 knapsack + reconstruction
 * Solution: your own (from kattis/Knapsack.java)
 *
 * Question (summary in my own words; read the full statement on Kattis):
 * Capacity C and n items with value and weight. Choose items with the most
 * total value that fit. Print how many items were chosen and their indices.
 * Input: several cases until end of file.
 */
import java.io.*;
import java.util.*;

public class Knapsack {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(
            new InputStreamReader(System.in)
        );

        StringBuilder output = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;

            StringTokenizer st = new StringTokenizer(line);
            int capacity = Integer.parseInt(st.nextToken());
            int n = Integer.parseInt(st.nextToken());

            int[] weights = new int[n];
            int[] dp = new int[capacity + 1];
            boolean[][] taken = new boolean[n][capacity + 1];

            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine());

                int value = Integer.parseInt(st.nextToken());
                int weight = Integer.parseInt(st.nextToken());

                weights[i] = weight;

                for (int w = capacity; w >= weight; w--) {
                    int newValue = dp[w - weight] + value;

                    if (newValue > dp[w]) {
                        dp[w] = newValue;
                        taken[i][w] = true;
                    }
                }
            }

            ArrayList<Integer> chosen = new ArrayList<>();
            int w = capacity;

            for (int i = n - 1; i >= 0; i--) {
                if (taken[i][w]) {
                    chosen.add(i);
                    w -= weights[i];
                }
            }

            output.append(chosen.size()).append('\n');

            for (int i = 0; i < chosen.size(); i++) {
                if (i > 0) output.append(" ");
                output.append(chosen.get(i));
            }

            output.append('\n');
        }

        System.out.print(output);
    }
}
