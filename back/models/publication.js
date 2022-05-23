const moment = require('moment');
const sha1 = require('sha1');
const { default: mongoose } = require("mongoose");

const PublicationSchema = new mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    titre: {type: String, trim: true, required: [true, "Titre obligatoire"]},
    description: {type: String, trim: true, required: [true, "Description obligatoire"]},
    img: String,
    datePub: {type: Date, default: Date.now()},
    id_theme: {type: mongoose.Schema.Types.ObjectId, required: true, ref: 'Theme'},
    id_user: {type: mongoose.Schema.Types.ObjectId, required: true, ref: 'Utilisateur'},
});

PublicationSchema.statics.findAll = async function (id_user, params) {
    const crt = {id_user};
    if(params.id_theme) 
        crt.id_theme = new mongoose.Types.ObjectId(params.id_theme);
    if(params.search){
        const search = params.search.trim();
        const regex = { $regex: new RegExp(`.*${search}.*`), $options: "i" };
        crt.titre = regex;
        crt.description = regex;
    }    

    return await Publication.find(...crt)
    .populate('id_theme')
    .populate('id_user')
    .sort({datePub: -1})
    .skip((params.pageNumber - 1) * params.nPerPage)
    .limit(params.nPerPage);
}

PublicationSchema.statics.findDetailsById = async function(id){
    return await Publication.findById({_id: id})
    .populate('id_theme')
    .populate('id_user');
}


const Publication = mongoose.model('Publication', PublicationSchema);




module.exports = Publication;