/*
 * Kattis: swaptosort
 * https://open.kattis.com/problems/swaptosort
 * Topic: Union-Find
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * The array is N, N-1, ..., 1. You are given K pairs (a, b) and may swap
 * the values at positions a and b any number of times, in any order.
 * Can you sort the array into 1..N?
 * Input: N K, then K lines "a b" (1-indexed).
 * Output: Yes or No.
 */
import java.io.*;
import java.util.*;

public class SwapToSort {
    static int[] parent;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        parent = new int[n + 1];
        for (int i = 1; i <= n; i++) parent[i] = i;

        // positions that can swap end up in the same set
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            union(a, b);
        }

        /*
         * The array starts as n, n-1, ..., 1 and must become 1..n.
         * So the value at position i must travel to position n-i+1.
         * Inside one set we can reach any arrangement,
         * so i and n-i+1 just need to be in the same set.
         */
        boolean possible = true;
        for (int i = 1; i <= n; i++) {
            if (find(i) != find(n - i + 1)) {
                possible = false;
                break;
            }
        }

        System.out.println(possible ? "Yes" : "No");
    }

    static int find(int x) {
        while (x != parent[x]) {
            parent[x] = parent[parent[x]];
            x = parent[x];
        }
        return x;
    }

    static void union(int a, int b) {
        parent[find(a)] = find(b);
    }
}
