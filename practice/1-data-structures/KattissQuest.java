/*
 * Kattis: kattissquest
 * https://open.kattis.com/problems/kattissquest
 * Topic: TreeMap + PriorityQueue
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * Commands: "add E G" adds a quest costing E energy that gives G gold.
 * "query X": with X energy, repeatedly take the quest with the largest
 * energy cost you can still afford (ties: most gold), remove it, and
 * subtract its cost. Print total gold earned for each query.
 * Input: N, then N commands.
 * Output: one number per query.
 */
import java.io.*;
import java.util.*;

public class KattissQuest {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder output = new StringBuilder();

        int n = Integer.parseInt(br.readLine().trim());

        /*
         * energy -> all the gold rewards of quests with that energy.
         * The PriorityQueue is a max-heap so the best gold comes out first.
         * TreeMap lets us find "biggest energy <= what I have left".
         */
        TreeMap<Integer, PriorityQueue<Integer>> quests = new TreeMap<>();

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String command = st.nextToken();

            if (command.equals("add")) {
                int energy = Integer.parseInt(st.nextToken());
                int gold = Integer.parseInt(st.nextToken());

                if (!quests.containsKey(energy)) {
                    quests.put(energy, new PriorityQueue<>(Collections.reverseOrder()));
                }
                quests.get(energy).add(gold);
            } else {
                int left = Integer.parseInt(st.nextToken());
                long totalGold = 0;

                // keep taking the hardest quest we can still afford
                while (true) {
                    Integer energy = quests.floorKey(left);
                    if (energy == null) break;

                    PriorityQueue<Integer> golds = quests.get(energy);
                    totalGold += golds.poll();
                    left -= energy;

                    if (golds.isEmpty()) quests.remove(energy);
                }

                output.append(totalGold).append('\n');
            }
        }

        System.out.print(output);
    }
}
