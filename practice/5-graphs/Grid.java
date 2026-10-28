/*
 * Kattis: grid
 * https://open.kattis.com/problems/grid
 * Topic: BFS
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * An n x m grid of digits. From a cell with digit k you may move exactly
 * k cells up, down, left or right (staying in the grid). Find the fewest
 * moves from the top-left to the bottom-right cell.
 * Input: n m, then n rows of digits.
 * Output: the minimum moves, or -1.
 */
import java.io.*;
import java.util.*;

public class Grid {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] grid = new int[n][m];
        for (int r = 0; r < n; r++) {
            String line = br.readLine();
            for (int c = 0; c < m; c++) grid[r][c] = line.charAt(c) - '0';
        }

        // plain BFS: each move costs 1, jump length = digit in the cell
        int[][] dist = new int[n][m];
        for (int[] row : dist) Arrays.fill(row, -1);
        dist[0][0] = 0;

        ArrayDeque<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{0, 0});

        int[] dr = {1, -1, 0, 0};
        int[] dc = {0, 0, 1, -1};

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int r = cur[0], c = cur[1];
            int k = grid[r][c];

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d] * k;
                int nc = c + dc[d] * k;
                if (nr < 0 || nr >= n || nc < 0 || nc >= m) continue;
                if (dist[nr][nc] != -1) continue;

                dist[nr][nc] = dist[r][c] + 1;
                queue.add(new int[]{nr, nc});
            }
        }

        System.out.println(dist[n - 1][m - 1]);
    }
}
