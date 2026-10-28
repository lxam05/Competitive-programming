/*
 * Kattis: flipfive
 * https://open.kattis.com/problems/flipfive
 * Course: COMP30970, Week 6: Graphs 1
 * Topic: BFS over states (bitmask)
 * Solution: written for this set
 *
 * Question (summary in my own words; read the full statement on Kattis):
 * A 3x3 grid starts all white. Clicking a cell flips it and its up/down/
 * left/right neighbours. Print the fewest clicks to reach the given pattern
 * ('*' = black, '.' = white), for each case.
 */
import java.io.*;
import java.util.*;

public class FlipFive {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder output = new StringBuilder();

        /*
         * The board is 9 cells, so a whole board fits in 9 bits.
         * flip[c] = the bits that change when you click cell c
         * (the cell itself plus its up/down/left/right neighbours).
         */
        int[] flip = new int[9];
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                int mask = 1 << (r * 3 + c);
                if (r > 0) mask |= 1 << ((r - 1) * 3 + c);
                if (r < 2) mask |= 1 << ((r + 1) * 3 + c);
                if (c > 0) mask |= 1 << (r * 3 + c - 1);
                if (c < 2) mask |= 1 << (r * 3 + c + 1);
                flip[r * 3 + c] = mask;
            }
        }

        /*
         * BFS over the 512 possible boards, starting from all white (0).
         * dist[board] = fewest clicks to reach that board.
         * Do it once, then answer every test case by lookup.
         */
        int[] dist = new int[512];
        Arrays.fill(dist, -1);
        dist[0] = 0;
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        queue.add(0);

        while (!queue.isEmpty()) {
            int board = queue.poll();
            for (int c = 0; c < 9; c++) {
                int nextBoard = board ^ flip[c];
                if (dist[nextBoard] == -1) {
                    dist[nextBoard] = dist[board] + 1;
                    queue.add(nextBoard);
                }
            }
        }

        int tests = Integer.parseInt(br.readLine().trim());
        while (tests-- > 0) {
            int target = 0;
            for (int r = 0; r < 3; r++) {
                String row = br.readLine().trim();
                while (row.isEmpty()) row = br.readLine().trim();
                for (int c = 0; c < 3; c++) {
                    if (row.charAt(c) == '*') target |= 1 << (r * 3 + c);
                }
            }
            output.append(dist[target]).append('\n');
        }

        System.out.print(output);
    }
}
