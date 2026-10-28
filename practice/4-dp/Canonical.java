/*
 * Kattis: canonical
 * https://open.kattis.com/problems/canonical
 * Topic: DP vs greedy
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * A coin system (always contains 1) is canonical if the greedy method
 * (always take the biggest coin that fits) gives the fewest coins for
 * every amount. Decide if the given system is canonical.
 * Input: n, then n coin values in increasing order.
 * Output: canonical or non-canonical.
 */
import java.io.*;
import java.util.*;

public class Canonical {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine().trim());
        int[] coins = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) coins[i] = Integer.parseInt(st.nextToken());

        /*
         * Known fact: if greedy ever fails, it fails below
         * (largest coin + second largest coin). So check every
         * amount up to there with DP (true best) vs greedy.
         */
        int limit = (n >= 2) ? coins[n - 1] + coins[n - 2] : coins[0];

        int[] dp = new int[limit + 1];    // fewest coins (unbounded)
        int[] greedy = new int[limit + 1]; // coins greedy uses

        boolean canonical = true;
        for (int s = 1; s <= limit && canonical; s++) {
            dp[s] = Integer.MAX_VALUE;
            int biggestFit = 0;

            for (int c : coins) {
                if (c > s) break; // coins are sorted
                dp[s] = Math.min(dp[s], dp[s - c] + 1);
                biggestFit = c;
            }

            // greedy: take the biggest coin, then greedy on the rest
            greedy[s] = greedy[s - biggestFit] + 1;

            if (greedy[s] > dp[s]) canonical = false;
        }

        System.out.println(canonical ? "canonical" : "non-canonical");
    }
}
