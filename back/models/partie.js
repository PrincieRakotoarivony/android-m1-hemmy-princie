const { ObjectId } = require("mongodb");
const { default: mongoose } = require("mongoose");

const PartieSchema = new mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    id_utilisateur: {type: mongoose.Schema.Types.ObjectId, required: true, ref: 'Utilisateur'},
    id_categorie: {type: mongoose.Schema.Types.ObjectId, required: true, ref: 'Categorie'},
    detail: [],
    score: Int16Array
});

async function get(id){
    const p = await Categorie.findById(id).exec();
    if(!p) throw new Error("Categorie invalide");
    return p;
}

PartieSchema.statics.getById = async function(id){
    return get(id);
}

PartieSchema.statics.findAll = async function(){
    const p = await Categorie.find();
    return p;
}


const Categorie = mongoose.model('Categorie', PartieSchema);

module.exports = Categorie;