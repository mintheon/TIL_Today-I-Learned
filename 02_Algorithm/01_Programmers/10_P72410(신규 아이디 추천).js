function solution(new_id) {
   var answer = new_id;

   //1
   answer = answer.toLowerCase();

   //2
   var pattern = new RegExp(/^[a-z0-9\-\_\.]$/);
   var charArr = answer.split("")
                  .filter((char) => 
                  pattern.test(char));

   //3
   for(var i = 1; i < charArr.length; i++) {
      if(charArr[i] === "." && charArr[i - 1] === ".") {
         charArr[i - 1] = "";
      }
   }
   answer = charArr.join("");

   //4 
   charArr = answer.split("");
   if(charArr[0] === ".") charArr[0] = "";
   if(charArr[charArr.length - 1] === ".") charArr[charArr.length - 1] = "";
   answer = charArr.join("");

   //5
   answer = !answer ? "a" : answer;

   //6
   charArr = answer.split("").slice(0, 15);
   if(charArr[charArr.length - 1] === ".") charArr[charArr.length - 1] = "";
   answer = charArr.join("");

   //7
   var needLength = 3 - answer.length;
   if(needLength > 0) {
      var lastText = answer.charAt(answer.length - 1);
      for(var i = 0; i < needLength; i++) {
         answer += lastText;
      }
   }

   return answer;
}

//다른사람의 답
// function solution(new_id) {
//    var answer = new_id
//       .toLowerCase() //1
//       .replace(/[^\w-_.]/g, "") //2
//       .replace(/\.{2,}/g, ".") //3
//       .replace(/^\.|\.$/g, "") //4
//       .replace(/^$/, "a") //5
//       .slice(0, 15).replace(/\.$/, ""); //6

//    const len = answer.length;

//    return len > 2 
//       ? answer
//       : answer + answer.charAt(len - 1).repeat(3 - len);
// }

console.log(solution("...!@BaT#*..y.abcdefghijklm")); // "bat.y.abcdefghi"
console.log(solution("z-+.^.")); // "z--"
console.log(solution("=.=")); //"aaa"
console.log(solution("123_.def")); //"123_.def"
console.log(solution("abcdefghijklmn.p")); //"abcdefghijklmn"
