/*
 * Kattis: lostmap
 * https://open.kattis.com/problems/lostmap
 * Course: COMP30970, Week 7: Graphs 2
 * Topic: MST
 * Solution: your own (from kattis/LostMap.java)
 *
 * Question (summary in my own words; read the full statement on Kattis):
 * You get the full distance matrix between n villages, which came from a
 * road network that is a tree. Print the n-1 roads (pairs of villages).
 */
import java.io.*;
import java.util.*;

public class LostMap {
    static int[] parent;

    // Which set (group) is town x in? Follow parents up to the leader.
    static int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]);
        return parent[x];
    }

    public static void main(String[] args) throws IOException {
        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        in.nextToken();
        int n = (int) in.nval;

        // Read the table. Each pair (i, j) with i < j is an edge: {weight, from, to}.
        int[][] edges = new int[n * (n - 1) / 2][];
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                in.nextToken();
                int d = (int) in.nval;
                if (i < j) edges[count++] = new int[]{d, i, j};
            }
        }

        // Sort edges by weight, cheapest first.
        Arrays.sort(edges, (a, b) -> Integer.compare(a[0], b[0]));

        // Every town starts in its own set.
        parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;

        List<int[]> roads = new ArrayList<>();
        for (int[] e : edges) {
            int a = find(e[1]), b = find(e[2]);
            if (a != b) {                 // different sets, so no cycle
                parent[a] = b;            // merge the two sets
                roads.add(new int[]{e[1], e[2]});
                if (roads.size() == n - 1) break;
            }
        }

        // Print roads in order: by first village, then by second.
        roads.sort((a, b) -> a[0] != b[0] ? Integer.compare(a[0], b[0]) : Integer.compare(a[1], b[1]));
        StringBuilder out = new StringBuilder();
        for (int[] r : roads) {
            out.append(r[0] + 1).append(' ').append(r[1] + 1).append('\n'); // villages are 1-indexed
        }
        System.out.print(out);
    }
}