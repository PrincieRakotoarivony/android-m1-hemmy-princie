const express = require('express');
const { default: mongoose } = require('mongoose');
const { Comment, Utilisateur } = require('../models');
const {responseBuilder, tools, mail} = require('../utils');
const { constantes } = require('../utils');
 const router = express.Router();

router.post('/', async function(req, res){
    try{
        const token = tools.extractToken(req.headers.authorization);
        const u = await Utilisateur.findUser(token);
        const result = await Comment.find({id_pub: new mongoose.Types.ObjectId(req.body.id_pub)});
        res.json(responseBuilder.success(result));
    } catch(error){
        res.json(responseBuilder.error(error));
    }
});

router.post('/save', async function(req, res){
    try{
        const token = tools.extractToken(req.headers.authorization);
        const u = await Utilisateur.findUser(token);
        const comment = new Comment(req.body);
        comment._id = new mongoose.Types.ObjectId();
        comment.id_user = u._id;
        await comment.save();
        res.json(responseBuilder.success(comment._id));
    } catch(error){
        res.json(responseBuilder.error(error));
    }
});



module.exports = router;