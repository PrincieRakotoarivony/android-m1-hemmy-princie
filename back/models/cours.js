const { ObjectId } = require("mongodb");
const { default: mongoose } = require("mongoose");

const CoursSchema = new mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    description: String,
    img: String,
    link: { type: String, required: true },
});

CoursSchema.statics.findAll = async function (params) {
    return await Cours
        .find({
            ...params.crt,
            nom: { $regex: new RegExp(`.*${params.search}.*`), $options: "i" },
        })
        .sort({ nom: 1 })
        .skip((params.pageNumber - 1) * params.nPerPage)
        .limit(params.nPerPage)
        .toArray();;
}


const Cours = mongoose.model('Cours', CoursSchema);

module.exports = Cours;