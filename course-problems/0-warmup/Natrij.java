/*
 * Kattis: natrij
 * https://open.kattis.com/problems/natrij
 * Course: COMP30970, Warm-up Exercises
 * Topic: Time arithmetic
 * Solution: written for this set
 *
 * Question (summary in my own words; read the full statement on Kattis):
 * Given the current time and the explosion time (hh:mm:ss), print how long
 * to wait in hh:mm:ss. The wait is at least 1 second and at most 24 hours
 * (same time means a full day).
 */
import java.io.*;
import java.util.*;

public class Natrij {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int now = toSeconds(br.readLine().trim());
        int explosion = toSeconds(br.readLine().trim());

        // wrap around midnight
        int wait = explosion - now;
        if (wait <= 0) wait += 24 * 60 * 60; // same time -> a full day

        int h = wait / 3600;
        int m = (wait % 3600) / 60;
        int s = wait % 60;

        System.out.println(String.format("%02d:%02d:%02d", h, m, s));
    }

    // "hh:mm:ss" -> seconds since midnight
    static int toSeconds(String time) {
        String[] parts = time.split(":");
        return Integer.parseInt(parts[0]) * 3600
             + Integer.parseInt(parts[1]) * 60
             + Integer.parseInt(parts[2]);
    }
}
