const { default: mongoose } = require("mongoose");

const AbonnementSchema = new mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    id_user: {type: mongoose.Schema.Types.ObjectId, required: true, ref: 'Utilisateur'},
    id_theme: {type: mongoose.Schema.Types.ObjectId, required: true, ref: 'Theme'},
    notif: {type: Boolean, default: true}
});


const Abonnement = mongoose.model('Abonnement', AbonnementSchema);




module.exports = Abonnement;