/*
 * Kattis: virtualfriends
 * https://open.kattis.com/problems/virtualfriends
 * Topic: Union-Find + HashMap
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * Friendships are formed one at a time between people given by name.
 * After each new friendship, print how many people are in the friend
 * network of the two people who just became friends.
 * Input: T test cases. Each: F, then F lines "name1 name2".
 * Output: F numbers per test case, one per line.
 */
import java.io.*;
import java.util.*;

public class VirtualFriends {
    static int[] parent;
    static int[] size;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder output = new StringBuilder();

        int tests = Integer.parseInt(br.readLine().trim());

        while (tests-- > 0) {
            int f = Integer.parseInt(br.readLine().trim());

            // at most 2 new names per line
            parent = new int[2 * f];
            size = new int[2 * f];

            // give every name a number the first time we see it
            HashMap<String, Integer> ids = new HashMap<>();

            for (int i = 0; i < f; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int a = getId(ids, st.nextToken());
                int b = getId(ids, st.nextToken());

                union(a, b);

                // size of the network a is now in
                output.append(size[find(a)]).append('\n');
            }
        }

        System.out.print(output);
    }

    static int getId(HashMap<String, Integer> ids, String name) {
        if (!ids.containsKey(name)) {
            int id = ids.size();
            ids.put(name, id);
            parent[id] = id;
            size[id] = 1;
        }
        return ids.get(name);
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

        // attach smaller set under bigger set
        if (size[rootA] < size[rootB]) {
            int temp = rootA;
            rootA = rootB;
            rootB = temp;
        }
        parent[rootB] = rootA;
        size[rootA] += size[rootB];
    }
}
