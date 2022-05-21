const { ObjectId } = require("mongodb");
const { default: mongoose } = require("mongoose");

const CategorieSchema = new mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    nom: {type: String, required: [true, "Nom obligatoire"]},
    description: String,
    img: String,
});

CategorieSchema.statics.getById = async function(id){
    const p = await Categorie.findById(id).exec();
    if(!p) throw new Error("Categorie invalide");
    return p;
}

CategorieSchema.statics.findAll = async function(){
    const p = await Categorie.find();
    return p;
}


const Categorie = mongoose.model('Categorie', CategorieSchema);

module.exports = Categorie;