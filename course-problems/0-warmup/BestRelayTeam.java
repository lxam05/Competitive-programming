/*
 * Kattis: bestrelayteam
 * https://open.kattis.com/problems/bestrelayteam
 * Course: COMP30970, Warm-up Exercises
 * Topic: Sorting / brute force
 * Solution: written for this set
 *
 * Question (summary in my own words; read the full statement on Kattis):
 * Pick a 4-person relay team from n runners. Each runner has one time for
 * the first leg and a (faster) time for the other legs. Minimise the total.
 * Input: n, then n lines "name firstLegTime otherLegTime".
 * Output: the best total time, then the 4 names in running order.
 */
import java.io.*;
import java.util.*;

public class BestRelayTeam {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine().trim());
        String[] names = new String[n];
        double[] first = new double[n];  // time on the first leg
        double[] other = new double[n];  // time on any other leg

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            names[i] = st.nextToken();
            first[i] = Double.parseDouble(st.nextToken());
            other[i] = Double.parseDouble(st.nextToken());
        }

        // runners sorted by their "other leg" time, fastest first
        Integer[] byOther = new Integer[n];
        for (int i = 0; i < n; i++) byOther[i] = i;
        Arrays.sort(byOther, (a, b) -> Double.compare(other[a], other[b]));

        /*
         * Try every runner on the first leg.
         * The other three legs are just the 3 fastest "other" times
         * among everyone else.
         */
        double bestTime = Double.MAX_VALUE;
        int[] bestTeam = new int[4];

        for (int start = 0; start < n; start++) {
            double time = first[start];
            int[] team = new int[4];
            team[0] = start;
            int picked = 1;

            for (int k = 0; k < n && picked < 4; k++) {
                int runner = byOther[k];
                if (runner == start) continue;
                time += other[runner];
                team[picked++] = runner;
            }

            if (time < bestTime) {
                bestTime = time;
                bestTeam = team;
            }
        }

        StringBuilder output = new StringBuilder();
        output.append(String.format(Locale.US, "%.2f", bestTime)).append('\n');
        for (int i = 0; i < 4; i++) output.append(names[bestTeam[i]]).append('\n');
        System.out.print(output);
    }
}
