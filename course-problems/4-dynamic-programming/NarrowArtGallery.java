/*
 * Kattis: narrowartgallery
 * https://open.kattis.com/problems/narrowartgallery
 * Course: COMP30970, Week 5: Dynamic Programming
 * Topic: DP on rows
 * Solution: your own (from kattis/NarrowArtGallery.java)
 *
 * Question (summary in my own words; read the full statement on Kattis):
 * A gallery 2 rooms wide and N rows long, each room with a value. Close
 * exactly k rooms so visitors can still walk through: no row may have both
 * rooms closed, and a closed room can't touch a closed room diagonally in
 * the next row. Maximise the total value of the open rooms.
 */
import java.util.*;

public class NarrowArtGallery {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            int n = sc.nextInt();
            int K = sc.nextInt();

            if (n == 0 && K == 0) {
                break;
            }

            int[] left = new int[n];
            int[] right = new int[n];

            for (int i = 0; i < n; i++) {
                left[i] = sc.nextInt();
                right[i] = sc.nextInt();
            }

            int NEG = -1_000_000_000;

            int[][][] dp = new int[n + 1][K + 1][3];

            // Initialise all states as impossible
            for (int i = 0; i <= n; i++) {
                for (int k = 0; k <= K; k++) {
                    Arrays.fill(dp[i][k], NEG);
                }
            }

            // No rows processed, no rooms closed
            dp[0][0][0] = 0;

            for (int i = 0; i < n; i++) {
                for (int k = 0; k <= K; k++) {
                    for (int s = 0; s < 3; s++) {

                        if (dp[i][k][s] == NEG) {
                            continue;
                        }

                        int current = dp[i][k][s];

                        // Option 1: Keep both rooms open
                        dp[i + 1][k][0] = Math.max(
                            dp[i + 1][k][0],
                            current + left[i] + right[i]
                        );

                        if (k < K) {

                            // Option 2: Close left room
                            // Cannot follow a right closure
                            if (s != 2) {
                                dp[i + 1][k + 1][1] = Math.max(
                                    dp[i + 1][k + 1][1],
                                    current + right[i]
                                );
                            }

                            // Option 3: Close right room
                            // Cannot follow a left closure
                            if (s != 1) {
                                dp[i + 1][k + 1][2] = Math.max(
                                    dp[i + 1][k + 1][2],
                                    current + left[i]
                                );
                            }
                        }
                    }
                }
            }

            int answer = Math.max(
                dp[n][K][0],
                Math.max(dp[n][K][1], dp[n][K][2])
            );

            System.out.println(answer);
        }

        sc.close();
    }
}