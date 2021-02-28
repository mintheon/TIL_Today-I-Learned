package programmers;

import java.util.Arrays;

// 내 풀이
public class P42576 {
    public String solution(String[] participant, String[] completion) {
        String answer = "";

        Arrays.sort(participant);
        Arrays.sort(completion);

        for(int i = 0; i < completion.length; i++) {
            if(!completion[i].equals(participant[i])) {
                return participant[i];
            }
        }

        return participant[participant.length - 1];
    }
}

// 다른사람 풀이
// public class P42576 {
//     public String solution(String[] participant, String[] completion) {
//         String answer = "";
//         HashMap<String, Integer> hm = new HashMap<>();

//         for (String player : participant) {
//             hm.put(player, hm.getOrDefault(player, 0) + 1);
//         }
//         for (String player : completion) {
//             hm.put(player, hm.get(player) - 1);
//         }

//         for (String key : hm.keySet()) {
//             if (hm.get(key) != 0){
//                 answer = key;
//             }
//         }
//         return answer;
//     }
// }