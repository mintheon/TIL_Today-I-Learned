package codility;

public class P_CDCTKP_9YW {
    public int solution(int N) {
        // write your code in Java SE 8
        int max = 0, count = 0;
        int temp;

        while(N > 1) {
            temp = N % 2;

            if(temp == 1) {
                count = 0;
            } else {
                max = Math.max(max, ++count);
            }

            N /= 2;
        }

        return max;
    }
}
