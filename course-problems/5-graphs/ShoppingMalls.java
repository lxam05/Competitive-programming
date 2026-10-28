/*
 * Kattis: shoppingmalls
 * https://open.kattis.com/problems/shoppingmalls
 * Course: COMP30970, Week 7: Graphs 2
 * Topic: Shortest path + path output
 * Solution: your own (from kattis/ShoppingMalls.java)
 *
 * Question (summary in my own words; read the full statement on Kattis):
 * A mall of places on floors with walkways, stairs, lifts and escalators,
 * each with its own travel cost. For each query, print the places on a
 * cheapest route from a to b.
 */
import java.io.*;
import java.util.*;

public class ShoppingMalls {
    static BufferedReader br;
    static StringTokenizer st;

    static String next() throws IOException {
        while (st == null || !st.hasMoreTokens()) st = new StringTokenizer(br.readLine());
        return st.nextToken();
    }

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(next()), m = Integer.parseInt(next());
        double[] z = new double[n], x = new double[n], y = new double[n];
        for (int i = 0; i < n; i++) {
            z[i] = Double.parseDouble(next()) * 5.0;
            x[i] = Double.parseDouble(next());
            y[i] = Double.parseDouble(next());
        }

        List<double[]>[] adj = new ArrayList[n]; // {to, cost}
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            int a = Integer.parseInt(next()), b = Integer.parseInt(next());
            String t = next();
            double d = Math.sqrt((z[a]-z[b])*(z[a]-z[b]) + (x[a]-x[b])*(x[a]-x[b]) + (y[a]-y[b])*(y[a]-y[b]));
            if (t.equals("walking") || t.equals("stairs")) {
                adj[a].add(new double[]{b, d}); adj[b].add(new double[]{a, d});
            } else if (t.equals("lift")) {
                adj[a].add(new double[]{b, 1}); adj[b].add(new double[]{a, 1});
            } else { // escalator
                adj[a].add(new double[]{b, 1}); adj[b].add(new double[]{a, 3 * d});
            }
        }

        int[][] parCache = new int[n][];
        int q = Integer.parseInt(next());
        StringBuilder sb = new StringBuilder();
        for (int qi = 0; qi < q; qi++) {
            int a = Integer.parseInt(next()), b = Integer.parseInt(next());
            if (parCache[a] == null) parCache[a] = dijkstra(a, n, adj);
            int[] par = parCache[a];
            ArrayDeque<Integer> path = new ArrayDeque<>();
            for (int v = b; v != a; v = par[v]) path.addFirst(v);
            path.addFirst(a);
            StringBuilder line = new StringBuilder();
            for (int v : path) {
                if (line.length() > 0) line.append(' ');
                line.append(v);
            }
            sb.append(line).append('\n');
        }
        System.out.print(sb);
    }

    static int[] dijkstra(int s, int n, List<double[]>[] adj) {
        double[] dist = new double[n];
        int[] par = new int[n];
        Arrays.fill(dist, Double.MAX_VALUE);
        Arrays.fill(par, -1);
        dist[s] = 0;
        PriorityQueue<double[]> pq = new PriorityQueue<>((p, r) -> Double.compare(p[0], r[0]));
        pq.add(new double[]{0, s});
        while (!pq.isEmpty()) {
            double[] cur = pq.poll();
            int u = (int) cur[1];
            if (cur[0] > dist[u]) continue;
            for (double[] e : adj[u]) {
                int v = (int) e[0];
                double nd = cur[0] + e[1];
                if (nd < dist[v]) {
                    dist[v] = nd;
                    par[v] = u;
                    pq.add(new double[]{nd, v});
                }
            }
        }
        return par;
    }
}