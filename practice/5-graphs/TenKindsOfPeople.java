/*
 * Kattis: 10kindsofpeople
 * https://open.kattis.com/problems/10kindsofpeople
 * Topic: Flood fill / connected components
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * A map of 0s (binary people) and 1s (decimal people). Each kind can only
 * walk on its own digit, moving up/down/left/right. For each query, can
 * someone get from cell A to cell B?
 * Input: r c, the map, n, then n queries "r1 c1 r2 c2" (1-indexed).
 * Output: binary, decimal or neither for each query.
 */
import java.io.*;
import java.util.*;

// Kattis id: 10kindsofpeople (class names can't start with a digit)
public class TenKindsOfPeople {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder output = new StringBuilder();

        StringTokenizer st = new StringTokenizer(br.readLine());
        int rows = Integer.parseInt(st.nextToken());
        int cols = Integer.parseInt(st.nextToken());

        char[][] map = new char[rows][];
        for (int r = 0; r < rows; r++) map[r] = br.readLine().toCharArray();

        /*
         * Flood fill once: give every cell a component number.
         * Then each query is just "same component?" -> O(1).
         * (Doing a BFS per query is too slow.)
         */
        int[][] comp = new int[rows][cols];
        for (int[] row : comp) Arrays.fill(row, -1);

        int[] dr = {1, -1, 0, 0};
        int[] dc = {0, 0, 1, -1};
        int compCount = 0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (comp[r][c] != -1) continue;

                // BFS from (r, c) over cells with the same digit
                ArrayDeque<int[]> queue = new ArrayDeque<>();
                queue.add(new int[]{r, c});
                comp[r][c] = compCount;

                while (!queue.isEmpty()) {
                    int[] cur = queue.poll();
                    for (int d = 0; d < 4; d++) {
                        int nr = cur[0] + dr[d], nc = cur[1] + dc[d];
                        if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) continue;
                        if (comp[nr][nc] != -1 || map[nr][nc] != map[r][c]) continue;
                        comp[nr][nc] = compCount;
                        queue.add(new int[]{nr, nc});
                    }
                }
                compCount++;
            }
        }

        int q = Integer.parseInt(br.readLine().trim());
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int r1 = Integer.parseInt(st.nextToken()) - 1;
            int c1 = Integer.parseInt(st.nextToken()) - 1;
            int r2 = Integer.parseInt(st.nextToken()) - 1;
            int c2 = Integer.parseInt(st.nextToken()) - 1;

            if (comp[r1][c1] != comp[r2][c2]) output.append("neither\n");
            else if (map[r1][c1] == '0') output.append("binary\n");
            else output.append("decimal\n");
        }

        System.out.print(output);
    }
}
