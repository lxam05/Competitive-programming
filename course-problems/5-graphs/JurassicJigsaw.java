/*
 * Kattis: jurassicjigsaw
 * https://open.kattis.com/problems/jurassicjigsaw
 * Course: COMP30970, Week 7: Graphs 2
 * Topic: MST on Hamming distance
 * Solution: your own (from kattis/JurassicJigsaw.java)
 *
 * Question (summary in my own words; read the full statement on Kattis):
 * n DNA strings of equal length. The cost between two strings is the number
 * of positions where they differ. Build the cheapest tree linking every
 * string; print its cost and its edges.
 */
import java.io.*;
import java.util.*;

public class JurassicJigsaw {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tk = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(tk.nextToken());
        int k = Integer.parseInt(tk.nextToken());
        char[][] s = new char[n][];
        for (int i = 0; i < n; i++) s[i] = br.readLine().trim().toCharArray();

        // Prim's MST in O(n^2 * k) on the complete graph, weight = Hamming distance
        int[] best = new int[n];
        int[] parent = new int[n];
        boolean[] used = new boolean[n];
        Arrays.fill(best, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        best[0] = 0;

        long total = 0;
        StringBuilder edges = new StringBuilder();

        for (int it = 0; it < n; it++) {
            int u = -1;
            for (int v = 0; v < n; v++)
                if (!used[v] && (u == -1 || best[v] < best[u])) u = v;
            used[u] = true;
            if (parent[u] != -1) {
                total += best[u];
                edges.append(parent[u]).append(' ').append(u).append('\n');
            }
            for (int v = 0; v < n; v++) {
                if (used[v]) continue;
                int d = 0;
                for (int c = 0; c < k; c++) if (s[u][c] != s[v][c]) d++;
                if (d < best[v]) { best[v] = d; parent[v] = u; }
            }
        }

        System.out.print(total + "\n" + edges);
    }
}