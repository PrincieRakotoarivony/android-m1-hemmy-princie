const moment = require('moment');
const sha1 = require('sha1');
const { default: mongoose } = require("mongoose");
const Abonnement = require('./abonnement');

const PublicationSchema = new mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    titre: String,
    description: String,
    img: String,
    id_theme: { type: mongoose.Schema.Types.ObjectId, required: true, ref: 'Theme' },
    id_user: { type: mongoose.Schema.Types.ObjectId, required: true, ref: 'Utilisateur' },
});

PublicationSchema.statics.findAll = async function (params) {
    return await Publication.find({
        ...params.crt
    })
        .skip((params.pageNumber - 1) * params.nPerPage)
        .limit(params.nPerPage);
}

PublicationSchema.statics.createPublication = async function (params, user) {
    // save publication
    params.img = 'imgs/publication/' + params.img;
    const publication = new Publication(params);
    publication._id = new mongoose.Types.ObjectId();
    publication.id_user = user._id;
    publication.id_theme = new mongoose.Types.ObjectId(params.id_theme);
    await publication.save();

    // save abonnement
    const abonnement = new Abonnement({notif: true, status: 1, id_theme: publication.id_theme, id_user: user._id});
    abonnement._id = new mongoose.Types.ObjectId();
    await abonnement.save();
    return publication._id;
}


const Publication = mongoose.model('Publication', PublicationSchema);




module.exports = Publication;