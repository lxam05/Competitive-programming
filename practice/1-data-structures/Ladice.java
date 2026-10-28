/*
 * Kattis: ladice
 * https://open.kattis.com/problems/ladice
 * Topic: Union-Find
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * N items and L drawers. Item i can go in drawer A_i or B_i. Place the
 * items in order: try A, else B, else try to shuffle earlier items
 * (each to its other drawer) to make room. Print LADICA if item i was
 * stored, SMECE if it had to be thrown away.
 * Input: N L, then N lines "A B".
 * Output: N lines, LADICA or SMECE.
 */
import java.io.*;
import java.util.*;

public class Ladice {
    static int[] parent;
    static int[] free; // free drawers left in each set (only valid at the root)

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder output = new StringBuilder();

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int l = Integer.parseInt(st.nextToken());

        parent = new int[l + 1];
        free = new int[l + 1];
        for (int i = 1; i <= l; i++) {
            parent[i] = i;
            free[i] = 1;
        }

        /*
         * Idea: join drawer A and drawer B into one set.
         * Items can always be shuffled around inside a set,
         * so we only need to know how many empty drawers
         * the set still has.
         */
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            union(a, b);
            int root = find(a);

            if (free[root] > 0) {
                free[root]--;
                output.append("LADICA\n");
            } else {
                output.append("SMECE\n");
            }
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

    static void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);
        if (rootA == rootB) return;
        parent[rootB] = rootA;
        free[rootA] += free[rootB];
    }
}
