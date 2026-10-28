/*
 * Kattis: recount
 * https://open.kattis.com/problems/recount
 * Course: COMP30970, Week 1: Data Structures
 * Topic: HashMap counting
 * Solution: your own (from kattis/week1/recount/Main.java)
 *
 * Question (summary in my own words; read the full statement on Kattis):
 * Votes are names, one per line, ending with "***". Print the name with the
 * most votes, or "Runoff!" if the top count is tied.
 */
import java.io.*;
import java.util.*;

public class Recount {
    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        HashMap<String, Integer> votes = new HashMap<>();
        String line;

        while ((line = in.readLine()) != null && !line.equals("***")) {
            votes.put(line, votes.getOrDefault(line, 0) + 1);
        }

        String winner = "";
        int best = 0;
        boolean tied = false;

        for (Map.Entry<String, Integer> entry : votes.entrySet()) {
            int count = entry.getValue();
            if (count > best) {
                best = count;
                winner = entry.getKey();
                tied = false;
            } else if (count == best) {
                tied = true;
            }
        }

        System.out.println(tied ? "Runoff!" : winner);
    }
}
