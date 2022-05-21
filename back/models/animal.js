const { ObjectId } = require("mongodb");
const { default: mongoose } = require("mongoose");

const AnimalSchema = new mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    nom: {type: String, required: [true, "Nom obligatoire"]},
    description: String,
    img: String,
});

AnimalSchema.statics.findAll = async function(){
    const p = await Animal.find();
    return p;
}


const Animal = mongoose.model('Animal', AnimalSchema);

module.exports = Animal;