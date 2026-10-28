/*
 * Kattis: teque
 * https://open.kattis.com/problems/teque
 * Topic: Two deques / arrays
 *
 * Problem (summary in my own words, read the full statement on Kattis):
 * A "triple-ended queue" supporting push_back x, push_front x,
 * push_middle x (insert at position floor((k+1)/2) when it holds k
 * elements, 0-indexed) and get i (print element at index i).
 * Input: N, then N commands.
 * Output: the answer to each get, one per line.
 */
import java.io.*;
import java.util.*;

public class Teque {
    /*
     * Split the teque into a front half and a back half.
     * Each half is an array with head/tail pointers so we can
     * push on both ends AND look up any index in O(1).
     * front always has the same size as back, or one more.
     */
    static int[] front, back;
    static int frontHead, frontTail, backHead, backTail;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder output = new StringBuilder();

        int n = Integer.parseInt(br.readLine().trim());

        // start pointers in the middle so both ends have room
        front = new int[2 * n + 2];
        back = new int[2 * n + 2];
        frontHead = frontTail = n + 1; // elements are front[head .. tail-1]
        backHead = backTail = n + 1;

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String command = st.nextToken();
            int x = Integer.parseInt(st.nextToken());

            if (command.equals("push_back")) {
                back[backTail++] = x;
            } else if (command.equals("push_front")) {
                front[--frontHead] = x;
            } else if (command.equals("push_middle")) {
                front[frontTail++] = x;
            } else {
                // get x
                int frontSize = frontTail - frontHead;
                if (x < frontSize) output.append(front[frontHead + x]).append('\n');
                else output.append(back[backHead + x - frontSize]).append('\n');
                continue;
            }

            balance();
        }

        System.out.print(output);
    }

    static void balance() {
        int frontSize = frontTail - frontHead;
        int backSize = backTail - backHead;

        if (frontSize > backSize + 1) {
            // move last of front to the start of back
            back[--backHead] = front[--frontTail];
        } else if (backSize > frontSize) {
            // move first of back to the end of front
            front[frontTail++] = back[backHead++];
        }
    }
}
