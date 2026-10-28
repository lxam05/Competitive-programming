/*
 * Kattis: zipline
 * https://open.kattis.com/problems/zipline
 * Course: COMP30970, Week 3: Problem Solving Paradigms
 * Topic: Geometry / ternary search
 * Solution: written for this set
 *
 * Question (summary in my own words; read the full statement on Kattis):
 * Two poles w apart with cable heights g and h. A rider hangs r below the
 * cable and must never touch the ground. Print the shortest and longest
 * possible cable length for each case (absolute error 1e-6 allowed).
 * Input: n, then n lines "w g h r".
 */
import java.io.*;
import java.util.*;

public class Zipline {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder output = new StringBuilder();

        int n = Integer.parseInt(br.readLine().trim());

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            double w = Double.parseDouble(st.nextToken());
            double g = Double.parseDouble(st.nextToken());
            double h = Double.parseDouble(st.nextToken());
            double r = Double.parseDouble(st.nextToken());

            // shortest cable: a straight line between the two pole tops
            double shortest = Math.sqrt(w * w + (g - h) * (g - h));

            /*
             * Longest cable: the rider just touches height r somewhere.
             * Lower both poles by r and the cable goes down to the ground
             * and back up. The best touch point (where the cable is
             * shortest = tightest) comes from reflecting one pole:
             * a straight line of length sqrt(w^2 + ((g-r) + (h-r))^2).
             * (You could also ternary search on the touch point.)
             */
            double longest = Math.sqrt(w * w + (g - r + h - r) * (g - r + h - r));

            output.append(String.format(Locale.US, "%.8f %.8f", shortest, longest)).append('\n');
        }

        System.out.print(output);
    }
}
