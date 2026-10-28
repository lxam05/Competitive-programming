/*
 * Kattis: froshweek2
 * https://open.kattis.com/problems/froshweek2
 * Topic: Greedy (sort + two pointers)
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * n tasks with lengths and m quiet intervals with lengths. Each interval
 * can hold at most one task, which must fit. Maximise tasks done.
 * Input: n m, task lengths, interval lengths.
 * Output: the maximum number of tasks.
 */
import java.io.*;
import java.util.*;

public class FroshWeek2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[] tasks = new int[n];
        int[] gaps = new int[m];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) tasks[i] = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) gaps[i] = Integer.parseInt(st.nextToken());

        Arrays.sort(tasks);
        Arrays.sort(gaps);

        /*
         * Greedy: go through quiet intervals smallest first.
         * Give each one the smallest task that is still left,
         * if it fits. Otherwise skip that interval.
         */
        int task = 0;
        int done = 0;
        for (int g = 0; g < m && task < n; g++) {
            if (tasks[task] <= gaps[g]) {
                done++;
                task++;
            }
        }

        System.out.println(done);
    }
}
