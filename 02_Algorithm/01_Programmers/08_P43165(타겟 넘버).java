package programmers;

// 본인 문제 풀이
public class P43165 {
    private int[] numbers;
    private int target;
    private int count;

    public int solution(int[] numbers, int target) {
        this.target = target;
        this.numbers = numbers;
        this.count = 0;

        dfs(0, 0);

        return count;
    }

    public void dfs(int index, int sum) {
        if(index == numbers.length) {
            if(sum == target) {
                count++;
            }
        } else {
            dfs(index + 1, sum + numbers[index]);
            dfs(index + 1, sum - numbers[index]);
        }
    }
}

//다른사람 문제 풀이
// class Solution {
//     public int solution(int[] numbers, int target) {
//         int answer = 0;
//         answer = dfs(numbers, 0, 0, target);
//         return answer;
//     }
//     int dfs(int[] numbers, int n, int sum, int target) {
//         if(n == numbers.length) {
//             if(sum == target) {
//                 return 1;
//             }
//             return 0;
//         }
//         return dfs(numbers, n + 1, sum + numbers[n], target) + dfs(numbers, n + 1, sum - numbers[n], target);
//     }
// }