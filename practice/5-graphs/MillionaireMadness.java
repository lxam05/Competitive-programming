/*
 * Kattis: millionairemadness
 * https://open.kattis.com/problems/millionairemadness
 * Topic: Dijkstra on max edge (minimax)
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * A grid of coin-pile heights. Walk from the top-left to the bottom-right
 * moving up/down/left/right. Going down is free; going up needs a ladder
 * as long as the height difference. Find the shortest ladder that works
 * for the whole route.
 * Input: M N, then the grid.
 * Output: the minimum ladder length.
 */
import java.io.*;
import java.util.*;

public class MillionaireMadness {
    static BufferedReader br;
    static StringTokenizer st;

    static String next() throws IOException {
        while (st == null || !st.hasMoreTokens()) st = new StringTokenizer(br.readLine());
        return st.nextToken();
    }

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));

        int rows = Integer.parseInt(next());
        int cols = Integer.parseInt(next());
        int[][] height = new int[rows][cols];
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                height[r][c] = Integer.parseInt(next());

        /*
         * Moving from a to b needs a ladder of max(0, h[b] - h[a]).
         * We want the path where the BIGGEST ladder is smallest.
         * Dijkstra still works if "distance" = max ladder on the path.
         */
        int[][] need = new int[rows][cols];
        for (int[] row : need) Arrays.fill(row, Integer.MAX_VALUE);
        need[0][0] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        pq.add(new int[]{0, 0, 0}); // {ladder, row, col}

        int[] dr = {1, -1, 0, 0};
        int[] dc = {0, 0, 1, -1};

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int r = cur[1], c = cur[2];
            if (cur[0] > need[r][c]) continue;
            if (r == rows - 1 && c == cols - 1) break;

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d], nc = c + dc[d];
                if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) continue;

                int climb = Math.max(0, height[nr][nc] - height[r][c]);
                int ladder = Math.max(cur[0], climb);
                if (ladder < need[nr][nc]) {
                    need[nr][nc] = ladder;
                    pq.add(new int[]{ladder, nr, nc});
                }
            }
        }

        System.out.println(need[rows - 1][cols - 1]);
    }
}
