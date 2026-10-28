/*
 * Kattis: ballotboxes
 * https://open.kattis.com/problems/ballotboxes
 * Topic: Binary search on the answer
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * N cities with given populations and B ballot boxes (B >= N). Every
 * city gets at least one box, and a city's people are split evenly over
 * its boxes. Minimise the largest number of people assigned to one box.
 * Input: several cases "N B" + N populations, ending with "-1 -1".
 * Output: the minimum possible maximum per case.
 */
import java.io.*;
import java.util.*;

public class BallotBoxes {
    static BufferedReader br;
    static StringTokenizer st;

    static String next() throws IOException {
        while (st == null || !st.hasMoreTokens()) st = new StringTokenizer(br.readLine());
        return st.nextToken();
    }

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder output = new StringBuilder();

        while (true) {
            int n = Integer.parseInt(next());
            int b = Integer.parseInt(next());
            if (n == -1 && b == -1) break;

            int[] people = new int[n];
            int biggest = 0;
            for (int i = 0; i < n; i++) {
                people[i] = Integer.parseInt(next());
                biggest = Math.max(biggest, people[i]);
            }

            /*
             * Binary search on the answer x = max people per box.
             * City with p people needs ceil(p / x) boxes.
             * Bigger x -> fewer boxes needed, so it's monotonic.
             */
            int low = 1, high = biggest;
            while (low < high) {
                int mid = (low + high) / 2;

                long boxesNeeded = 0;
                for (int i = 0; i < n; i++) {
                    boxesNeeded += (people[i] + mid - 1) / mid;
                }

                if (boxesNeeded <= b) high = mid;
                else low = mid + 1;
            }

            output.append(low).append('\n');
        }

        System.out.print(output);
    }
}
