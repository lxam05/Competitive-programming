/*
 * Kattis: intervalcover
 * https://open.kattis.com/problems/intervalcover
 * Topic: Greedy
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * Cover the interval [A, B] with as few of the given intervals as
 * possible. Print how many and which ones (0-indexed, any order), or
 * "impossible".
 * Input: several cases until end of file: "A B", n, n lines "a b".
 * Output: count + indices, or impossible.
 */
import java.io.*;
import java.util.*;

public class IntervalCover {
    static BufferedReader br;
    static StringTokenizer st;

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

        String token;
        while ((token = next()) != null) {
            double a = Double.parseDouble(token);
            double b = Double.parseDouble(next());
            int n = Integer.parseInt(next());

            double[] left = new double[n];
            double[] right = new double[n];
            Integer[] order = new Integer[n];
            for (int i = 0; i < n; i++) {
                left[i] = Double.parseDouble(next());
                right[i] = Double.parseDouble(next());
                order[i] = i;
            }

            // sort interval indices by left end
            Arrays.sort(order, (x, y) -> Double.compare(left[x], left[y]));

            /*
             * Same greedy as Watering Grass:
             * from everything starting at or before "covered",
             * pick the one that reaches furthest right.
             */
            ArrayList<Integer> chosen = new ArrayList<>();
            double covered = a;
            int index = 0;
            boolean possible = true;
            boolean first = true;

            while (first || covered < b) {
                double furthest = -Double.MAX_VALUE;
                int pick = -1;

                while (index < n && left[order[index]] <= covered) {
                    if (right[order[index]] > furthest) {
                        furthest = right[order[index]];
                        pick = order[index];
                    }
                    index++;
                }

                // nothing reaches "covered", or nothing moves us forward
                if (pick == -1 || furthest < covered || (!first && furthest == covered)) {
                    possible = false;
                    break;
                }

                chosen.add(pick);
                covered = furthest;
                first = false;
            }

            if (!possible) {
                output.append("impossible\n");
            } else {
                output.append(chosen.size()).append('\n');
                for (int i = 0; i < chosen.size(); i++) {
                    if (i > 0) output.append(' ');
                    output.append(chosen.get(i));
                }
                output.append('\n');
            }
        }

        System.out.print(output);
    }
}
