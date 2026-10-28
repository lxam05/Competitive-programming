/*
 * Kattis: flowerytrails
 * https://open.kattis.com/problems/flowerytrails
 * Topic: Dijkstra twice
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * A park of trails between points. Hikers only use shortest paths from the
 * entrance (0) to the peak (P-1). Flowers are planted on both sides of
 * every trail that lies on at least one shortest path. How much flower
 * length is needed?
 * Input: P T, then T lines "a b length".
 * Output: twice the total length of those trails.
 */
import java.io.*;
import java.util.*;

public class FloweryTrails {
    static int p;
    static List<int[]>[] adj;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        p = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());

        adj = new ArrayList[p];
        for (int i = 0; i < p; i++) adj[i] = new ArrayList<>();

        int[][] trails = new int[t][3];
        for (int i = 0; i < t; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int len = Integer.parseInt(st.nextToken());
            trails[i] = new int[]{a, b, len};
            adj[a].add(new int[]{b, len});
            adj[b].add(new int[]{a, len});
        }

        /*
         * Run Dijkstra from the entrance (0) and from the peak (p-1).
         * A trail a-b is on SOME shortest path exactly when
         * fromStart[a] + len + fromPeak[b] == shortest (either direction).
         */
        long[] fromStart = dijkstra(0);
        long[] fromPeak = dijkstra(p - 1);
        long shortest = fromStart[p - 1];

        long total = 0;
        for (int[] tr : trails) {
            int a = tr[0], b = tr[1], len = tr[2];
            if (fromStart[a] + len + fromPeak[b] == shortest
                    || fromStart[b] + len + fromPeak[a] == shortest) {
                total += len;
            }
        }

        // flowers go on both sides of the trail
        System.out.println(total * 2);
    }

    static long[] dijkstra(int source) {
        long INF = Long.MAX_VALUE / 4; // /4 so adding two never overflows
        long[] dist = new long[p];
        Arrays.fill(dist, INF);
        dist[source] = 0;

        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
        pq.add(new long[]{0, source});

        while (!pq.isEmpty()) {
            long[] cur = pq.poll();
            int u = (int) cur[1];
            if (cur[0] > dist[u]) continue;

            for (int[] e : adj[u]) {
                long nd = dist[u] + e[1];
                if (nd < dist[e[0]]) {
                    dist[e[0]] = nd;
                    pq.add(new long[]{nd, e[0]});
                }
            }
        }
        return dist;
    }
}
