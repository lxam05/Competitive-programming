/*
 * Kattis: natjecanje
 * https://open.kattis.com/problems/natjecanje
 * Topic: Brute force / greedy
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * N kayak teams in a row. Some have damaged kayaks, some have a spare.
 * A team with a spare can lend it to itself or a direct neighbour
 * (one spare = one kayak). How many teams still can't race?
 * Input: N S R, then the S damaged teams, then the R teams with spares.
 * Output: the minimum number of teams that can't start.
 */
import java.io.*;
import java.util.*;

public class Natjecanje {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());

        // extra spot at each end so i-1 and i+1 never go out of bounds
        boolean[] damaged = new boolean[n + 2];
        boolean[] reserve = new boolean[n + 2];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < s; i++) damaged[Integer.parseInt(st.nextToken())] = true;

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < r; i++) reserve[Integer.parseInt(st.nextToken())] = true;

        // a team with a damaged kayak AND a reserve just uses its own
        for (int i = 1; i <= n; i++) {
            if (damaged[i] && reserve[i]) {
                damaged[i] = false;
                reserve[i] = false;
            }
        }

        /*
         * Greedy left to right: borrow from the left neighbour first
         * (nobody further right can use that kayak anyway),
         * otherwise borrow from the right neighbour.
         */
        int cannotStart = 0;
        for (int i = 1; i <= n; i++) {
            if (!damaged[i]) continue;

            if (reserve[i - 1]) {
                reserve[i - 1] = false;
            } else if (reserve[i + 1]) {
                reserve[i + 1] = false;
            } else {
                cannotStart++;
            }
        }

        System.out.println(cannotStart);
    }
}
