/*
 * Kattis: amoebas
 * https://open.kattis.com/problems/amoebas
 * Course: COMP30970, Week 6: Graphs 1
 * Topic: Flood fill (8 directions)
 * Solution: your own (from kattis/ShebaSAmoebas.java)
 *
 * Question (summary in my own words; read the full statement on Kattis):
 * A grid of '#' (amoeba) and '.' cells. Count the amoebas (loops), where
 * cells touching in any of the 8 directions belong to the same loop.
 */
import java.util.*;

public class ShebasAmoebas {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int m = sc.nextInt();
        int n = sc.nextInt();
        sc.nextLine();

        String[][] amoeba = new String[m][n];

        for (int i = 0; i < m; i++) {
            String[] row = sc.nextLine().split("");

            for (int j = 0; j < n; j++) {
                amoeba[i][j] = row[j];
            }
        }

        int[][] visited = new int[m][n];
        int counter = 0;

        // All 8 directions
        int[][] directions = {
            {-1, 0},   // North
            {-1, 1},   // North-East
            {0, 1},    // East
            {1, 1},    // South-East
            {1, 0},    // South
            {1, -1},   // South-West
            {0, -1},   // West
            {-1, -1}   // North-West
        };

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (amoeba[i][j].equals("#") && visited[i][j] == 0) {

                    counter++;

                    Stack<int[]> stack = new Stack<>();

                    stack.push(new int[]{i, j});
                    visited[i][j] = 1;

                    while (!stack.isEmpty()) {

                        int[] current = stack.pop();

                        int a = current[0];
                        int b = current[1];

                        // Check all 8 neighbours
                        for (int[] direction : directions) {

                            int newA = a + direction[0];
                            int newB = b + direction[1];

                            if (newA >= 0 && newA < m &&
                                newB >= 0 && newB < n &&
                                amoeba[newA][newB].equals("#") &&
                                visited[newA][newB] == 0) {

                                visited[newA][newB] = 1;

                                stack.push(new int[]{newA, newB});
                            }
                        }
                    }
                }
            }
        }

        System.out.println(counter);
        sc.close();
    }
}