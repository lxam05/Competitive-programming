/*
 * Kattis: shortestpath1
 * https://open.kattis.com/problems/shortestpath1
 * Topic: Dijkstra
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * Directed graph with non-negative edge weights and a start node s.
 * Answer queries: the shortest distance from s to node q.
 * Input: several cases "n m q s" + m edges "u v w" + q queries,
 * ending with "0 0 0 0".
 * Output: the distance or "Impossible" per query (blank line after each case).
 */
import java.io.*;
import java.util.*;

public class ShortestPath1 {
    static BufferedReader br;
    static StringTokenizer st;

    static String next() throws IOException {
        while (st == null || !st.hasMoreTokens()) st = new StringTokenizer(br.readLine());
        return st.nextToken();
    }

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder output = new StringBuilder();

        while (true) {
            int n = Integer.parseInt(next());
            int m = Integer.parseInt(next());
            int q = Integer.parseInt(next());
            int s = Integer.parseInt(next());
            if (n == 0 && m == 0 && q == 0 && s == 0) break;

            List<int[]>[] adj = new ArrayList[n]; // {to, weight}
            for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
            for (int i = 0; i < m; i++) {
                int u = Integer.parseInt(next());
                int v = Integer.parseInt(next());
                int w = Integer.parseInt(next());
                adj[u].add(new int[]{v, w}); // directed
            }

            // standard Dijkstra with a priority queue of {dist, node}
            long[] dist = new long[n];
            Arrays.fill(dist, Long.MAX_VALUE);
            dist[s] = 0;
            PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
            pq.add(new long[]{0, s});

            while (!pq.isEmpty()) {
                long[] cur = pq.poll();
                int u = (int) cur[1];
                if (cur[0] > dist[u]) continue; // old entry

                for (int[] e : adj[u]) {
                    long nd = dist[u] + e[1];
                    if (nd < dist[e[0]]) {
                        dist[e[0]] = nd;
                        pq.add(new long[]{nd, e[0]});
                    }
                }
            }

            for (int i = 0; i < q; i++) {
                int target = Integer.parseInt(next());
                if (dist[target] == Long.MAX_VALUE) output.append("Impossible\n");
                else output.append(dist[target]).append('\n');
            }
            output.append('\n');
        }

        System.out.print(output);
    }
}
