/*
 * Kattis: dominoes2
 * https://open.kattis.com/problems/dominoes2
 * Course: COMP30970, Week 6: Graphs 1
 * Topic: BFS / DFS reachability
 * Solution: written for this set
 *
 * Question (summary in my own words; read the full statement on Kattis):
 * Some dominoes knock over others (x -> y). A few are pushed by hand.
 * Print how many dominoes fall in total, for each case.
 * Input: T cases. Each: n m l, m lines "x y", l lines z.
 */
import java.io.*;
import java.util.*;

public class Dominoes2 {
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
            int m = Integer.parseInt(next());
            int l = Integer.parseInt(next());

            List<Integer>[] adj = new ArrayList[n + 1];
            for (int i = 1; i <= n; i++) adj[i] = new ArrayList<>();
            for (int i = 0; i < m; i++) {
                int x = Integer.parseInt(next());
                int y = Integer.parseInt(next());
                adj[x].add(y); // x falling knocks over y
            }

            // BFS from every domino knocked by hand
            boolean[] fallen = new boolean[n + 1];
            ArrayDeque<Integer> queue = new ArrayDeque<>();
            for (int i = 0; i < l; i++) {
                int z = Integer.parseInt(next());
                if (!fallen[z]) {
                    fallen[z] = true;
                    queue.add(z);
                }
            }

            int count = queue.size();
            while (!queue.isEmpty()) {
                int u = queue.poll();
                for (int v : adj[u]) {
                    if (!fallen[v]) {
                        fallen[v] = true;
                        count++;
                        queue.add(v);
                    }
                }
            }

            output.append(count).append('\n');
        }

        System.out.print(output);
    }
}
