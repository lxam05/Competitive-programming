/*
 * Kattis: unionfind
 * https://open.kattis.com/problems/unionfind
 * Course: COMP30970, Week 2: Data Structures
 * Topic: Union-Find
 * Solution: your own (from kattis/week2/unionfind/Main.java)
 *
 * Question (summary in my own words; read the full statement on Kattis):
 * N elements, Q operations: "= a b" joins the sets of a and b, "? a b" asks
 * whether a and b are in the same set (print yes or no).
 */
import java.io.*;

public class UnionFind {
    static int[] parent;
    static int[] size;

    public static void main(String[] args) throws Exception {
        FastScanner in = new FastScanner(System.in);
        int n = in.nextInt();
        int q = in.nextInt();
        parent = new int[n];
        size = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }

        StringBuilder answer = new StringBuilder();
        for (int i = 0; i < q; i++) {
            char operation = in.nextChar();
            int a = in.nextInt();
            int b = in.nextInt();

            if (operation == '=') {
                union(a, b);
            } else {
                answer.append(find(a) == find(b) ? "yes\n" : "no\n");
            }
        }

        System.out.print(answer);
    }

    static int find(int value) {
        while (value != parent[value]) {
            parent[value] = parent[parent[value]];
            value = parent[value];
        }
        return value;
    }

    static void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);
        if (rootA == rootB) {
            return;
        }
        if (size[rootA] < size[rootB]) {
            int temporary = rootA;
            rootA = rootB;
            rootB = temporary;
        }
        parent[rootB] = rootA;
        size[rootA] += size[rootB];
    }

    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int position;
        private int length;

        FastScanner(InputStream in) {
            this.in = in;
        }

        private int read() throws IOException {
            if (position >= length) {
                length = in.read(buffer);
                position = 0;
                if (length == -1) {
                    return -1;
                }
            }
            return buffer[position++];
        }

        char nextChar() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= 32);
            return (char) c;
        }

        int nextInt() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= 32);
            int value = 0;
            while (c > 32) {
                value = value * 10 + c - '0';
                c = read();
            }
            return value;
        }
    }
}
