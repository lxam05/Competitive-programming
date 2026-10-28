/*
 * Kattis: joinstrings
 * https://open.kattis.com/problems/joinstrings
 * Topic: Linked list
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * N strings. N-1 operations "a b": append string b to the end of string
 * a, then string b becomes empty (never used again). Print the single
 * string left at the end.
 * Input: N, N strings, then N-1 lines "a b" (1-indexed).
 * Output: the final string.
 */
import java.io.*;
import java.util.*;

public class JoinStrings {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine().trim());
        String[] words = new String[n + 1];
        for (int i = 1; i <= n; i++) words[i] = br.readLine();

        /*
         * Joining real strings is too slow (copies everything).
         * Instead build a linked list of indices:
         * next[i] = which string comes after string i
         * last[i] = the final string in the chain starting at i
         */
        int[] next = new int[n + 1];
        int[] last = new int[n + 1];
        for (int i = 1; i <= n; i++) last[i] = i;

        int start = 1; // if n == 1 there are no operations
        for (int i = 0; i < n - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            next[last[a]] = b;
            last[a] = last[b];
            start = a; // the last a used is the one holding everything
        }

        StringBuilder output = new StringBuilder();
        for (int cur = start; cur != 0; cur = next[cur]) {
            output.append(words[cur]);
        }
        System.out.println(output);
    }
}
