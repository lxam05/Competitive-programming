/*
 * Kattis: fenwick
 * https://open.kattis.com/problems/fenwick
 * Topic: Fenwick tree (BIT)
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * An array of N zeros. Operations: "+ i d" adds d to a[i];
 * "? i" asks for the sum a[0] + ... + a[i-1].
 * Input: N Q, then Q operations.
 * Output: the answer to each query.
 */
import java.io.*;
import java.util.*;

public class Fenwick {
    static long[] tree;
    static int n;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder output = new StringBuilder();

        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        tree = new long[n + 1]; // 1-indexed inside the tree

        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            String op = st.nextToken();

            if (op.equals("+")) {
                int index = Integer.parseInt(st.nextToken());
                long delta = Long.parseLong(st.nextToken());
                add(index + 1, delta); // problem index is 0-based
            } else {
                int index = Integer.parseInt(st.nextToken());
                // sum of a[0..index-1] = tree prefix up to position index
                output.append(sum(index)).append('\n');
            }
        }

        System.out.print(output);
    }

    static void add(int i, long delta) {
        while (i <= n) {
            tree[i] += delta;
            i += i & (-i);
        }
    }

    // sum of positions 1..i
    static long sum(int i) {
        long total = 0;
        while (i > 0) {
            total += tree[i];
            i -= i & (-i);
        }
        return total;
    }
}
