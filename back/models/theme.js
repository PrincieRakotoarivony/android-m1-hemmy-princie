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

ThemeSchema.statics.createTheme = async function (params, user) {
    params.img = 'imgs/theme/' + params.img;
    const theme = new Theme(params);
    theme._id = new mongoose.Types.ObjectId();
    await theme.save();

    // abonnement (id, idjoueur, idtheme, notif (true, false), status (1 par defaut, 0 rehefa mi-se desabonne))
}


const Theme = mongoose.model('Theme', ThemeSchema);

module.exports = Theme;