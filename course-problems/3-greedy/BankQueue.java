/*
 * Kattis: bank
 * https://open.kattis.com/problems/bank
 * Course: COMP30970, Week 4: Greedy Algorithms
 * Topic: Greedy (schedule latest first)
 * Solution: your own (from kattis/BankQueue.java)
 *
 * Question (summary in my own words; read the full statement on Kattis):
 * N people queue at a bank. Person i has c_i cash and leaves after t_i
 * minutes. You serve one person per minute and the bank closes after T
 * minutes. Maximise the total cash deposited.
 * Input: N T, then N lines "c t".
 */
import java.util.*;

public class BankQueue {
    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);
        HashMap<Integer, Integer> map = new HashMap<>();
        

        int n = sc.nextInt();
        int t = sc.nextInt();

        int[] list = new int[n];
        int[] ans = new int[t];


        //put vals in list and map
        for (int i = 0; i < n; i++) {
            int first = sc.nextInt();
            int second = sc.nextInt();
            map.put(first, second);
            list[i] = first;
        }
        

        //sort the array from largest to smallest
        Arrays.sort(list);
        for(int i = 0; i < n / 2; i++){
            int temp = list[i];
            list[i] = list[n - i - 1];
            list[n - i - 1] = temp;
        }

        //add values to int[] ans
        for(int i = 0; i < n; i++){

            //try to put it in its correct spot first
            // thats less than t
            int ansPlace = Math.min(t - 1, map.get(list[i]));
            if(ans[ansPlace] != 0){
                //go down till free slot
                for(int a = ansPlace; a >= 0; a--){
                    if(ans[a] == 0){
                        ans[a] = list[i];
                        break;
                    }
                }
            }else{
                ans[ansPlace] = list[i];
            }
        }

        int sum = 0;
        for(int i = 0; i < ans.length; i++){
            sum += ans[i];
        }

        System.out.println(sum);
        sc.close();
    }
}
