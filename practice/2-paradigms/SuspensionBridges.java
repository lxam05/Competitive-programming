/*
 * Kattis: suspensionbridges
 * https://open.kattis.com/problems/suspensionbridges
 * Topic: Binary search on a real number
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * A cable hangs between two poles d apart and sags s in the middle,
 * following y = a*cosh(x/a). Find a from a + s = a*cosh(d/(2a)),
 * then print the cable length 2a*sinh(d/(2a)).
 * Input: d s.
 * Output: the length (absolute/relative error 1e-6 allowed).
 */
import java.io.*;
import java.util.*;

public class SuspensionBridges {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        double d = Double.parseDouble(st.nextToken());
        double s = Double.parseDouble(st.nextToken());

        /*
         * Need a with  a + s = a * cosh(d / (2a)).
         * The sag  a*cosh(d/2a) - a  gets smaller as a gets bigger,
         * so we can binary search on a (real numbers: fixed iterations).
         */
        double low = 0, high = 1e9;
        for (int i = 0; i < 200; i++) {
            double a = (low + high) / 2;
            double sag = a * Math.cosh(d / (2 * a)) - a;

            if (sag > s) low = a;   // sags too much -> need bigger a
            else high = a;
        }

        double a = (low + high) / 2;
        double length = 2 * a * Math.sinh(d / (2 * a));

        System.out.println(String.format(Locale.US, "%.9f", length));
    }
}
