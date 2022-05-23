const { ObjectId } = require("mongodb");
const { default: mongoose } = require("mongoose");
const Abonnement = require("./abonnement");

const ThemeSchema = new mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    nom: { type: String, required: [true, "Nom obligatoire"] },
    description: String,
    img: String
});

ThemeSchema.statics.findAll = async function (idUser, params) {
    let match = {};
    if(params.search) {
        const search = params.search.trim();
        const regex = { $regex: new RegExp(`.*${search}.*`), $options: "i" };
        match = {$or: [{nom: regex}, {description: regex}]};
    }

    const aggregateParams = [
        {$match: match},
        {
            $lookup: {
                from: "abonnements",
                localField: "_id",
                foreignField: "id_theme",
                pipeline: [
                    {$match: {id_user: idUser}}
                ],
                as: "abonm"
            }
        },
        {$unwind: {path: "$abonm", preserveNullAndEmptyArrays: params.all}}
    ];

    return await Theme.aggregate(aggregateParams)
        .sort({nom: 1})
        .skip((params.pageNumber - 1) * params.nPerPage)
        .limit(params.nPerPage);
}

ThemeSchema.statics.findDetailsById = async function (idUser, id) {
    let match = {};
    
    const aggregateParams = [
        {$match: {_id: new mongoose.Types.ObjectId(id)}},
        {
            $lookup: {
                from: "abonnements",
                localField: "_id",
                foreignField: "id_theme",
                pipeline: [
                    {$match: {id_user: idUser}}
                ],
                as: "abonm"
            }
        },
        {$unwind: {path: "$abonm", preserveNullAndEmptyArrays: true}}
    ];

    const result = await Theme.aggregate(aggregateParams);
    return result.length == 0 ? null : result[0];
}

ThemeSchema.methods.subscribe = async function (idUser){
    const abonm = new Abonnement({
        _id: new mongoose.Types.ObjectId(),
        id_user: idUser,
        id_theme: this._id
    });
    await abonm.save();
    return abonm;
}

ThemeSchema.methods.unsubscribe = async function (idUser){
    await Abonnement.deleteMany({id_user: idUser, id_theme: this._id});
}

const Theme = mongoose.model('Theme', ThemeSchema);

module.exports = Theme;