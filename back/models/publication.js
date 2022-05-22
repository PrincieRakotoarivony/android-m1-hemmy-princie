const moment = require('moment');
const sha1 = require('sha1');
const { default: mongoose } = require("mongoose");

const PublicationSchema = new mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    titre: String,
    description: String,
    img: String,
    id_theme: {type: mongoose.Schema.Types.ObjectId, required: true, ref: 'Theme'},
    id_user: {type: mongoose.Schema.Types.ObjectId, required: true, ref: 'Utilisateur'},
});

PublicationSchema.statics.findAll = async function (params) {
    return await Publication.find({
        ...params.crt
    })
        .skip((params.pageNumber - 1) * params.nPerPage)
        .limit(params.nPerPage);
}


const Publication = mongoose.model('Publication', PublicationSchema);




module.exports = Publication;