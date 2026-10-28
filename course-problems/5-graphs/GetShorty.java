/*
 * Kattis: getshorty
 * https://open.kattis.com/problems/getshorty
 * Course: COMP30970, Week 7: Graphs 2
 * Topic: Dijkstra (max product)
 * Solution: your own (from kattis/GetShorty.java)
 *
 * Question (summary in my own words; read the full statement on Kattis):
 * Corridors scale your size by a factor between 0 and 1. Find the largest
 * size you can still have when you reach the exit (node n-1) from 0.
 * Input: cases "n m" + m lines "a b f", ending with "0 0".
 */
import java.io.*;
import java.util.*;

public class GetShorty {
    static BufferedReader br;
    static StringTokenizer st;

    static String next() throws IOException {
        while (st == null || !st.hasMoreTokens()) st = new StringTokenizer(br.readLine());
        return st.nextToken();
    }

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        while (true) {
            int n = Integer.parseInt(next()), m = Integer.parseInt(next());
            if (n == 0 && m == 0) break;

            List<double[]>[] adj = new ArrayList[n]; // {to, factor}
            for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
            for (int i = 0; i < m; i++) {
                int a = Integer.parseInt(next()), b = Integer.parseInt(next());
                double f = Double.parseDouble(next());
                adj[a].add(new double[]{b, f});
                adj[b].add(new double[]{a, f});
            }

            // Max-product Dijkstra: factors are in [0,1], so products only shrink.
            double[] best = new double[n];
            Arrays.fill(best, -1.0);
            best[0] = 1.0;
            PriorityQueue<double[]> pq = new PriorityQueue<>((p, r) -> Double.compare(r[0], p[0]));
            pq.add(new double[]{1.0, 0});
            while (!pq.isEmpty()) {
                double[] cur = pq.poll();
                int u = (int) cur[1];
                if (cur[0] < best[u]) continue;
                if (u == n - 1) break;
                for (double[] e : adj[u]) {
                    int v = (int) e[0];
                    double np = cur[0] * e[1];
                    if (np > best[v]) {
                        best[v] = np;
                        pq.add(new double[]{np, v});
                    }
                }
            }
            sb.append(String.format(Locale.US, "%.4f", best[n - 1])).append('\n');
        }
        System.out.print(sb);
    }
}