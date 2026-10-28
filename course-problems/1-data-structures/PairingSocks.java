/*
 * Kattis: pairingsocks
 * https://open.kattis.com/problems/pairingsocks
 * Course: COMP30970, Week 1: Data Structures
 * Topic: Stack simulation
 * Solution: your own (from kattis/week1/pairingsocks/Main.java)
 *
 * Question (summary in my own words; read the full statement on Kattis):
 * 2n socks are in a pile. Using the original pile and one auxiliary pile,
 * move socks one at a time and remove pairs when the two top socks match.
 * Print the fewest moves needed to pair everything, or "impossible".
 */
import java.io.*;
import java.util.*;

public class PairingSocks {
    public static void main(String[] args) throws Exception {
        FastScanner in = new FastScanner(System.in);
        int n = in.nextInt();
        ArrayDeque<Integer> pile = new ArrayDeque<>();

        for (int i = 0; i < 2 * n; i++) {
            int sock = in.nextInt();
            if (!pile.isEmpty() && pile.peek() == sock) {
                pile.pop();
            } else {
                pile.push(sock);
            }
        }

        if (pile.isEmpty()) {
            System.out.println(2 * n);
        } else {
            System.out.println("impossible");
        }
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
