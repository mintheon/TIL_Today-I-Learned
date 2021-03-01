package codility;

public class P_953YW8_GBJ {
    public int solution(int X, int Y, int D) {
        // write your code in Java SE 8
        int answer = 0;
        int distance = Y - X;

        if(X == Y) {
            return 0;
        }

        answer = distance % D == 0
                ? distance / D
                : (distance / D) + 1;

        return answer;
    }
}
