/*
 * Kattis: exactchange2
 * https://open.kattis.com/problems/exactchange2
 * Topic: DP (0/1 knapsack)
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * You must pay at least a price using some of your coins (no change is
 * given). Pay the smallest amount you can, and among those use the
 * fewest coins.
 * Input: T cases. Each: price, n, n coin values.
 * Output: "amount coins" per case.
 */
import java.io.*;
import java.util.*;

public class ExactChange2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder output = new StringBuilder();

        int tests = Integer.parseInt(br.readLine().trim());

        while (tests-- > 0) {
            int price = Integer.parseInt(br.readLine().trim());
            int n = Integer.parseInt(br.readLine().trim());

            /*
             * 0/1 knapsack:
             * minCoins[s] = fewest coins that add up to exactly s.
             * We never need to pay more than price + 10000
             * (every coin is at most 10000).
             */
            int limit = price + 10000;
            int INF = Integer.MAX_VALUE / 2;
            int[] minCoins = new int[limit + 1];
            Arrays.fill(minCoins, INF);
            minCoins[0] = 0;

            for (int i = 0; i < n; i++) {
                int coin = Integer.parseInt(br.readLine().trim());

                // go backwards so each coin is used at most once
                for (int s = limit; s >= coin; s--) {
                    if (minCoins[s - coin] + 1 < minCoins[s]) {
                        minCoins[s] = minCoins[s - coin] + 1;
                    }
                }
            }

            // smallest amount >= price that we can make
            for (int s = price; s <= limit; s++) {
                if (minCoins[s] < INF) {
                    output.append(s).append(' ').append(minCoins[s]).append('\n');
                    break;
                }
            }
        }

        System.out.print(output);
    }
}
