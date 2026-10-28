/*
 * Kattis: almostunionfind
 * https://open.kattis.com/problems/almostunionfind
 * Course: COMP30970, Week 2: Data Structures
 * Topic: Union-Find with move
 * Solution: your own (from kattis/week2/almostunionfind/Main.java)
 *
 * Question (summary in my own words; read the full statement on Kattis):
 * Sets {1}, {2}, ..., {n}. Commands: "1 p q" union the sets of p and q,
 * "2 p q" move p into q's set, "3 p" print the size and the sum of p's set.
 * Input: several test cases until end of file.
 */
import java.io.*;

public class AlmostUnionFind {
    static int[] parent;
    static int[] size;
    static int[] location;
    static long[] sum;
    static int nextNode;

    public static void main(String[] args) throws Exception {
        FastScanner in = new FastScanner(System.in);
        StringBuilder answer = new StringBuilder();
        Integer n;

        while ((n = in.nextInt()) != null) {
            int m = in.nextInt();
            int capacity = n + m + 1;
            parent = new int[capacity];
            size = new int[capacity];
            sum = new long[capacity];
            location = new int[n + 1];
            nextNode = n + 1;

            for (int i = 1; i <= n; i++) {
                parent[i] = i;
                size[i] = 1;
                sum[i] = i;
                location[i] = i;
            }

            for (int i = 0; i < m; i++) {
                int operation = in.nextInt();
                int p = in.nextInt();

                if (operation == 1) {
                    int q = in.nextInt();
                    union(location[p], location[q]);
                } else if (operation == 2) {
                    int q = in.nextInt();
                    move(p, q);
                } else {
                    int root = find(location[p]);
                    answer.append(size[root]).append(' ').append(sum[root]).append('\n');
                }
            }
        }

        System.out.print(answer);
    }

    static int find(int value) {
        if (parent[value] != value) {
            parent[value] = find(parent[value]);
        }
        return parent[value];
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
        sum[rootA] += sum[rootB];
    }

    static void move(int p, int q) {
        int rootP = find(location[p]);
        int rootQ = find(location[q]);
        if (rootP == rootQ) {
            return;
        }

        size[rootP]--;
        sum[rootP] -= p;
        int newNode = nextNode++;
        location[p] = newNode;
        parent[newNode] = rootQ;
        size[newNode] = 1;
        sum[newNode] = p;
        size[rootQ]++;
        sum[rootQ] += p;
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

        Integer nextInt() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= 32 && c != -1);
            if (c == -1) {
                return null;
            }
            int value = 0;
            while (c > 32 && c != -1) {
                value = value * 10 + c - '0';
                c = read();
            }
            return value;
        }
    }
}
