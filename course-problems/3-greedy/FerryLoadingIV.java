/*
 * Kattis: ferryloading4
 * https://open.kattis.com/problems/ferryloading4
 * Course: COMP30970, Week 4: Greedy Algorithms
 * Topic: Queue simulation
 * Solution: your own (from kattis/FerryLoadingIv.java)
 *
 * Question (summary in my own words; read the full statement on Kattis):
 * A ferry of length l metres starts on the left bank. Cars (length in cm,
 * arriving at the left or right bank) are loaded in order while they fit.
 * Count how many crossings are needed to carry every car.
 */
import java.util.*;

public class FerryLoadingIV {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int cases = sc.nextInt();

        while (cases-- > 0) {

            int l = sc.nextInt();
            int m = sc.nextInt();

            // Ferry length is given in metres, car lengths in centimetres
            int capacity = l * 100;

            Queue<Integer> left = new LinkedList<>();
            Queue<Integer> right = new LinkedList<>();

            for (int i = 0; i < m; i++) {

                int carLength = sc.nextInt();
                String side = sc.next();

                if (side.equals("left")) {
                    left.add(carLength);
                } else {
                    right.add(carLength);
                }
            }

            boolean onLeft = true;
            int crossings = 0;

            while (!left.isEmpty() || !right.isEmpty()) {

                int spaceUsed = 0;

                if (onLeft) {

                    while (!left.isEmpty()
                            && spaceUsed + left.peek() <= capacity) {

                        spaceUsed += left.poll();
                    }

                } else {

                    while (!right.isEmpty()
                            && spaceUsed + right.peek() <= capacity) {

                        spaceUsed += right.poll();
                    }
                }

                // Ferry crosses even if it is empty
                crossings++;

                // Switch bank
                onLeft = !onLeft;
            }

            System.out.println(crossings);
        }

        sc.close();
    }
}