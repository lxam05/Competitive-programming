/*
 * Kattis: server
 * https://open.kattis.com/problems/server
 * Course: COMP30970, Warm-up Exercises
 * Topic: Simulation
 * Solution: written for this set
 *
 * Question (summary in my own words; read the full statement on Kattis):
 * n tasks arrive in order with given run times; the server has T minutes
 * and runs tasks first come, first served. How many tasks finish in time?
 * Input: n T, then the n task times.
 * Output: the number of tasks completed.
 */
import java.io.*;
import java.util.*;

public class Server {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int limit = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());

        // first come first served: keep doing tasks until the next one doesn't fit
        int used = 0;
        int done = 0;
        for (int i = 0; i < n; i++) {
            int task = Integer.parseInt(st.nextToken());
            if (used + task > limit) break;
            used += task;
            done++;
        }

        System.out.println(done);
    }
}
