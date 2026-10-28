/*
 * Kattis: islandhopping
 * https://open.kattis.com/problems/islandhopping
 * Topic: MST (Prim)
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * Islands are points on a plane. Build bridges (straight lines) so every
 * island is connected, with the smallest total bridge length.
 * Input: n cases. Each: m, then m lines "x y".
 * Output: the total length per case.
 */
import java.io.*;
import java.util.*;

public class IslandHopping {
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
            int m = Integer.parseInt(next());
            double[] x = new double[m], y = new double[m];
            for (int i = 0; i < m; i++) {
                x[i] = Double.parseDouble(next());
                y[i] = Double.parseDouble(next());
            }

            // Prim's MST in O(m^2), same as Arctic Network
            double[] best = new double[m];
            boolean[] used = new boolean[m];
            Arrays.fill(best, Double.MAX_VALUE);
            best[0] = 0;
            double total = 0;

            for (int it = 0; it < m; it++) {
                // closest island not in the tree yet
                int u = -1;
                for (int v = 0; v < m; v++) {
                    if (!used[v] && (u == -1 || best[v] < best[u])) u = v;
                }
                used[u] = true;
                total += best[u];

                for (int v = 0; v < m; v++) {
                    if (used[v]) continue;
                    double d = Math.hypot(x[u] - x[v], y[u] - y[v]);
                    if (d < best[v]) best[v] = d;
                }
            }

            output.append(String.format(Locale.US, "%.6f", total)).append('\n');
        }

        System.out.print(output);
    }
}
