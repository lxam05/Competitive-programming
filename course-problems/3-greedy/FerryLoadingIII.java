/*
 * Kattis: ferryloading3
 * https://open.kattis.com/problems/ferryloading3
 * Course: COMP30970, Week 4: Greedy Algorithms
 * Topic: Queue simulation
 * Solution: your own (from kattis/FeryyLoadingLii.java)
 *
 * Question (summary in my own words; read the full statement on Kattis):
 * A ferry carries up to n cars and a crossing takes t minutes. m cars arrive
 * at given times on either bank. The ferry loads the cars waiting where it
 * is (earliest first) and crosses when there are cars to move.
 * Print the time each car reaches the other side, in input order.
 */
import java.util.*;

public class FerryLoadingIII {

    static class Car {
        int arrivalTime;
        int index;

        Car(int arrivalTime, int index) {
            this.arrivalTime = arrivalTime;
            this.index = index;
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int cases = sc.nextInt();

        while (cases-- > 0) {

            int n = sc.nextInt();
            int t = sc.nextInt();
            int m = sc.nextInt();

            Queue<Car> left = new LinkedList<>();
            Queue<Car> right = new LinkedList<>();

            int[] answer = new int[m];

            for (int i = 0; i < m; i++) {

                int arrival = sc.nextInt();
                String side = sc.next();

                Car car = new Car(arrival, i);

                if (side.equals("left")) {
                    left.add(car);
                } else {
                    right.add(car);
                }
            }

            int currentTime = 0;
            String currentSide = "left";

            while (!left.isEmpty() || !right.isEmpty()) {

                Queue<Car> currentQueue;
                Queue<Car> otherQueue;

                if (currentSide.equals("left")) {
                    currentQueue = left;
                    otherQueue = right;
                } else {
                    currentQueue = right;
                    otherQueue = left;
                }

                // Are any cars currently waiting?
                boolean carHere =
                        !currentQueue.isEmpty()
                        && currentQueue.peek().arrivalTime <= currentTime;

                boolean carOtherSide =
                        !otherQueue.isEmpty()
                        && otherQueue.peek().arrivalTime <= currentTime;

                /*
                 * Nothing has arrived yet.
                 * Jump forward to the next arrival time.
                 */
                if (!carHere && !carOtherSide) {

                    int nextTime = Integer.MAX_VALUE;

                    if (!left.isEmpty()) {
                        nextTime = Math.min(
                                nextTime,
                                left.peek().arrivalTime
                        );
                    }

                    if (!right.isEmpty()) {
                        nextTime = Math.min(
                                nextTime,
                                right.peek().arrivalTime
                        );
                    }

                    currentTime = nextTime;

                    continue;
                }

                /*
                 * Load cars from the current side.
                 */
                int loaded = 0;

                while (
                        loaded < n
                        && !currentQueue.isEmpty()
                        && currentQueue.peek().arrivalTime <= currentTime
                ) {

                    Car car = currentQueue.poll();

                    // It takes t minutes to cross
                    answer[car.index] = currentTime + t;

                    loaded++;
                }

                /*
                 * Whether loaded or empty, if something is waiting,
                 * the ferry crosses.
                 */
                currentTime += t;

                if (currentSide.equals("left")) {
                    currentSide = "right";
                } else {
                    currentSide = "left";
                }
            }

            for (int value : answer) {
                System.out.println(value);
            }

            if (cases > 0) {
                System.out.println();
            }
        }

        sc.close();
    }
}