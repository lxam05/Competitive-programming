/*
 * Kattis: mandelbrot
 * https://open.kattis.com/problems/mandelbrot
 * Course: COMP30970, Warm-up Exercises
 * Topic: Simulation (complex numbers)
 * Solution: written for this set
 *
 * Question (summary in my own words; read the full statement on Kattis):
 * For each line "x y r", start with z = 0 and apply z = z^2 + c (c = x + yi)
 * r times. If |z| ever goes above 2 the point is OUT, otherwise IN.
 * Input: lines until end of file.
 * Output: "Case k: IN" or "Case k: OUT".
 */
import java.io.*;
import java.util.*;

public class Mandelbrot {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder output = new StringBuilder();

        int caseNumber = 1;
        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;

            StringTokenizer st = new StringTokenizer(line);
            double cx = Double.parseDouble(st.nextToken());
            double cy = Double.parseDouble(st.nextToken());
            int r = Integer.parseInt(st.nextToken());

            /*
             * z starts at 0, then z = z^2 + c, r times.
             * With z = x + yi:  z^2 = (x^2 - y^2) + (2xy)i
             * If |z| ever goes above 2 the point is OUT.
             */
            double x = 0, y = 0;
            boolean inside = true;
            for (int i = 0; i < r; i++) {
                double newX = x * x - y * y + cx;
                double newY = 2 * x * y + cy;
                x = newX;
                y = newY;

                if (x * x + y * y > 4) { // |z| > 2, compare squares to skip sqrt
                    inside = false;
                    break;
                }
            }

            output.append("Case ").append(caseNumber++).append(": ")
                  .append(inside ? "IN" : "OUT").append('\n');
        }

        System.out.print(output);
    }
}
