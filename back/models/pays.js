const { ObjectId } = require("mongodb");
const { default: mongoose } = require("mongoose");

const PaysSchema = new mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    nom: {type: String, required: [true, "Nom obligatoire"]},
    description: String,
    img: String,
});

PaysSchema.statics.findAll = async function(){
    const p = await Pays.find();
    return p;
}


const Pays = mongoose.model('Pays', PaysSchema);

module.exports = Pays;