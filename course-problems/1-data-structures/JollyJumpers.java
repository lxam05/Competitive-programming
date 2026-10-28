/*
 * Kattis: jollyjumpers
 * https://open.kattis.com/problems/jollyjumpers
 * Course: COMP30970, Week 1: Data Structures
 * Topic: Array / boolean set
 * Solution: your own (from kattis/week1/jollyjumpers/Main.java)
 *
 * Question (summary in my own words; read the full statement on Kattis):
 * A sequence of n numbers is jolly if the absolute differences between
 * neighbours take every value from 1 to n-1. Each line is "n a1 ... an".
 * Print "Jolly" or "Not jolly" for each line.
 */
import java.io.*;

public class JollyJumpers {
    public static void main(String[] args) throws Exception {
        FastScanner in = new FastScanner(System.in);
        StringBuilder answer = new StringBuilder();
        Integer n;

        while ((n = in.nextInt()) != null) {
            boolean[] found = new boolean[n];
            int previous = in.nextInt();
            boolean jolly = true;

            for (int i = 1; i < n; i++) {
                int current = in.nextInt();
                int difference = Math.abs(current - previous);
                if (difference <= 0 || difference >= n || found[difference]) {
                    jolly = false;
                } else {
                    found[difference] = true;
                }
                previous = current;
            }

            answer.append(jolly ? "Jolly\n" : "Not jolly\n");
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

        Integer nextInt() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= 32 && c != -1);
            if (c == -1) {
                return null;
            }
            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }
            int value = 0;
            while (c > 32 && c != -1) {
                value = value * 10 + c - '0';
                c = read();
            }
            return value * sign;
        }
    }
}
