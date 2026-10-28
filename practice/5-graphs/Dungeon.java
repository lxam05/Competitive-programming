/*
 * Kattis: dungeon
 * https://open.kattis.com/problems/dungeon
 * Topic: 3D BFS
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * A 3D dungeon of L levels, each R x C. '#' is rock, '.' is open,
 * S is the start and E is the exit. Each minute you move one cell
 * north/south/east/west/up/down. Find the quickest escape.
 * Input: several dungeons "L R C" + the levels, ending with "0 0 0".
 * Output: "Escaped in x minute(s)." or "Trapped!".
 */
import java.io.*;
import java.util.*;

public class Dungeon {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder output = new StringBuilder();

        while (true) {
            String line = br.readLine();
            if (line == null) break;
            if (line.trim().isEmpty()) continue;

            StringTokenizer st = new StringTokenizer(line);
            int levels = Integer.parseInt(st.nextToken());
            int rows = Integer.parseInt(st.nextToken());
            int cols = Integer.parseInt(st.nextToken());
            if (levels == 0 && rows == 0 && cols == 0) break;

            char[][][] map = new char[levels][rows][];
            int[] start = null;

            for (int l = 0; l < levels; l++) {
                for (int r = 0; r < rows; r++) {
                    line = br.readLine();
                    while (line.trim().isEmpty()) line = br.readLine(); // blank lines between levels
                    map[l][r] = line.trim().toCharArray();
                    for (int c = 0; c < cols; c++) {
                        if (map[l][r][c] == 'S') start = new int[]{l, r, c};
                    }
                }
            }

            // BFS in 3D: 6 directions (N S E W up down)
            int[] dl = {1, -1, 0, 0, 0, 0};
            int[] dr = {0, 0, 1, -1, 0, 0};
            int[] dc = {0, 0, 0, 0, 1, -1};

            int[][][] dist = new int[levels][rows][cols];
            for (int[][] a : dist) for (int[] b : a) Arrays.fill(b, -1);
            dist[start[0]][start[1]][start[2]] = 0;

            ArrayDeque<int[]> queue = new ArrayDeque<>();
            queue.add(start);
            int answer = -1;

            while (!queue.isEmpty()) {
                int[] cur = queue.poll();
                int l = cur[0], r = cur[1], c = cur[2];

                if (map[l][r][c] == 'E') {
                    answer = dist[l][r][c];
                    break;
                }

                for (int d = 0; d < 6; d++) {
                    int nl = l + dl[d], nr = r + dr[d], nc = c + dc[d];
                    if (nl < 0 || nl >= levels || nr < 0 || nr >= rows || nc < 0 || nc >= cols) continue;
                    if (map[nl][nr][nc] == '#' || dist[nl][nr][nc] != -1) continue;

                    dist[nl][nr][nc] = dist[l][r][c] + 1;
                    queue.add(new int[]{nl, nr, nc});
                }
            }

            if (answer == -1) output.append("Trapped!\n");
            else output.append("Escaped in ").append(answer).append(" minute(s).\n");
        }

        System.out.print(output);
    }
}
