const { ObjectId } = require("mongodb");
const { default: mongoose } = require("mongoose");
const { MATHEMATIQUE, ANIMAL, PAYS } = require("../utils/constantes");
const { quizes, quizMath } = require("../utils/quiz");
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


// MATHEMATICS

PartieSchema.statics.createPartie = async function(id_categorie) {
    let data = [];
    let listQuiz = [];
    if (id_categorie.equals(MATHEMATIQUE)) listQuiz = quizMath();
    else{
        if (id_categorie.equals(ANIMAL)) data = await Animal.find();
        if (id_categorie.equals(PAYS)) data = await Pays.find();
        listQuiz = quizes(data);
    }
    return listQuiz;
}


const Partie = mongoose.model('Partie', PartieSchema);

module.exports = Partie;