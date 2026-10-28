/*
 * Kattis: ferryloading5
 * https://open.kattis.com/problems/ferryloading5
 * Course: COMP30970, Week 4: Greedy Algorithms
 * Topic: Subset sum
 * Solution: your own (from kattis/FerryLoadingV.java)
 *
 * Question (summary in my own words; read the full statement on Kattis):
 * (Summary based on your solution.) Split the cars between the two sides of
 * the ferry so the weights are as balanced as possible. Print the car
 * numbers that go on one side. Input cases end with 0.
 */
import java.io.*;
import java.util.*;

public class FerryLoadingV {

    static class FastScanner {

        private final InputStream in = System.in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0;
        private int len = 0;

        private int read() throws IOException {

            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;

                if (len <= 0) {
                    return -1;
                }
            }

            return buffer[ptr++];
        }

        String next() throws IOException {

            StringBuilder sb = new StringBuilder();

            int c;

            do {
                c = read();
            } while (c <= ' ' && c != -1);

            while (c > ' ') {
                sb.append((char) c);
                c = read();
            }

            return sb.toString();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }
    }

    public static void main(String[] args) throws Exception {

        FastScanner sc = new FastScanner();
        StringBuilder output = new StringBuilder();

        while (true) {

            int n = sc.nextInt();

            if (n == 0) {
                break;
            }

            double[] weights = new double[n];

            double totalWeight = 0;

            for (int i = 0; i < n; i++) {

                weights[i] = sc.nextDouble();

                totalWeight += weights[i];
            }

            /*
             * Instead of multiplying the actual weights by 10/100/1000,
             * convert each weight into a proportion of the total.
             *
             * Total scaled weight will be about 100000.
             */
            int SCALE = 100000;

            int[] scaled = new int[n];

            int totalScaled = 0;

            for (int i = 0; i < n; i++) {

                scaled[i] = (int) Math.round(
                        weights[i] / totalWeight * SCALE
                );

                totalScaled += scaled[i];
            }

            int target = totalScaled / 2;

            boolean[] possible = new boolean[target + 1];

            int[] previous = new int[target + 1];
            int[] usedCar = new int[target + 1];

            Arrays.fill(usedCar, -1);

            possible[0] = true;

            for (int i = 0; i < n; i++) {

                int weight = scaled[i];

                /*
                 * If it rounded to 0, it makes essentially
                 * no difference to the balance.
                 */
                if (weight == 0) {
                    continue;
                }

                for (int j = target; j >= weight; j--) {

                    if (!possible[j] && possible[j - weight]) {

                        possible[j] = true;

                        previous[j] = j - weight;

                        usedCar[j] = i;
                    }
                }
            }

            /*
             * Find the closest possible weight to half.
             */
            int best = target;

            while (best > 0 && !possible[best]) {
                best--;
            }

            ArrayList<Integer> answer = new ArrayList<>();

            int current = best;

            while (current > 0) {

                int car = usedCar[current];

                answer.add(car + 1);

                current = previous[current];
            }

            Collections.sort(answer);

            for (int i = 0; i < answer.size(); i++) {

                if (i > 0) {
                    output.append(" ");
                }

                output.append(answer.get(i));
            }

            output.append("\n");
        }

        System.out.print(output);
    }
}