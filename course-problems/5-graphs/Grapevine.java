/*
 * Kattis: grapevine
 * https://open.kattis.com/problems/grapevine
 * Course: COMP30970, Week 6: Graphs 1
 * Topic: BFS by days
 * Solution: written for this set
 *
 * Question (summary in my own words; read the full statement on Kattis):
 * A rumour starts at one person. Each person needs to hear it from t
 * different people before passing it on, and spreads to all contacts the
 * next day. Count how many people (not the starter) heard it after d days.
 * Input: n m d, n lines "name t", m lines "name name", the starter.
 */
import java.io.*;
import java.util.*;

public class Grapevine {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());

        // name -> number, and how many tellers each person needs
        HashMap<String, Integer> id = new HashMap<>();
        int[] skepticism = new int[n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            id.put(st.nextToken(), i);
            skepticism[i] = Integer.parseInt(st.nextToken());
        }

        List<Integer>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = id.get(st.nextToken());
            int b = id.get(st.nextToken());
            adj[a].add(b);
            adj[b].add(a);
        }

        int start = id.get(br.readLine().trim());

        /*
         * Simulate day by day (like BFS levels).
         * Each spreader tells all their contacts once.
         * told[v] = how many different people told v.
         * v starts spreading the day after told[v] reaches skepticism[v].
         */
        int[] told = new int[n];
        boolean[] heard = new boolean[n];
        boolean[] spreading = new boolean[n];

        heard[start] = true;
        spreading[start] = true;
        ArrayList<Integer> today = new ArrayList<>();
        today.add(start);

        for (int day = 0; day < d && !today.isEmpty(); day++) {
            ArrayList<Integer> tomorrow = new ArrayList<>();

            for (int u : today) {
                for (int v : adj[u]) {
                    heard[v] = true;
                    told[v]++;
                    if (!spreading[v] && told[v] >= skepticism[v]) {
                        spreading[v] = true;
                        tomorrow.add(v);
                    }
                }
            }
            today = tomorrow;
        }

        // count everyone who heard it, not including the person who started it
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (heard[i] && i != start) count++;
        }
        System.out.println(count);
    }
}
