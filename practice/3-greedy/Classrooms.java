/*
 * Kattis: classrooms
 * https://open.kattis.com/problems/classrooms
 * Topic: Greedy + TreeMap multiset
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * n activities with start/finish times and k classrooms. Two activities
 * in the same room need the second to start strictly after the first
 * finishes. Schedule as many activities as possible.
 * Input: n k, then n lines "s f".
 * Output: the maximum number of activities.
 */
import java.io.*;
import java.util.*;

public class Classrooms {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int[][] activities = new int[n][2]; // {start, finish}
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            activities[i][0] = Integer.parseInt(st.nextToken());
            activities[i][1] = Integer.parseInt(st.nextToken());
        }

        // classic interval scheduling: sort by finish time
        Arrays.sort(activities, (a, b) -> Integer.compare(a[1], b[1]));

        /*
         * rooms: finish time of the last activity in each room -> how many rooms.
         * (TreeMap used as a multiset because times can repeat.)
         * All k rooms start empty (time 0).
         */
        TreeMap<Integer, Integer> rooms = new TreeMap<>();
        rooms.put(0, k);

        int count = 0;
        for (int i = 0; i < n; i++) {
            int start = activities[i][0];
            int finish = activities[i][1];

            // room that became free latest but strictly before start
            // (latest so earlier-free rooms stay available for others)
            Integer room = rooms.lowerKey(start);
            if (room == null) continue;

            remove(rooms, room);
            rooms.put(finish, rooms.getOrDefault(finish, 0) + 1);
            count++;
        }

        System.out.println(count);
    }

    static void remove(TreeMap<Integer, Integer> map, int key) {
        int c = map.get(key);
        if (c == 1) map.remove(key);
        else map.put(key, c - 1);
    }
}
