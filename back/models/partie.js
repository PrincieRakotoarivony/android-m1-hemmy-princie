const { ObjectId } = require("mongodb");
const { default: mongoose } = require("mongoose");
const { MATHEMATIQUE, ANIMAL, PAYS } = require("../utils/constantes");
const Animal = require("./animal");
const Pays = require("./pays");

const PartieSchema = new mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    id_utilisateur: {type: mongoose.Schema.Types.ObjectId, required: true, ref: 'Utilisateur'},
    id_categorie: {type: mongoose.Schema.Types.ObjectId, required: true, ref: 'Partie'},
    detail: [],
    score: Number
});

async function get(id){
    const p = await Partie.findById(id).exec();
    if(!p) throw new Error("Partie invalide");
    return p;
}

PartieSchema.statics.getById = async function(id){
    return get(id);
}

PartieSchema.statics.findAll = async function(){
    const p = await Partie.find();
    return p;
}


PartieSchema.statics.random = function(animalLength, notIn) {
    let rand = Math.floor(Math.random() * animalLength);
    while(notIn.includes(rand)){
        rand = Math.floor(Math.random() * animalLength);
    }
    return rand;
}

PartieSchema.statics.quizIndexes = function(animalLength) {
    let listQuiz = [];
    let notInTarget = [];
    for (let index = 0; index < 10; index++) {
        let target = Partie.random(animalLength, notInTarget);
        notInTarget.push(target);
        let suggestions = [];
        let notInSuggestion = [target];
        for (let index1 = 0; index1 < 3; index1++) {
            let suggestion = Partie.random(animalLength, notInSuggestion);
            suggestions.push(suggestion);
            notInSuggestion.push(suggestion);
        }
        listQuiz.push({ target, suggestions });
    }
    return listQuiz;
}

PartieSchema.statics.createPartie = async function(id_categorie) {
    let data = [];
    if (id_categorie.equals(ANIMAL)) data = await Animal.find();
    if (id_categorie.equals(PAYS)) data = await Pays.find();
    let listQuiz = Partie.quiz(data);
    return listQuiz;
}


PartieSchema.statics.quiz = function(data){
    let quizesIndexes = Partie.quizIndexes(data.length);
    let quizes = [];
    quizesIndexes.map((quizPart) => {
        let target = data[quizPart.target];
        target.isCorrect = true;
        console.log('target', target)
        let suggestions = [target];
        quizPart.suggestions.map((sugg) => {
            let d = data[sugg];
            d.isCorrect = false;
            suggestions.push(d);
        });
        quizes.push({target, suggestions});
    });
    return quizes;
}

const Partie = mongoose.model('Partie', PartieSchema);

module.exports = Partie;