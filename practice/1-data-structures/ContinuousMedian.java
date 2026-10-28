/*
 * Kattis: continuousmedian
 * https://open.kattis.com/problems/continuousmedian
 * Topic: Two heaps
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * For a sequence of numbers, after reading each number take the median
 * of everything so far (even count: average of the two middle values,
 * rounded down). Print the sum of all these medians.
 * Input: T test cases. Each: N, then N integers.
 * Output: one sum per test case.
 */
import java.io.*;
import java.util.*;

public class ContinuousMedian {
    static BufferedReader br;
    static StringTokenizer st;

    static String next() throws IOException {
        while (st == null || !st.hasMoreTokens()) st = new StringTokenizer(br.readLine());
        return st.nextToken();
    }

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder output = new StringBuilder();

        int tests = Integer.parseInt(next());

        while (tests-- > 0) {
            int n = Integer.parseInt(next());

            // small = lower half (max-heap), big = upper half (min-heap)
            PriorityQueue<Integer> small = new PriorityQueue<>(Collections.reverseOrder());
            PriorityQueue<Integer> big = new PriorityQueue<>();
            long sum = 0;

            for (int i = 0; i < n; i++) {
                int x = Integer.parseInt(next());

                if (small.isEmpty() || x <= small.peek()) {
                    small.add(x);
                } else {
                    big.add(x);
                }

                // keep sizes balanced: small has same size or one more
                if (small.size() > big.size() + 1) {
                    big.add(small.poll());
                } else if (big.size() > small.size()) {
                    small.add(big.poll());
                }

                if (small.size() > big.size()) {
                    // odd count: middle element
                    sum += small.peek();
                } else {
                    // even count: average of the two middles, rounded down
                    sum += ((long) small.peek() + big.peek()) / 2;
                }
            }

            output.append(sum).append('\n');
        }

        System.out.print(output);
    }
}
