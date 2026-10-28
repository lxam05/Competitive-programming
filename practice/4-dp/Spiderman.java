/*
 * Kattis: spiderman
 * https://open.kattis.com/problems/spiderman
 * Topic: DP with path reconstruction
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * A workout is a list of distances; each one is climbed up or down.
 * Start and finish at height 0, never go below 0, and keep the highest
 * point as low as possible.
 * Input: N cases. Each: M, then M distances.
 * Output: a string of U/D per case, or IMPOSSIBLE.
 */
import java.io.*;
import java.util.*;

public class Spiderman {
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
            int[] dist = new int[m];
            int total = 0;
            for (int i = 0; i < m; i++) {
                dist[i] = Integer.parseInt(next());
                total += dist[i];
            }

            /*
             * best[i][h] = lowest "highest point so far" after i moves,
             * standing at height h. Height can never go above total.
             * cameFrom[i][h] = height before move i-1, to rebuild the path.
             */
            int INF = Integer.MAX_VALUE;
            int[][] best = new int[m + 1][total + 1];
            int[][] cameFrom = new int[m + 1][total + 1];
            for (int[] row : best) Arrays.fill(row, INF);
            best[0][0] = 0;

            for (int i = 0; i < m; i++) {
                for (int h = 0; h <= total; h++) {
                    if (best[i][h] == INF) continue;

                    // go up
                    int up = h + dist[i];
                    if (up <= total) {
                        int high = Math.max(best[i][h], up);
                        if (high < best[i + 1][up]) {
                            best[i + 1][up] = high;
                            cameFrom[i + 1][up] = h;
                        }
                    }

                    // go down (never below street level)
                    int down = h - dist[i];
                    if (down >= 0 && best[i][h] < best[i + 1][down]) {
                        best[i + 1][down] = best[i][h];
                        cameFrom[i + 1][down] = h;
                    }
                }
            }

            if (best[m][0] == INF) {
                output.append("IMPOSSIBLE\n");
                continue;
            }

            // rebuild moves backwards from (m, 0)
            char[] moves = new char[m];
            int h = 0;
            for (int i = m; i > 0; i--) {
                int prev = cameFrom[i][h];
                moves[i - 1] = prev < h ? 'U' : 'D';
                h = prev;
            }
            output.append(new String(moves)).append('\n');
        }

        System.out.print(output);
    }
}
