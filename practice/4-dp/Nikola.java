/*
 * Kattis: nikola
 * https://open.kattis.com/problems/nikola
 * Topic: DP
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * N squares in a row, each with an entrance fee. Start on square 1; the
 * first jump goes to square 2. Every forward jump must be one longer than
 * the previous jump; a backward jump has the same length as the previous
 * one. Pay the fee of every square you land on. Reach square N as
 * cheaply as possible.
 * Input: N, then N fees.
 * Output: the minimum total cost.
 */
import java.io.*;
import java.util.*;

public class Nikola {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine().trim());
        int[] fee = new int[n + 1];
        for (int i = 1; i <= n; i++) fee[i] = Integer.parseInt(br.readLine().trim());

        /*
         * dp[pos][len] = cheapest cost to stand on square pos
         * where the last jump had length len.
         *   forward:  (pos, len) -> (pos + len + 1, len + 1)
         *   backward: (pos, len) -> (pos - len,     len)
         * A backward jump keeps len and goes left, so for each len
         * we handle positions from right to left.
         */
        int INF = Integer.MAX_VALUE / 2;
        int[][] dp = new int[n + 1][n + 1];
        for (int[] row : dp) Arrays.fill(row, INF);

        // the first jump is always from square 1 to square 2
        if (n >= 2) dp[2][1] = fee[2];

        for (int len = 1; len <= n; len++) {
            for (int pos = n; pos >= 1; pos--) {
                if (dp[pos][len] == INF) continue;
                int cost = dp[pos][len];

                int back = pos - len;
                if (back >= 1 && cost + fee[back] < dp[back][len]) {
                    dp[back][len] = cost + fee[back];
                }

                int forward = pos + len + 1;
                if (forward <= n && len + 1 <= n && cost + fee[forward] < dp[forward][len + 1]) {
                    dp[forward][len + 1] = cost + fee[forward];
                }
            }
        }

        int best = INF;
        for (int len = 1; len <= n; len++) best = Math.min(best, dp[n][len]);

        System.out.println(best);
    }
}
