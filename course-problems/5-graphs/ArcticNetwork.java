/*
 * Kattis: arcticnetwork
 * https://open.kattis.com/problems/arcticnetwork
 * Course: COMP30970, Week 7: Graphs 2
 * Topic: MST (Prim)
 * Solution: your own (from kattis/ArcticNetwork.java)
 *
 * Question (summary in my own words; read the full statement on Kattis):
 * P outposts need to be connected. S of them get satellite channels (free
 * links between any of them); the rest need radios, all with the same range
 * D. Print the smallest D (2 decimal places) for each case.
 */
import java.io.*;
import java.util.*;

public class ArcticNetwork {
    public static void main(String[] args) throws IOException {
        StreamTokenizer st = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        st.nextToken();
        int n = (int) st.nval;
        StringBuilder out = new StringBuilder();

        while (n-- > 0) {
            st.nextToken(); int s = (int) st.nval;
            st.nextToken(); int p = (int) st.nval;
            double[] x = new double[p], y = new double[p];
            for (int i = 0; i < p; i++) {
                st.nextToken(); x[i] = st.nval;
                st.nextToken(); y[i] = st.nval;
            }

            // Prim's MST in O(P^2) on the complete graph (squared distances)
            double[] best = new double[p];
            boolean[] used = new boolean[p];
            Arrays.fill(best, Double.MAX_VALUE);
            best[0] = 0;
            double[] edges = new double[p - 1];
            int cnt = 0;

            for (int it = 0; it < p; it++) {
                int u = -1;
                for (int v = 0; v < p; v++)
                    if (!used[v] && (u == -1 || best[v] < best[u])) u = v;
                used[u] = true;
                if (it > 0) edges[cnt++] = best[u];
                for (int v = 0; v < p; v++) {
                    if (!used[v]) {
                        double dx = x[u] - x[v], dy = y[u] - y[v];
                        double d = dx * dx + dy * dy;
                        if (d < best[v]) best[v] = d;
                    }
                }
            }

            // S satellite channels let us drop the S-1 longest MST edges
            Arrays.sort(edges);
            double ans = 0;
            if (s < p) ans = Math.sqrt(edges[p - s - 1]);
            out.append(String.format(Locale.US, "%.2f", ans)).append('\n');
        }
        System.out.print(out);
    }
}