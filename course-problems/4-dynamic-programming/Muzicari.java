/*
 * Kattis: muzicari
 * https://open.kattis.com/problems/muzicari
 * Course: COMP30970, Week 5: Dynamic Programming
 * Topic: Subset sum
 * Solution: your own (from kattis/muzicari.java)
 *
 * Question (summary in my own words; read the full statement on Kattis):
 * A concert lasts T minutes and each of N musicians needs one break of a
 * given length. Breaks are split into two groups that each fit in T
 * minutes. Print the start time of each musician's break.
 */
import java.util.*;

public class Muzicari {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        int N = sc.nextInt();

        int[] breaks = new int[N];

        for (int i = 0; i < N; i++) {
            breaks[i] = sc.nextInt();
        }

        // dp[t] = can we make a total break time of t?
        boolean[] dp = new boolean[T + 1];
        int[] parent = new int[T + 1];
        int[] prev = new int[T + 1];

        dp[0] = true;

        // Subset sum DP
        for (int i = 0; i < N; i++) {
            int w = breaks[i];

            for (int t = T; t >= w; t--) {
                if (!dp[t] && dp[t - w]) {
                    dp[t] = true;
                    parent[t] = i;
                    prev[t] = t - w;
                }
            }
        }

        // Find largest possible total for group 1
        int best = T;
        while (!dp[best]) {
            best--;
        }

        // Reconstruct group 1
        boolean[] group1 = new boolean[N];

        while (best > 0) {
            int i = parent[best];
            group1[i] = true;
            best = prev[best];
        }

        // Schedule both groups independently
        int time1 = 0;
        int time2 = 0;
        int[] answer = new int[N];

        for (int i = 0; i < N; i++) {
            if (group1[i]) {
                answer[i] = time1;
                time1 += breaks[i];
            } else {
                answer[i] = time2;
                time2 += breaks[i];
            }
        }

        for (int i = 0; i < N; i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(answer[i]);
        }
        System.out.println();

        sc.close();
    }
}
