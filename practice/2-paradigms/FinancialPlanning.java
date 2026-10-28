/*
 * Kattis: financialplanning
 * https://open.kattis.com/problems/financialplanning
 * Topic: Binary search on the answer
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * You need M money to retire. Investment i costs c_i up front and earns
 * p_i per day. Choose investments to retire as soon as possible.
 * Find the fewest days d so that some choice earns at least M profit
 * (sum of p_i*d - c_i over the chosen ones).
 * Input: n M, then n lines "p c".
 * Output: the minimum number of days.
 */
import java.io.*;
import java.util.*;

public class FinancialPlanning {
    static int n;
    static long target;
    static long[] profit, cost;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        target = Long.parseLong(st.nextToken());

        profit = new long[n];
        cost = new long[n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            profit[i] = Long.parseLong(st.nextToken());
            cost[i] = Long.parseLong(st.nextToken());
        }

        /*
         * Binary search on the answer (number of days).
         * If we can retire after d days, we can also retire after d+1,
         * so find the smallest d that works.
         */
        long low = 1, high = 2_000_000_000L;
        while (low < high) {
            long mid = (low + high) / 2;
            if (enough(mid)) high = mid;
            else low = mid + 1;
        }

        System.out.println(low);
    }

    // only buy investments that make a profit by day d
    static boolean enough(long days) {
        long total = 0;
        for (int i = 0; i < n; i++) {
            long gain = profit[i] * days - cost[i];
            if (gain > 0) {
                total += gain;
                if (total >= target) return true; // stop early, avoids overflow
            }
        }
        return false;
    }
}
