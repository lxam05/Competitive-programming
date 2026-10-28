/*
 * Kattis: guessthedatastructure
 * https://open.kattis.com/problems/guessthedatastructure
 * Course: COMP30970, Week 1: Data Structures
 * Topic: Stack / Queue / PriorityQueue
 * Solution: your own (from kattis/ICanGuessTheDataStructure.java)
 *
 * Question (summary in my own words; read the full statement on Kattis):
 * A sequence of "1 x" (insert x) and "2 x" (removed x) operations. Decide if
 * the bag behaves like a stack, queue, priority queue (max first), several of
 * them ("not sure") or none ("impossible").
 * Input: several test cases until end of file.
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.io.*;

public class ICanGuessTheDataStructure {
    public static void main(String[] args) throws Exception{

        Deque<Integer> stack = new ArrayDeque<>();
        Queue<Integer> q = new ArrayDeque<>();
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());

             

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        

        while((line = br.readLine()) != null){

            boolean stackBool = true;
            boolean qBool = true;
            boolean pqBool = true;

            int numTrue = 0;

            int len = Integer.parseInt(line);


            for(int i = 0; i < len; i++){

                line = br.readLine();

                String[] row = line.split(" ");

                int[] two = new int[2];

                two[0] = Integer.parseInt(row[0]);
                two[1] = Integer.parseInt(row[1]);
                //System.out.println();
                //System.out.print(two[0] + " " + two[1] + " ");

                //stack
                if(two[0] == 1){
                    stack.push(two[1]);
                }else if(two[0] == 2){
                    if(stack.isEmpty() || stack.peek() != two[1]) {
                        stackBool = false;
                        //System.out.print(" not stack ");
                    }
                    if(!stack.isEmpty()){
                        stack.pop();
                    }
                }

                //queue
                if(two[0] == 1){
                    q.add(two[1]);
                }else if(two[0] == 2){
                    if(q.isEmpty() || q.peek() != two[1]){
                        //System.out.println("\n" + q.peek() + " : " + two[1]);
                        //System.out.print(" not q ");
                        qBool = false;
                    }
                    if(!q.isEmpty()) q.poll();
                }

                //pq
                if(two[0] == 1){
                    pq.offer(two[1]);
                }else if(two[0] == 2){
                    if(pq.isEmpty() || pq.peek() != two[1]){
                        //System.out.print(" not pq ");
                        pqBool = false;
                    }
                    if(!pq.isEmpty()) pq.poll();
                }
            }

            stack.clear();
            q.clear();
            pq.clear();

            if(pqBool) numTrue++;
            if(qBool) numTrue++;
            if(stackBool) numTrue++;

            if(!stackBool && !pqBool && ! qBool){
                System.out.println("impossible");
            }else if(numTrue >= 2){
                System.out.println("not sure");
            }else if(pqBool){
                System.out.println("priority queue");
            }else if(qBool){
                System.out.println("queue");
            }else{
                System.out.println("stack");
            }
        }



    }
}
