/*
 * Kattis: brexit
 * https://open.kattis.com/problems/brexit
 * Topic: BFS cascade
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * C countries with P partnerships. Country L leaves the union. A country
 * leaves once at least half of its partners have left. Does your
 * country X end up leaving?
 * Input: C P X L, then P lines "a b".
 * Output: leave or stay.
 */
import java.io.*;
import java.util.*;

public class Brexit {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int c = Integer.parseInt(st.nextToken());
        int p = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());
        int l = Integer.parseInt(st.nextToken());

        List<Integer>[] adj = new ArrayList[c + 1];
        for (int i = 1; i <= c; i++) adj[i] = new ArrayList<>();
        for (int i = 0; i < p; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            adj[a].add(b);
            adj[b].add(a);
        }

        /*
         * BFS-style cascade.
         * lostPartners[v] counts partners of v that have left.
         * v leaves once lostPartners[v] * 2 >= its original partner count.
         */
        int[] lostPartners = new int[c + 1];
        boolean[] left = new boolean[c + 1];
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        left[l] = true;
        queue.add(l);

        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v : adj[u]) {
                if (left[v]) continue;
                lostPartners[v]++;
                if (lostPartners[v] * 2 >= adj[v].size()) {
                    left[v] = true;
                    queue.add(v);
                }
            }
        }

        System.out.println(left[x] ? "leave" : "stay");
    }
}
