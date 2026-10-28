/*
 * Kattis: roompainting
 * https://open.kattis.com/problems/roompainting
 * Course: COMP30970, Week 3: Problem Solving Paradigms
 * Topic: Sorting + binary search
 * Solution: written for this set
 *
 * Question (summary in my own words; read the full statement on Kattis):
 * A shop sells n can sizes. For each of m colours you need a given amount
 * of paint; you buy the smallest can that is big enough. Print the total
 * amount of paint wasted.
 * Input: n m, the n can sizes, then the m amounts.
 */
import java.io.*;
import java.util.*;

public class RoomPainting {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[] cans = new int[n];
        for (int i = 0; i < n; i++) cans[i] = Integer.parseInt(br.readLine().trim());
        Arrays.sort(cans);

        /*
         * For each colour, buy the smallest can that is big enough.
         * Cans are sorted, so binary search finds it quickly.
         */
        long waste = 0;
        for (int i = 0; i < m; i++) {
            int need = Integer.parseInt(br.readLine().trim());

            // first can with size >= need
            int low = 0, high = n - 1;
            while (low < high) {
                int mid = (low + high) / 2;
                if (cans[mid] >= need) high = mid;
                else low = mid + 1;
            }

            waste += cans[low] - need;
        }

        System.out.println(waste);
    }
}
