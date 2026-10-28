/*
 * Kattis: grass
 * https://open.kattis.com/problems/grass
 * Course: COMP30970, Week 4: Greedy Algorithms
 * Topic: Greedy interval cover
 * Solution: your own (from kattis/WateringGrass.java)
 *
 * Question (summary in my own words; read the full statement on Kattis):
 * A strip of grass l long and w wide has n sprinklers on its centre line,
 * each at a position with a radius. Find the fewest sprinklers needed to
 * water the whole strip, or -1.
 * Input: several cases "n l w" + n lines "x r", until end of file.
 */
import java.io.*;
import java.util.*;

public class WateringGrass {

    static class Interval {
        double left;
        double right;

        Interval(double left, double right) {
            this.left = left;
            this.right = right;
        }
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String line;

        while ((line = br.readLine()) != null) {

            line = line.trim();

            if (line.isEmpty()) {
                continue;
            }

            StringTokenizer st = new StringTokenizer(line);

            int n = Integer.parseInt(st.nextToken());
            int l = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            ArrayList<Interval> intervals = new ArrayList<>();

            double halfWidth = w / 2.0;

            for (int i = 0; i < n; i++) {

                st = new StringTokenizer(br.readLine());

                int x = Integer.parseInt(st.nextToken());
                int r = Integer.parseInt(st.nextToken());

                /*
                 * If the radius is not large enough to reach
                 * both edges of the grass strip, this sprinkler
                 * is useless.
                 */
                if (r <= halfWidth) {
                    continue;
                }

                /*
                 * The sprinkler covers a circle.
                 *
                 * We need the horizontal distance from the centre
                 * where the circle still reaches the top/bottom
                 * edge of the strip.
                 *
                 * horizontal^2 + (w/2)^2 = r^2
                 */
                double horizontal =
                        Math.sqrt(r * r - halfWidth * halfWidth);

                double left = x - horizontal;
                double right = x + horizontal;

                intervals.add(new Interval(left, right));
            }

            /*
             * Sort by left endpoint.
             *
             * If two intervals start at the same place,
             * put the one reaching further right first.
             */
            intervals.sort((a, b) -> {

                if (Double.compare(a.left, b.left) != 0) {
                    return Double.compare(a.left, b.left);
                }

                return Double.compare(b.right, a.right);
            });

            double covered = 0.0;

            int index = 0;
            int answer = 0;

            boolean possible = true;

            /*
             * Greedy:
             *
             * Among every sprinkler that starts before or at
             * the currently covered point, choose the one that
             * reaches furthest right.
             */
            while (covered < l) {

                double furthest = covered;

                while (index < intervals.size()
                        && intervals.get(index).left <= covered) {

                    furthest = Math.max(
                            furthest,
                            intervals.get(index).right
                    );

                    index++;
                }

                /*
                 * No interval can extend our coverage.
                 * Therefore there is a gap.
                 */
                if (furthest == covered) {
                    possible = false;
                    break;
                }

                covered = furthest;
                answer++;
            }

            if (possible) {
                System.out.println(answer);
            } else {
                System.out.println(-1);
            }
        }
    }
}