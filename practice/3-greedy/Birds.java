/*
 * Kattis: birds
 * https://open.kattis.com/problems/birds
 * Topic: Greedy
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * A wire of length l, poles at both ends. Birds must sit at least d cm
 * apart and at least 6 cm from each pole. n birds are already there.
 * How many more birds can sit down?
 * Input: l d n, then n positions.
 * Output: the maximum number of extra birds.
 */
import java.io.*;
import java.util.*;

public class Birds {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        long l = Long.parseLong(st.nextToken());
        long d = Long.parseLong(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        /*
         * Add two pretend birds so the pole rule (6 cm) is automatic:
         * one at 6-d and one at l-6+d. Then every real spot must be
         * at least d away from them, same as from any bird.
         */
        long[] birds = new long[n + 2];
        birds[0] = 6 - d;
        birds[n + 1] = l - 6 + d;
        for (int i = 1; i <= n; i++) {
            birds[i] = Long.parseLong(br.readLine().trim());
        }
        Arrays.sort(birds);

        // in a gap of length g we can squeeze in g/d - 1 more birds
        long extra = 0;
        for (int i = 0; i + 1 < birds.length; i++) {
            long gap = birds[i + 1] - birds[i];
            extra += Math.max(0, gap / d - 1);
        }

        System.out.println(extra);
    }
}
