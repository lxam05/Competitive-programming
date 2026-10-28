/*
 * Kattis: closestsums
 * https://open.kattis.com/problems/closestsums
 * Topic: Complete search
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * Given n numbers and m queries, for each query value
 * find the sum of two different numbers from the set that is closest
 * to it.
 * Input: several cases until end of file. Each: n, n numbers, m, m queries.
 * Output: "Case k:" then "Closest sum to Q is S." for each query.
 */
import java.io.*;
import java.util.*;

public class ClosestSums {
    static BufferedReader br;
    static StringTokenizer st;

    // returns null at end of input
    static String next() throws IOException {
        while (st == null || !st.hasMoreTokens()) {
            String line = br.readLine();
            if (line == null) return null;
            st = new StringTokenizer(line);
        }
        return st.nextToken();
    }

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder output = new StringBuilder();
        int caseNumber = 1;

        String token;
        while ((token = next()) != null) {
            int n = Integer.parseInt(token);
            int[] numbers = new int[n];
            for (int i = 0; i < n; i++) numbers[i] = Integer.parseInt(next());

            output.append("Case ").append(caseNumber++).append(":\n");

            int m = Integer.parseInt(next());
            for (int q = 0; q < m; q++) {
                int target = Integer.parseInt(next());

                // brute force: try every pair of different numbers (n <= 1000)
                int best = numbers[0] + numbers[1];
                for (int i = 0; i < n; i++) {
                    for (int j = i + 1; j < n; j++) {
                        int sum = numbers[i] + numbers[j];
                        if (Math.abs(sum - target) < Math.abs(best - target)) {
                            best = sum;
                        }
                    }
                }

                output.append("Closest sum to ").append(target)
                      .append(" is ").append(best).append(".\n");
            }
        }

        System.out.print(output);
    }
}
