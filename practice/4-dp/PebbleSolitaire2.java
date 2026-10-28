/*
 * Kattis: pebblesolitaire2
 * https://open.kattis.com/problems/pebblesolitaire2
 * Topic: DP / memoisation on bitmask
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * A row of 23 cells, 'o' = pebble, '-' = empty. A move: a pebble jumps
 * over an adjacent pebble into an empty cell right after it, and the
 * jumped pebble is removed. Find the fewest pebbles you can end with.
 * Input: n, then n boards.
 * Output: the minimum pebbles left for each board.
 */
import java.io.*;
import java.util.*;

public class PebbleSolitaire2 {
    static final int SIZE = 23;
    static byte[] memo = new byte[1 << SIZE]; // 0 = not worked out yet, else answer + 1

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder output = new StringBuilder();

        int tests = Integer.parseInt(br.readLine().trim());

        while (tests-- > 0) {
            String board = br.readLine().trim();

            // turn the board into a bitmask: bit i set = pebble at i
            int mask = 0;
            for (int i = 0; i < SIZE; i++) {
                if (board.charAt(i) == 'o') mask |= (1 << i);
            }

            output.append(solve(mask)).append('\n');
        }

        System.out.print(output);
    }

    // fewest pebbles we can end with from this board
    static int solve(int mask) {
        if (memo[mask] != 0) return memo[mask] - 1;

        int best = Integer.bitCount(mask); // make no more moves

        for (int i = 0; i < SIZE; i++) {
            if ((mask & (1 << i)) == 0) continue;

            // jump right: i over i+1 into i+2
            if (i + 2 < SIZE && has(mask, i + 1) && !has(mask, i + 2)) {
                int nextMask = mask & ~(1 << i) & ~(1 << (i + 1)) | (1 << (i + 2));
                best = Math.min(best, solve(nextMask));
            }

            // jump left: i over i-1 into i-2
            if (i - 2 >= 0 && has(mask, i - 1) && !has(mask, i - 2)) {
                int nextMask = mask & ~(1 << i) & ~(1 << (i - 1)) | (1 << (i - 2));
                best = Math.min(best, solve(nextMask));
            }
        }

        memo[mask] = (byte) (best + 1);
        return best;
    }

    static boolean has(int mask, int i) {
        return (mask & (1 << i)) != 0;
    }
}
