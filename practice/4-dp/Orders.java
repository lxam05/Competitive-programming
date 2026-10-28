/*
 * Kattis: orders
 * https://open.kattis.com/problems/orders
 * Topic: DP (unbounded knapsack, counting ways)
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * A menu of n items with prices. For each order total, find which items
 * (each can be ordered many times) were bought. Print the item numbers
 * (1-indexed, ascending), or "Impossible" if no combination works, or
 * "Ambiguous" if more than one combination works.
 * Input: n, n prices, m, m totals.
 * Output: one line per order.
 */
import java.io.*;
import java.util.*;

public class Orders {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder output = new StringBuilder();

        int n = Integer.parseInt(br.readLine().trim());
        int[] cost = new int[n + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) cost[i] = Integer.parseInt(st.nextToken());

        int m = Integer.parseInt(br.readLine().trim());
        int[] orders = new int[m];
        int maxOrder = 0;
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) {
            orders[i] = Integer.parseInt(st.nextToken());
            maxOrder = Math.max(maxOrder, orders[i]);
        }

        /*
         * Unbounded knapsack that COUNTS ways (capped at 2,
         * since we only care about 0, 1 or "more than 1").
         * Items are looped on the outside so each multiset of
         * items is counted once, not once per ordering.
         * lastItem[s] remembers an item used to reach s.
         */
        int[] ways = new int[maxOrder + 1];
        int[] lastItem = new int[maxOrder + 1];
        ways[0] = 1;

        for (int i = 1; i <= n; i++) {
            for (int s = cost[i]; s <= maxOrder; s++) {
                if (ways[s - cost[i]] == 0) continue;
                if (ways[s] == 0) lastItem[s] = i;
                ways[s] = Math.min(2, ways[s] + ways[s - cost[i]]);
            }
        }

        for (int i = 0; i < m; i++) {
            int s = orders[i];

            if (ways[s] == 0) {
                output.append("Impossible\n");
            } else if (ways[s] > 1) {
                output.append("Ambiguous\n");
            } else {
                // walk back through lastItem to rebuild the order
                ArrayList<Integer> items = new ArrayList<>();
                while (s > 0) {
                    items.add(lastItem[s]);
                    s -= cost[lastItem[s]];
                }
                Collections.sort(items);

                for (int j = 0; j < items.size(); j++) {
                    if (j > 0) output.append(' ');
                    output.append(items.get(j));
                }
                output.append('\n');
            }
        }

        System.out.print(output);
    }
}
