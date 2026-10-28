/*
 * Kattis: cd
 * https://open.kattis.com/problems/cd
 * Course: COMP30970, Week 1: Data Structures
 * Topic: Two pointers / HashSet
 * Solution: your own (from kattis/week1/cd/Main.java)
 *
 * Question (summary in my own words; read the full statement on Kattis):
 * Jack and Jill each list their CD catalogue numbers (sorted). Print how many
 * CDs they both own, for each case. Input ends with "0 0".
 */
import java.io.*;

public class CD {
    public static void main(String[] args) throws Exception {
        FastScanner in = new FastScanner(System.in);
        StringBuilder answer = new StringBuilder();

        while (true) {
            int n = in.nextInt();
            int m = in.nextInt();
            if (n == 0 && m == 0) {
                break;
            }

            int[] jack = new int[n];
            for (int i = 0; i < n; i++) {
                jack[i] = in.nextInt();
            }

            int position = 0;
            int shared = 0;
            for (int i = 0; i < m; i++) {
                int cd = in.nextInt();
                while (position < n && jack[position] < cd) {
                    position++;
                }
                if (position < n && jack[position] == cd) {
                    shared++;
                    position++;
                }
            }
            answer.append(shared).append('\n');
        }

        System.out.print(answer);
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
