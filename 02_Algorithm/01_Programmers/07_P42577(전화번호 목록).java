package programmers;

import java.util.Arrays;

// 본인 문제 풀이
public class P42577 {
    public boolean solution(String[] phone_book) {
        Arrays.sort(phone_book);

        for(int i = 0; i < phone_book.length - 1; i++) {
            if(phone_book[i + 1].contains(phone_book[i])) {
                return false;
            }
        }

        return true;
    }
}

//다른사람 문제 풀이
//public class P42577 {
//    public boolean solution(String[] phone_book) {
//        for(int i=0; i<phone_book.length-1; i++) {
//        for(int j=i+1; j<phone_book.length; j++) {
//            if(phone_book[i].startsWith(phone_book[j])) {return false;}
//            if(phone_book[j].startsWith(phone_book[i])) {return false;}
//        }
//    }
//        return true;
//    }
//}