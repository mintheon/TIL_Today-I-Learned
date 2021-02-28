package programmers;

import java.util.HashSet;
import java.util.Set;

// 본인 문제 풀이
public class P42839 {
   private String[] numberArr;
   private boolean[] visited;

   private Set<Integer> numberSet;
   private String[] result;

   public int solution(String numbers) {
       numberArr = numbers.split("");
       visited = new boolean[numberArr.length];
       numberSet = new HashSet<>();

       for(int i = 1; i <= numberArr.length; i++) {
           result = new String[i];
           search(0, i, numberArr.length);
       }

       return numberSet.size();
   }

   public void search(int start, int end, int length) {
       if (start == end) {
           int number = Integer.parseInt(String.join("", result));
           if(isPrime(number)) {
               numberSet.add(number);
           };
       } else {
           for (int i = 0; i < length; i++) {
               if (!visited[i]) {
                   visited[i] = true;
                   result[start] = numberArr[i];
                   search(start + 1, end, length);
                   visited[i] = false;
               }
           }
       }
   }

   public boolean isPrime(int number) {
       if(number == 2) {
           return true;
       }

       if(number == 1 || number % 2 == 0) {
           return false;
       }

       for(int i = 3; i <= Math.sqrt(number); i += 2) {
           if(number % i == 0) {
               return false;
           }
       }

       return true;
   }
}

//다른사람 문제 풀이
// public class P42839 {
//     public int solution(String numbers) {
//         HashSet<Integer> set = new HashSet<>();
//         permutation("", numbers, set);
//         int count = 0;
//         while(set.iterator().hasNext()){
//             int a = set.iterator().next();
//             set.remove(a);
//             if(a==2) count++;
//             if(a%2!=0 && isPrime(a)){
//                 count++;
//             }
//         }
//         return count;
//     }

//     public boolean isPrime(int n){
//         if(n==0 || n==1) return false;
//         for(int i=3; i<=(int)Math.sqrt(n); i+=2){
//             if(n%i==0) return false;
//         }
//         return true;
//     }

//     public void permutation(String prefix, String str, HashSet<Integer> set) {
//         int n = str.length();
//         //if (n == 0) System.out.println(prefix);
//         if(!prefix.equals("")) set.add(Integer.valueOf(prefix));
//         for (int i = 0; i < n; i++)
//             permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n), set);

//     }
// }

