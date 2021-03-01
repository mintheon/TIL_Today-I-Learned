package codility;

import java.util.*;

public class P_3TFVU5_YJA {
    public int[] solution(int[] A, int K) {
        // write your code in Java SE 8
        int[] EMPTY_ARRAY = {};
        Deque<Integer> dequeue = new LinkedList<>();

        if(A.length == 0) {
            return EMPTY_ARRAY;
        }

        for(int a : A) {
            dequeue.add(a);
        }

        for(int i = 0; i < K; i++) {
            dequeue.offerFirst(dequeue.pollLast());
        }

        return dequeue.stream().mapToInt(Integer::intValue).toArray();
    }
}
