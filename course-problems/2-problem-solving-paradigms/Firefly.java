/*
 * Kattis: firefly
 * https://open.kattis.com/problems/firefly
 * Course: COMP30970, Week 3: Problem Solving Paradigms
 * Topic: Prefix / suffix sums
 * Solution: written for this set
 *
 * Question (summary in my own words; read the full statement on Kattis):
 * A cave of length N and height H has N obstacles that alternate between
 * stalagmites (from the floor) and stalactites (from the ceiling). A firefly
 * flies straight at one level and destroys every obstacle in its way.
 * Print the fewest obstacles it must destroy and how many levels achieve it.
 * Input: N H, then N sizes.
 */
import java.io.*;
import java.util.*;

public class Firefly {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int h = Integer.parseInt(st.nextToken());

        /*
         * floor[k]   = number of stalagmites with height exactly k
         * ceiling[k] = number of stalactites with length exactly k
         * Obstacles alternate: first from the floor, then from the ceiling.
         */
        int[] floor = new int[h + 2];
        int[] ceiling = new int[h + 2];
        for (int i = 0; i < n; i++) {
            int size = Integer.parseInt(br.readLine().trim());
            if (i % 2 == 0) floor[size]++;
            else ceiling[size]++;
        }

        /*
         * Flying at level L (1..H):
         *  - hits a stalagmite if its height >= L
         *  - hits a stalactite if its length >= H - L + 1
         * Build "how many are at least k tall" with suffix sums.
         */
        int[] floorAtLeast = new int[h + 2];
        int[] ceilingAtLeast = new int[h + 2];
        for (int k = h; k >= 1; k--) {
            floorAtLeast[k] = floorAtLeast[k + 1] + floor[k];
            ceilingAtLeast[k] = ceilingAtLeast[k + 1] + ceiling[k];
        }

        int best = Integer.MAX_VALUE;
        int levels = 0;
        for (int level = 1; level <= h; level++) {
            int hits = floorAtLeast[level] + ceilingAtLeast[h - level + 1];
            if (hits < best) {
                best = hits;
                levels = 1;
            } else if (hits == best) {
                levels++;
            }
        }

        System.out.println(best + " " + levels);
    }
}
