/*
 * Kattis: solitaire
 * https://open.kattis.com/problems/solitaire
 * Course: COMP30970, Week 5: Dynamic Programming
 * Topic: Backtracking / memo
 * Solution: your own (from kattis/solitare.java)
 *
 * Question (summary in my own words; read the full statement on Kattis):
 * Peg solitaire on a small board: a peg jumps over a neighbouring peg into an
 * empty hole and the jumped peg is removed. Find the fewest pegs that can be
 * left and the number of moves to get there, for each board.
 */
import java.util.*;
import java.io.*;

public class Solitaire {

    static HashMap<Long, Integer> dp;
    static ArrayList<long[]> moves = new ArrayList<>();

    static String[] shape = {
        "###...###",
        ".........",
        ".........",
        ".........",
        "###...###"
    };

    static int[][] directions = {
        {0, 1}, {0, -1},
        {1, 0}, {-1, 0}
    };

    // Check whether a position is a valid hole
    static boolean valid(int r, int c) {
        return r >= 0 && r < 5 &&
               c >= 0 && c < 9 &&
               shape[r].charAt(c) != '#';
    }

    // Precompute all possible jump positions
    static void buildMoves() {
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 9; c++) {

                if (!valid(r, c)) continue;

                for (int[] d : directions) {
                    int r1 = r + d[0];
                    int c1 = c + d[1];

                    int r2 = r + 2 * d[0];
                    int c2 = c + 2 * d[1];

                    if (valid(r1, c1) && valid(r2, c2)) {
                        long from = 1L << (r * 9 + c);
                        long middle = 1L << (r1 * 9 + c1);
                        long to = 1L << (r2 * 9 + c2);

                        moves.add(new long[]{from, middle, to});
                    }
                }
            }
        }
    }

    static int solve(long board) {

        if (dp.containsKey(board)) {
            return dp.get(board);
        }

        // If no moves are possible, this is our answer
        int best = Long.bitCount(board);

        // Try every possible jump
        for (long[] move : moves) {
            long from = move[0];
            long middle = move[1];
            long to = move[2];

            // Two pegs must exist, and destination must be empty
            if ((board & from) != 0 &&
                (board & middle) != 0 &&
                (board & to) == 0) {

                // Remove both pegs and place one at destination
                long newBoard = (board ^ from ^ middle) | to;

                best = Math.min(best, solve(newBoard));

                if (best == 1) break;
            }
        }

        dp.put(board, best);
        return best;
    }

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(
            new InputStreamReader(System.in)
        );

        int t = Integer.parseInt(br.readLine().trim());

        buildMoves();

        for (int test = 0; test < t; test++) {

            long board = 0;

            for (int r = 0; r < 5; r++) {
                String line = br.readLine();

                // Skip empty lines between test cases
                while (line != null && line.trim().isEmpty()) {
                    line = br.readLine();
                }

                for (int c = 0; c < 9; c++) {
                    if (line.charAt(c) == 'o') {
                        board |= 1L << (r * 9 + c);
                    }
                }
            }

            dp = new HashMap<>();

            int initial = Long.bitCount(board);
            int remaining = solve(board);

            System.out.println(remaining + " " + (initial - remaining));
        }
    }
}
