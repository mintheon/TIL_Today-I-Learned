package programmers;

import java.util.ArrayList;
import java.util.Comparator;

import java.util.List;
import java.util.stream.Collectors;

public class P42746 {
    public String solution(int[] numbers) {
        String answer = "";

        final Comparator<String> comp = (num1, num2) -> {
            return Integer.parseInt(num2 + num1) - Integer.parseInt(num1 + num2);
        };

        List<String> nums = new ArrayList<>();
        for(int i = 0; i < numbers.length; i++) {
            nums.add(String.valueOf(numbers[i]));
        }

        nums = nums.stream().sorted(comp).collect(Collectors.toList());
        answer = String.join("", nums);

        return answer.startsWith("0") ? "0" : answer;
    }
}
