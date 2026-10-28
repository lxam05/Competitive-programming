/*
 * Kattis: walrusweights
 * https://open.kattis.com/problems/walrusweights
 * Topic: DP (subset sum)
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * Choose some of n weight plates so their total is as close to 1000 as
 * possible. If two totals are equally close, pick the larger.
 * Input: n, then n weights.
 * Output: the best total.
 */
import java.io.*;
import java.util.*;

public class WalrusWeights {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine().trim());

        // can[s] = true if some plates add up to exactly s
        // nothing above 2000 is ever closer to 1000 than 0 is
        int limit = 2000;
        boolean[] can = new boolean[limit + 1];
        can[0] = true;

        for (int i = 0; i < n; i++) {
            int w = Integer.parseInt(br.readLine().trim());

            // backwards so each plate is used once (subset sum)
            for (int s = limit; s >= w; s--) {
                if (can[s - w]) can[s] = true;
            }
        }

        // closest to 1000, ties go to the bigger weight
        int best = 0;
        for (int s = 0; s <= limit; s++) {
            if (!can[s]) continue;
            if (Math.abs(s - 1000) <= Math.abs(best - 1000)) best = s;
        }

        System.out.println(best);
    }
}
