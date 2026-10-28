/*
 * Kattis: integerlists
 * https://open.kattis.com/problems/integerlists
 * Topic: Deque
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * A program of R (reverse the list) and D (drop the first element) is run
 * on a list of integers. Dropping from an empty list is an error.
 * Input: T test cases. Each: the program string, n, the list as [x1,...,xn].
 * Output: the final list in the same [..] format, or "error".
 */
import java.io.*;
import java.util.*;

public class IntegerLists {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder output = new StringBuilder();

        int tests = Integer.parseInt(br.readLine().trim());

        while (tests-- > 0) {
            String program = br.readLine().trim();
            int n = Integer.parseInt(br.readLine().trim());
            String listLine = br.readLine().trim();

            // strip the [ ] and split on commas
            ArrayDeque<Integer> list = new ArrayDeque<>();
            StringTokenizer st = new StringTokenizer(listLine.substring(1, listLine.length() - 1), ",");
            while (st.hasMoreTokens()) list.add(Integer.parseInt(st.nextToken()));

            /*
             * Never actually reverse (too slow).
             * Just remember which end is the "front".
             */
            boolean reversed = false;
            boolean error = false;

            for (int i = 0; i < program.length(); i++) {
                char c = program.charAt(i);
                if (c == 'R') {
                    reversed = !reversed;
                } else {
                    if (list.isEmpty()) {
                        error = true;
                        break;
                    }
                    if (reversed) list.pollLast();
                    else list.pollFirst();
                }
            }

            if (error) {
                output.append("error\n");
                continue;
            }

            output.append('[');
            boolean first = true;
            while (!list.isEmpty()) {
                if (!first) output.append(',');
                output.append(reversed ? list.pollLast() : list.pollFirst());
                first = false;
            }
            output.append("]\n");
        }

        System.out.print(output);
    }
}
