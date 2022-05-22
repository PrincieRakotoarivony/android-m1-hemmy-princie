const { ObjectId } = require("mongodb");
const { default: mongoose } = require("mongoose");

const ThemeSchema = new mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    nom: { type: String, required: [true, "Nom obligatoire"] },
    description: String,
    img: String
});

ThemeSchema.statics.findAll = async function (params) {
    return await Theme.find({
        ...params.crt
    })
        .skip((params.pageNumber - 1) * params.nPerPage)
        .limit(params.nPerPage);
}


const Theme = mongoose.model('Theme', ThemeSchema);

module.exports = Theme;