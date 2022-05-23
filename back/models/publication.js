const moment = require('moment');
const sha1 = require('sha1');
const { default: mongoose } = require("mongoose");
const Abonnement = require('./abonnement');
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
        crt["$or"] = [{titre: regex}, {description: regex}];
    }    

    const aggregateParams = [
        {$match: crt},
        {
            $lookup: {
                from: "themes",
                localField: "id_theme",
                foreignField: "_id",
                as: "theme"
            }
        },
        {$unwind: {path: "$theme"}},
        {
            $lookup: {
                from: "utilisateurs",
                localField: "id_user",
                foreignField: "_id",
                as: "user"
            }
        },
        {$unwind: {path: "$user"}},
        {
            $lookup: {
                from: "abonnements",
                localField: "id_theme",
                foreignField: "id_theme",
                pipeline: [
                    {$match: {id_user}}
                ],
                as: "abonm"
            }
        },
        {$unwind: {path: "$abonm", preserveNullAndEmptyArrays: crt.id_theme ? true : false}}
    ];

    return await Publication.aggregate(aggregateParams)
    .sort({datePub: -1})
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

PublicationSchema.statics.findDetailsById = async function(id_user, id){
    let aggregateParams = [
        {$match: {_id: id}},
        {
            $lookup: {
                from: "themes",
                localField: "id_theme",
                foreignField: "_id",
                as: "theme"
            }
        },
        {$unwind: {path: "$theme"}},
        {
            $lookup: {
                from: "utilisateurs",
                localField: "id_user",
                foreignField: "_id",
                as: "user"
            }
        },
        {$unwind: {path: "$user"}},
        {
            $lookup: {
                from: "abonnements",
                localField: "id_theme",
                foreignField: "id_theme",
                pipeline: [
                    {$match: {id_user}}
                ],
                as: "abonm"
            }
        },
        {$unwind: {path: "$abonm", preserveNullAndEmptyArrays: true}}
    ];

    const result = await Publication.aggregate(aggregateParams);
    if(result.length == 0) throw new Error("Publication introuvable");
    const pub = result[0];

    aggregateParams = [
        {$match: {id_pub: id}},
        {
            $lookup: {
                from: "utilisateurs",
                localField: "id_user",
                foreignField: "_id",
                as: "user"
            }
        },
        {$unwind: {path: "$user"}}
    ];
    pub.comments = await Comment
        .aggregate(aggregateParams)
        .sort({dateComment: -1});
    return pub;    
}

PublicationSchema.methods.comment = async function (id_user, comment){
    comment.id_pub = this._id;
    comment.id_user = id_user;
    comment._id = new mongoose.Types.ObjectId();
    await comment.save();
    return comment;
}

const Publication = mongoose.model('Publication', PublicationSchema);




module.exports = Publication;