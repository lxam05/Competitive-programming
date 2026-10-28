/*
 * Kattis: supercomputer
 * https://open.kattis.com/problems/supercomputer
 * Topic: Fenwick tree (BIT)
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * N bits, all 0. "F i" flips bit i; "C l r" asks how many bits in
 * positions l..r (inclusive, 1-indexed) are 1.
 * Input: N K, then K operations.
 * Output: the answer to each C query.
 */
import java.io.*;
import java.util.*;

public class Supercomputer {
    static int[] tree;
    static int n;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder output = new StringBuilder();

        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        tree = new int[n + 1];
        boolean[] on = new boolean[n + 1];

        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            String op = st.nextToken();

            if (op.equals("F")) {
                int index = Integer.parseInt(st.nextToken());
                // flip: +1 if it turns on, -1 if it turns off
                if (on[index]) add(index, -1);
                else add(index, 1);
                on[index] = !on[index];
            } else {
                int l = Integer.parseInt(st.nextToken());
                int r = Integer.parseInt(st.nextToken());
                output.append(sum(r) - sum(l - 1)).append('\n');
            }
        }

        System.out.print(output);
    }

    static void add(int i, int delta) {
        while (i <= n) {
            tree[i] += delta;
            i += i & (-i);
        }
    }

    static int sum(int i) {
        int total = 0;
        while (i > 0) {
            total += tree[i];
            i -= i & (-i);
        }
        return total;
    }
}
