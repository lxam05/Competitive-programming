/*
 * Kattis: airconditioned
 * https://open.kattis.com/problems/airconditioned
 * Topic: Greedy (interval stabbing)
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * Each minion likes temperatures in [L, U]. Every room is set to one
 * temperature. Put every minion in a room it likes, using as few rooms
 * as possible.
 * Input: N, then N lines "L U".
 * Output: the minimum number of rooms.
 */
import java.io.*;
import java.util.*;

public class AirConditioned {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine().trim());
        int[][] minions = new int[n][2]; // {lower, upper}

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            minions[i][0] = Integer.parseInt(st.nextToken());
            minions[i][1] = Integer.parseInt(st.nextToken());
        }

        // sort by upper limit
        Arrays.sort(minions, (a, b) -> Integer.compare(a[1], b[1]));

        /*
         * Greedy: set a room to the upper limit of the first minion
         * not yet placed. That's as high as possible, so it catches
         * as many later minions as it can.
         */
        int rooms = 0;
        int roomTemp = Integer.MIN_VALUE;

        for (int i = 0; i < n; i++) {
            if (minions[i][0] > roomTemp) {
                rooms++;
                roomTemp = minions[i][1];
            }
        }

        System.out.println(rooms);
    }
}
