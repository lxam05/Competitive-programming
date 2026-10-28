/*
 * Kattis: builddeps
 * https://open.kattis.com/problems/builddeps
 * Topic: Topological sort
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * Lines "file: dep1 dep2 ..." say file depends on those files. When one
 * file changes, it and everything depending on it (directly or not) must
 * be recompiled, each after the things it depends on.
 * Input: n, n rule lines, then the changed file.
 * Output: a valid recompile order, one file per line.
 */
import java.io.*;
import java.util.*;

public class BuildDeps {
    static HashMap<String, List<String>> usedBy = new HashMap<>();
    static HashSet<String> visited = new HashSet<>();
    static ArrayList<String> finished = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine().trim());

        /*
         * Line "a: b c" means a depends on b and c.
         * Store the reverse: b is used by a, c is used by a.
         * Then everything reachable from the changed file must be rebuilt.
         */
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String file = st.nextToken();
            file = file.substring(0, file.length() - 1); // drop the ':'
            usedBy.putIfAbsent(file, new ArrayList<>());

            while (st.hasMoreTokens()) {
                String dep = st.nextToken();
                usedBy.putIfAbsent(dep, new ArrayList<>());
                usedBy.get(dep).add(file);
            }
        }

        String changed = br.readLine().trim();

        // topological sort with DFS: reverse of finishing order
        dfs(changed);

        StringBuilder output = new StringBuilder();
        for (int i = finished.size() - 1; i >= 0; i--) {
            output.append(finished.get(i)).append('\n');
        }
        System.out.print(output);
    }

    static void dfs(String start) {
        // iterative DFS so a long chain of files can't overflow the stack
        ArrayDeque<String> stack = new ArrayDeque<>();
        ArrayDeque<Integer> nextChild = new ArrayDeque<>();
        stack.push(start);
        nextChild.push(0);
        visited.add(start);

        while (!stack.isEmpty()) {
            String u = stack.peek();
            int i = nextChild.pop();
            List<String> children = usedBy.getOrDefault(u, new ArrayList<>());

            if (i < children.size()) {
                nextChild.push(i + 1);
                String v = children.get(i);
                if (!visited.contains(v)) {
                    visited.add(v);
                    stack.push(v);
                    nextChild.push(0);
                }
            } else {
                finished.add(u);
                stack.pop();
            }
        }
    }
}
