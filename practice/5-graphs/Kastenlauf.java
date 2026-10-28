/*
 * Kattis: kastenlauf
 * https://open.kattis.com/problems/kastenlauf
 * Topic: BFS
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * Walk from home to a festival carrying a crate of 20 beers, drinking one
 * every 50 m (so 1000 m max). Beer stores on the way refill the crate.
 * Distances are Manhattan. Can you get there without running out?
 * Input: t cases. Each: n, then n+2 coordinates (home, stores, festival).
 * Output: happy or sad.
 */
import java.io.*;
import java.util.*;

public class Kastenlauf {
    static BufferedReader br;
    static StringTokenizer st;

    static String next() throws IOException {
        while (st == null || !st.hasMoreTokens()) st = new StringTokenizer(br.readLine());
        return st.nextToken();
    }

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder output = new StringBuilder();

        int tests = Integer.parseInt(next());

        while (tests-- > 0) {
            int n = Integer.parseInt(next());

            // point 0 = home, 1..n = stores, n+1 = festival
            int total = n + 2;
            int[] x = new int[total], y = new int[total];
            for (int i = 0; i < total; i++) {
                x[i] = Integer.parseInt(next());
                y[i] = Integer.parseInt(next());
            }

            /*
             * 20 beers * 50 m = 1000 m between refills.
             * Two points are connected if Manhattan distance <= 1000.
             * BFS from home, see if we reach the festival.
             */
            boolean[] seen = new boolean[total];
            ArrayDeque<Integer> queue = new ArrayDeque<>();
            queue.add(0);
            seen[0] = true;

            while (!queue.isEmpty()) {
                int u = queue.poll();
                for (int v = 0; v < total; v++) {
                    if (seen[v]) continue;
                    if (Math.abs(x[u] - x[v]) + Math.abs(y[u] - y[v]) <= 1000) {
                        seen[v] = true;
                        queue.add(v);
                    }
                }
            }

            output.append(seen[total - 1] ? "happy\n" : "sad\n");
        }

        System.out.print(output);
    }
}
