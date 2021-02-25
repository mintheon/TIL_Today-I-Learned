function solution(answers) {
   let result = [];
   
   let person1 = [1, 2, 3, 4, 5];
   let person2 = [2, 1, 2, 3, 2, 4, 2, 5];
   let person3 = [3, 3, 1, 1, 2, 2, 4, 4, 5, 5];
   
   let maxScore = 0;
   let score = {
       person1: 0,
       person2: 0,
       person3: 0
   };
   
   answers.forEach((answer, index) => {
       if(answer === person1[(index % person1.length)])
           score.person1++;
       
       if(answer === person2[(index % person2.length)])
           score.person2++;
       
       if(answer === person3[(index % person3.length)])
           score.person3++;
   });
   
   maxScore = Math.max(...Object.values(score));
   Object.entries(score).forEach(([key, value], index) => {
       if(value === maxScore) 
           result.push(index + 1);
   });
   
   return result;
}

console.log(solution([1,2,3,4,5])); // [1]
console.log(solution([1,3,2,4,2])); // [1,2,3]