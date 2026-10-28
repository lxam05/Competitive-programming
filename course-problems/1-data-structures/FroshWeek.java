/*
 * Kattis: froshweek
 * https://open.kattis.com/problems/froshweek
 * Course: COMP30970, Week 2: Data Structures
 * Topic: Merge sort inversion count
 * Solution: your own (from kattis/week2/froshweek/Main.java)
 *
 * Question (summary in my own words; read the full statement on Kattis):
 * Students stand in a line with distinct numbers. Count the swaps of
 * neighbouring students needed to sort the line (the number of inversions).
 */
import java.io.*;

public class FroshWeek {
    static int[] values;
    static int[] temporary;

    public static void main(String[] args) throws Exception {
        FastScanner in = new FastScanner(System.in);
        int n = in.nextInt();
        values = new int[n];
        temporary = new int[n];

        for (int i = 0; i < n; i++) {
            values[i] = in.nextInt();
        }

        System.out.println(countSwaps(0, n));
    }

    static long countSwaps(int left, int right) {
        if (right - left <= 1) {
            return 0;
        }

        int middle = (left + right) / 2;
        long swaps = countSwaps(left, middle) + countSwaps(middle, right);
        int i = left;
        int j = middle;
        int position = left;

        while (i < middle && j < right) {
            if (values[i] < values[j]) {
                temporary[position++] = values[i++];
            } else {
                temporary[position++] = values[j++];
                swaps += middle - i;
            }
        }

        while (i < middle) temporary[position++] = values[i++];
        while (j < right) temporary[position++] = values[j++];
        for (int k = left; k < right; k++) values[k] = temporary[k];
        return swaps;
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
