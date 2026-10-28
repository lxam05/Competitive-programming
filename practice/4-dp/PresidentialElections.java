/*
 * Kattis: presidentialelections
 * https://open.kattis.com/problems/presidentialelections
 * Topic: DP (0/1 knapsack)
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * S states, each with D delegates and current votes for the two parties
 * (C and F) plus U undecided voters. A state's delegates go to whoever
 * gets more votes. Find the fewest undecided voters you must convince so
 * your party wins more than half of all delegates.
 * Input: S, then S lines "D C F U".
 * Output: the minimum number of voters, or impossible.
 */
import java.io.*;
import java.util.*;

public class PresidentialElections {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int s = Integer.parseInt(br.readLine().trim());

        int[] delegates = new int[s];
        long[] votesNeeded = new long[s]; // -1 if the state can't be won
        int totalDelegates = 0;

        for (int i = 0; i < s; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int d = Integer.parseInt(st.nextToken());
            long c = Long.parseLong(st.nextToken());
            long f = Long.parseLong(st.nextToken());
            long u = Long.parseLong(st.nextToken());

            delegates[i] = d;
            totalDelegates += d;

            /*
             * If we convince x undecided voters we need
             * c + x > f + (u - x)  ->  2x > f + u - c.
             */
            long gap = f + u - c;
            long x = (gap < 0) ? 0 : gap / 2 + 1;
            votesNeeded[i] = (x <= u) ? x : -1;
        }

        int need = totalDelegates / 2 + 1;

        /*
         * 0/1 knapsack: minCost[k] = fewest voters to convince
         * to win exactly k delegates. Answer = best k >= need.
         */
        long INF = Long.MAX_VALUE / 2;
        long[] minCost = new long[totalDelegates + 1];
        Arrays.fill(minCost, INF);
        minCost[0] = 0;

        for (int i = 0; i < s; i++) {
            if (votesNeeded[i] < 0) continue;
            for (int k = totalDelegates; k >= delegates[i]; k--) {
                if (minCost[k - delegates[i]] + votesNeeded[i] < minCost[k]) {
                    minCost[k] = minCost[k - delegates[i]] + votesNeeded[i];
                }
            }
        }

        long best = INF;
        for (int k = need; k <= totalDelegates; k++) best = Math.min(best, minCost[k]);

        System.out.println(best >= INF ? "impossible" : String.valueOf(best));
    }
}
