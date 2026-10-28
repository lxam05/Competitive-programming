/*
 * Kattis: workstations
 * https://open.kattis.com/problems/workstations
 * Topic: Greedy + PriorityQueue
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * Researchers arrive at time a and stay s minutes. A workstation locks
 * itself after m minutes unused, and unlocking is annoying. Assign
 * researchers to stations to save as many unlocks as possible (a
 * station can be reused if it is free and has been idle <= m minutes).
 * Input: n m, then n lines "a s".
 * Output: the maximum number of unlocks saved.
 */
import java.io.*;
import java.util.*;

public class Workstations {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] researchers = new int[n][2]; // {arrive, stay}
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            researchers[i][0] = Integer.parseInt(st.nextToken());
            researchers[i][1] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(researchers, (a, b) -> Integer.compare(a[0], b[0]));

        // times when workstations become free (earliest first)
        PriorityQueue<Integer> freeAt = new PriorityQueue<>();
        int unlocksSaved = 0;

        for (int i = 0; i < n; i++) {
            int arrive = researchers[i][0];
            int leave = arrive + researchers[i][1];

            // throw away stations that already locked themselves
            while (!freeAt.isEmpty() && freeAt.peek() + m < arrive) {
                freeAt.poll();
            }

            // a station is free now and still unlocked -> reuse it
            if (!freeAt.isEmpty() && freeAt.peek() <= arrive) {
                freeAt.poll();
                unlocksSaved++;
            }

            freeAt.add(leave);
        }

        System.out.println(unlocksSaved);
    }
}
