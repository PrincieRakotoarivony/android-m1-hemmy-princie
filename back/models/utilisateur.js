const { default: mongoose } = require("mongoose");
const sha1 = require('sha1');
const moment = require('moment');
const { constantes } = require("../utils");
const Token = require("./token");
const { generateRandomCode } = require("../utils/tools");
const { sendMail } = require("../utils/mail");

const UtilisateurSchema = new mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    mail: {
        type: String, 
        required: [true, 'Adresse e-mail obligatoire'], 
        trim: true,
        match: [/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/, 'Adresse e-mail invalide'],
        index: [true, 'Adresse e-mail déjà utilisée'],
        unique: [true]
    },
    nom: {
        type: String, 
        required: [true, 'Nom obligatoire'], 
        trim: true
    },
    prenom: {type: String, trim: true},
    dateNaissance: {type: Date/*, required: [true, 'Date de naissance obligatoire']*/},
    mdp: {type: String, required: [true, 'Mot de passe obligatoire']},
});


UtilisateurSchema.methods.signUp = async function (params){
    if(!params.confirmMdp || params.confirmMdp != this.mdp)
        throw new Error("Mots de passe non conformes");
    const mdpNotHashed = this.mdp;    
    this.mdp = sha1(this.mdp);
    this._id = new mongoose.Types.ObjectId();
    await this.save();
    this.mdp = mdpNotHashed;
    return await this.login();
} 

UtilisateurSchema.methods.login = async function (){
    const u = await Utilisateur.findOne({
        mail: new RegExp(`^${this.mail}$`, "i"), 
        mdp: sha1(this.mdp)
    }).exec();
    if(!u) throw new Error("Nom d'utilisateur ou mot de passe invalide");
    const token = new Token({
        _id: new mongoose.Types.ObjectId(), 
        utilisateur: u._id, 
        dateExpiration: moment().add(1, 'y')
    });
    const tokenStr = sha1(u._id + moment());
    token.token = sha1(tokenStr);
    await token.save();
    return {user: u, token: tokenStr};
}

UtilisateurSchema.statics.findUser = async function (token){
    const t = await Token.findToken(token);
    if(!t) throw new Error("Token invalide");
    return t.utilisateur;
}


const Utilisateur = mongoose.model('Utilisateur', UtilisateurSchema);


module.exports = Utilisateur;
