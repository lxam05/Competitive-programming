/*
 * Kattis: wheresmyinternet
 * https://open.kattis.com/problems/wheresmyinternet
 * Topic: Union-Find / BFS
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * N houses, house 1 has internet. M cables each connect two houses.
 * List every house that is not connected to house 1.
 * Input: N M, then M lines "a b".
 * Output: those houses in increasing order, or "Connected".
 */
import java.io.*;
import java.util.*;

public class WheresMyInternet {
    static int[] parent;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        parent = new int[n + 1];
        for (int i = 1; i <= n; i++) parent[i] = i;

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            parent[find(a)] = find(b);
        }

        // every house not in house 1's set has no internet
        StringBuilder output = new StringBuilder();
        int root = find(1);
        for (int i = 2; i <= n; i++) {
            if (find(i) != root) output.append(i).append('\n');
        }

        if (output.length() == 0) System.out.println("Connected");
        else System.out.print(output);
    }

    static int find(int x) {
        while (x != parent[x]) {
            parent[x] = parent[parent[x]];
            x = parent[x];
        }
        return x;
    }
}
