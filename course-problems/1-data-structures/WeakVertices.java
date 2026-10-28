/*
 * Kattis: weakvertices
 * https://open.kattis.com/problems/weakvertices
 * Course: COMP30970, Week 1: Data Structures
 * Topic: Adjacency matrix
 * Solution: your own (from kattis/week1/weakvertices/Main.java)
 *
 * Question (summary in my own words; read the full statement on Kattis):
 * Graphs given as adjacency matrices (input ends with -1). A vertex is weak
 * if it is not part of any triangle. Print the weak vertices of each graph
 * in increasing order, one line per graph.
 */
import java.io.*;
import java.util.*;

public class WeakVertices {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new BufferedInputStream(System.in));
        StringBuilder answer = new StringBuilder();

        while (in.hasNextInt()) {
            int n = in.nextInt();
            if (n == -1) {
                break;
            }

            int[][] graph = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    graph[i][j] = in.nextInt();
                }
            }

            for (int i = 0; i < n; i++) {
                boolean weak = true;
                for (int j = 0; j < n && weak; j++) {
                    if (graph[i][j] == 0) {
                        continue;
                    }
                    for (int k = j + 1; k < n; k++) {
                        if (graph[i][k] == 1 && graph[j][k] == 1) {
                            weak = false;
                            break;
                        }
                    }
                }
                if (weak) {
                    answer.append(i).append(' ');
                }
            }
            if (answer.length() > 0 && answer.charAt(answer.length() - 1) == ' ') {
                answer.setLength(answer.length() - 1);
            }
            answer.append('\n');
        }

        System.out.print(answer);
    }
}
