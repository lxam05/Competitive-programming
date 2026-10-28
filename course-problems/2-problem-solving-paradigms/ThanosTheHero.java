/*
 * Kattis: thanosthehero
 * https://open.kattis.com/problems/thanosthehero
 * Course: COMP30970, Week 3: Problem Solving Paradigms
 * Topic: Greedy from the right
 * Solution: written for this set
 *
 * Question (summary in my own words; read the full statement on Kattis):
 * Thanos wants world populations to be strictly increasing from first to
 * last, and can only kill people. Print the minimum number killed, or 1 if
 * it is impossible.
 * Input: n, then the n populations.
 */
import java.io.*;
import java.util.*;

public class ThanosTheHero {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine().trim());
        long[] pop = new long[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) pop[i] = Long.parseLong(st.nextToken());

        /*
         * Populations must be strictly increasing, and Thanos can only
         * remove people. Never touch the last world. Go right to left:
         * each world may have at most (next world - 1) people.
         * If that would be negative, it can't be done.
         */
        long killed = 0;
        boolean possible = true;

        for (int i = n - 2; i >= 0; i--) {
            long allowed = pop[i + 1] - 1;
            if (allowed < 0) {
                possible = false;
                break;
            }
            if (pop[i] > allowed) {
                killed += pop[i] - allowed;
                pop[i] = allowed;
            }
        }

        // the problem asks for 1 when it's impossible
        System.out.println(possible ? killed : 1);
    }
}
