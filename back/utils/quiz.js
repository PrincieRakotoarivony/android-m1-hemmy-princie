
// ANIMAL OR COUNTRY
function random(animalLength, notIn) {
  let rand = Math.floor(Math.random() * animalLength);
  while(notIn.includes(rand)){
      rand = Math.floor(Math.random() * animalLength);
  }
  return rand;
}

function quizIndexes(animalLength) {
  let listQuiz = [];
  let notInTarget = [];
  for (let index = 0; index < 10; index++) {
      let target = random(animalLength, notInTarget);
      notInTarget.push(target);
      let suggestions = [];
      let notInSuggestion = [target];
      for (let index1 = 0; index1 < 3; index1++) {
          let suggestion = random(animalLength, notInSuggestion);
          suggestions.push(suggestion);
          notInSuggestion.push(suggestion);
      }
      listQuiz.push({ target, suggestions });
  }
  return listQuiz;
}

function quizes(data){
  let quizesIndexes = quizIndexes(data.length);
  let quizes = [];
  quizesIndexes.map((quizPart) => {
      let target = JSON.parse(JSON.stringify(data[quizPart.target]));
      let suggCorrect = JSON.parse(JSON.stringify(target));
      delete target.nom;
      suggCorrect.isCorrect = true;
      delete suggCorrect.image;
      let suggestions = [];
      quizPart.suggestions.map((sugg) => {
          let d = JSON.parse(JSON.stringify(data[sugg]));
          delete d.image;
          d.isCorrect = false;
          suggestions.push(d);
      });
      let posTarget = Math.floor(Math.random() * 4);
      suggestions.splice(posTarget, 0, suggCorrect);
      quizes.push({target, suggestions});
  });
  return quizes;
}

function makeid(length) {
  var result = '';
  var characters = '23456789';
  var charactersLength = characters.length;

  for (var i = 0; i < length; i++) {
      result += characters.charAt(Math.floor(Math.random() * charactersLength));
  }
  return result;
}

function randomOperation() {
  var result = '';
  var characters = '+-/*';
  var charactersLength = characters.length;
  for (var i = 0; i < 1; i++) {
      result += characters.charAt(Math.floor(Math.random() * charactersLength));
  }
  return result;
}

function nextOperation(level) {
  let operation = randomOperation();
  return operation + makeid(level);
}

function quizMath() {
  let quizes = [];
  let level = 2;
  for (let index = 0; index < 10; index++) {
      let calcul = makeid(level) + nextOperation(level - 1);
      if (index > 3) {
          level = 2;            
          calcul += nextOperation(level - 1);
      }
      if (index > 6) {
          level = 3;
          calcul += nextOperation(level - 1);
      }
      let quiz = { target: {nom: calcul}, suggestions: [] };
      const eval1 = Math.floor(eval(calcul)).toString();
      quiz.suggestions.push({isCorrect: true, nom: eval1});
      for (let index1 = 0; index1 < 3; index1++) {
          let suggestion = { isCorrect: false };
          let calcul1 = makeid(level);
          suggestion.nom = calcul1;
          quiz.suggestions.push({ ...suggestion });
      }
      quizes.push(quiz);
      
  }
  return quizes
}
module.exports = {quizes, quizMath};