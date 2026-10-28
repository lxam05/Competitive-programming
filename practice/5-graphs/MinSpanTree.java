/*
 * Kattis: minspantree
 * https://open.kattis.com/problems/minspantree
 * Topic: Kruskal MST
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * Given a weighted undirected graph, find a minimum spanning tree.
 * Print its cost and its edges (each as "u v" with u < v, sorted), or
 * "Impossible" if the graph isn't connected.
 * Input: several cases "n m" + m edges "u v w", ending with "0 0".
 * Output: cost then the edges, or Impossible.
 */
import java.io.*;
import java.util.*;

public class MinSpanTree {
    static BufferedReader br;
    static StringTokenizer st;
    static int[] parent;

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
            if (n == 0 && m == 0) break;

            int[][] edges = new int[m][3]; // {u, v, w}
            for (int i = 0; i < m; i++) {
                int u = Integer.parseInt(next());
                int v = Integer.parseInt(next());
                int w = Integer.parseInt(next());
                // store with u < v because output needs that
                edges[i][0] = Math.min(u, v);
                edges[i][1] = Math.max(u, v);
                edges[i][2] = w;
            }

            // Kruskal: cheapest edges first, skip ones that make a cycle
            Arrays.sort(edges, (a, b) -> Integer.compare(a[2], b[2]));

            parent = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;

            ArrayList<int[]> used = new ArrayList<>();
            long total = 0;
            for (int[] e : edges) {
                int ra = find(e[0]), rb = find(e[1]);
                if (ra == rb) continue;
                parent[ra] = rb;
                total += e[2];
                used.add(e);
            }

            if (used.size() != n - 1) {
                output.append("Impossible\n");
                continue;
            }

            // print edges in lexicographic order
            used.sort((a, b) -> a[0] != b[0] ? Integer.compare(a[0], b[0]) : Integer.compare(a[1], b[1]));
            output.append(total).append('\n');
            for (int[] e : used) output.append(e[0]).append(' ').append(e[1]).append('\n');
        }

        System.out.print(output);
    }

    static int find(int x) {
        while (x != parent[x]) {
            parent[x] = parent[parent[x]];
            x = parent[x];
        }
        return x;
    }
}
