/*
 * Kattis: dominos
 * https://open.kattis.com/problems/dominos
 * Topic: Strongly connected components
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * Some dominos knock over others (x falls -> y falls). Find the fewest
 * dominos you must push by hand so every domino ends up falling.
 * Input: T cases. Each: n m, then m lines "x y".
 * Output: the minimum number per case.
 */
import java.io.*;
import java.util.*;

public class Dominos {
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

            List<Integer>[] adj = new ArrayList[n + 1];
            List<Integer>[] radj = new ArrayList[n + 1]; // reversed edges
            for (int i = 1; i <= n; i++) {
                adj[i] = new ArrayList<>();
                radj[i] = new ArrayList<>();
            }
            for (int i = 0; i < m; i++) {
                int a = Integer.parseInt(next());
                int b = Integer.parseInt(next());
                adj[a].add(b);
                radj[b].add(a);
            }

            /*
             * Kosaraju's SCC algorithm.
             * A group of dominos that knock each other over (an SCC)
             * only needs pushing by hand if nothing OUTSIDE the group
             * knocks it over (in-degree 0 in the SCC graph).
             * DFS is done with an explicit stack: recursion would
             * overflow with 100,000 dominos.
             */

            // pass 1: get finish order on the normal graph
            boolean[] visited = new boolean[n + 1];
            int[] order = new int[n];
            int orderSize = 0;
            int[] edgeIndex = new int[n + 1];
            int[] stack = new int[n + 1];

            for (int s = 1; s <= n; s++) {
                if (visited[s]) continue;
                int top = 0;
                stack[top++] = s;
                visited[s] = true;

                while (top > 0) {
                    int u = stack[top - 1];
                    if (edgeIndex[u] < adj[u].size()) {
                        int v = adj[u].get(edgeIndex[u]++);
                        if (!visited[v]) {
                            visited[v] = true;
                            stack[top++] = v;
                        }
                    } else {
                        order[orderSize++] = u; // u is finished
                        top--;
                    }
                }
            }

            // pass 2: on the reversed graph, latest finished first
            int[] comp = new int[n + 1];
            Arrays.fill(comp, -1);
            int compCount = 0;

            for (int i = n - 1; i >= 0; i--) {
                int s = order[i];
                if (comp[s] != -1) continue;

                int top = 0;
                stack[top++] = s;
                comp[s] = compCount;
                while (top > 0) {
                    int u = stack[--top];
                    for (int v : radj[u]) {
                        if (comp[v] == -1) {
                            comp[v] = compCount;
                            stack[top++] = v;
                        }
                    }
                }
                compCount++;
            }

            // find SCCs that some other SCC points into
            boolean[] hasIncoming = new boolean[compCount];
            for (int u = 1; u <= n; u++) {
                for (int v : adj[u]) {
                    if (comp[u] != comp[v]) hasIncoming[comp[v]] = true;
                }
            }

            int answer = 0;
            for (int c = 0; c < compCount; c++) {
                if (!hasIncoming[c]) answer++;
            }
            output.append(answer).append('\n');
        }

        System.out.print(output);
    }
}
