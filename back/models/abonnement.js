<<<<<<< .mine
const moment = require('moment');
const sha1 = require('sha1');
const { default: mongoose } = require("mongoose");

const AbonnementSchema = new mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    notif: {type: Boolean, default: true},
    status: {type: Number, default: 1},
    id_theme: {type: mongoose.Schema.Types.ObjectId, required: true, ref: 'Theme'},
    id_user: {type: mongoose.Schema.Types.ObjectId, required: true, ref: 'Utilisateur'},
});

// abonnement (id, idjoueur, idtheme, notif (true, false), status (1 par defaut, 0 rehefa mi-se desabonne))

AbonnementSchema.statics.findAll = async function (params) {
    return await Abonnement.find({
        ...params.crt
    })
        .skip((params.pageNumber - 1) * params.nPerPage)
        .limit(params.nPerPage);
}


const Abonnement = mongoose.model('Abonnement', AbonnementSchema);




module.exports = Abonnement;
=======
const { default: mongoose } = require("mongoose");

const AbonnementSchema = new mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    id_user: {type: mongoose.Schema.Types.ObjectId, required: true, ref: 'Utilisateur'},
    id_theme: {type: mongoose.Schema.Types.ObjectId, required: true, ref: 'Theme'},
    notif: {type: Boolean, default: true}
});


const Abonnement = mongoose.model('Abonnement', AbonnementSchema);




module.exports = Abonnement;













>>>>>>> .theirs
