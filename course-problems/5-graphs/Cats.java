/*
 * Kattis: cats
 * https://open.kattis.com/problems/cats
 * Course: COMP30970, Week 7: Graphs 2
 * Topic: MST
 * Solution: your own (from kattis/Cats.java)
 *
 * Question (summary in my own words; read the full statement on Kattis):
 * A cat feeder has M millilitres of milk and C cats connected by distances.
 * Milk spills 1 ml per metre walked, and each cat needs 1 ml. Using the
 * cheapest network to reach every cat, can all cats be fed? Print yes/no.
 */
import java.io.*;
import java.util.*;

public class Cats {
    static DataInputStream in = new DataInputStream(new BufferedInputStream(System.in, 1 << 16));

    static int nextInt() throws IOException {
        int r = 0, b = in.read();
        while (b < '0' || b > '9') {
            if (b == '-') { b = in.read(); int x = 0; while (b >= '0' && b <= '9') { x = x * 10 + b - '0'; b = in.read(); } return -x; }
            b = in.read();
        }
        while (b >= '0' && b <= '9') { r = r * 10 + b - '0'; b = in.read(); }
        return r;
    }

    public static void main(String[] args) throws IOException {
        int t = nextInt();
        StringBuilder out = new StringBuilder();

        while (t-- > 0) {
            long m = nextInt();
            int c = nextInt();
            int[][] dist = new int[c][c];
            int edges = c * (c - 1) / 2;
            for (int e = 0; e < edges; e++) {
                int i = nextInt(), j = nextInt(), d = nextInt();
                dist[i][j] = d;
                dist[j][i] = d;
            }

            // Prim's MST in O(C^2) on the dense distance matrix
            int[] best = new int[c];
            boolean[] used = new boolean[c];
            Arrays.fill(best, Integer.MAX_VALUE);
            best[0] = 0;
            long total = 0;
            for (int it = 0; it < c; it++) {
                int u = -1;
                for (int v = 0; v < c; v++)
                    if (!used[v] && (u == -1 || best[v] < best[u])) u = v;
                used[u] = true;
                total += best[u];
                for (int v = 0; v < c; v++)
                    if (!used[v] && dist[u][v] < best[v]) best[v] = dist[u][v];
            }

            // walking the MST costs its weight; each cat also gets 1 ml
            out.append(total + c <= m ? "yes" : "no").append('\n');
        }
        System.out.print(out);
    }
}