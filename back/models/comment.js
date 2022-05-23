const { default: mongoose } = require("mongoose");

const CommentSchema = new mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    dateComment: {type: Date, default: Date.now()},
    id_user: {type: mongoose.Schema.Types.ObjectId, required: true, ref: 'Utilisateur'},
    id_pub: {type: mongoose.Schema.Types.ObjectId, required: true, ref: 'Publication'},
    content: {type: String, trim: true, required: [true, "Contenu obligatoire"]}
});

const Comment = mongoose.model('Comment', CommentSchema);

module.exports = Comment;