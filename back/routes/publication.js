const express = require('express');
const { default: mongoose } = require('mongoose');
const { Publication, Utilisateur, Comment } = require('../models');
const {responseBuilder, tools, mail} = require('../utils');
const { constantes } = require('../utils');
 const router = express.Router();

router.post('/', async function(req, res){
    try{
        const token = tools.extractToken(req.headers.authorization);
        const u = await Utilisateur.findUser(token);
        const result = await Publication.findAll(u._id, req.body);
        res.json(responseBuilder.success(result));
    } catch(error){
        res.json(responseBuilder.error(error));
    }
});

router.post('/save', async function(req, res){
    try{
        const token = tools.extractToken(req.headers.authorization);
        const user = await Utilisateur.findUser(token);
        const publication = await Publication.createPublication(req.body, user);
        res.json(responseBuilder.success(publication._id));
    } catch(error){
        res.json(responseBuilder.error(error));
    }
});

router.get('/:id', async function(req, res){
    try{
        const token = tools.extractToken(req.headers.authorization);
        const u = await Utilisateur.findUser(token);
        console.log(req.params.id);
        const result = await Publication.findDetailsById(u._id, new mongoose.Types.ObjectId(req.params.id));
        res.json(responseBuilder.success(result));
    } catch(error){
        console.error(error);
        res.json(responseBuilder.error(error));
    }
});

router.post('/comment', async function(req, res){
    try{
        const token = tools.extractToken(req.headers.authorization);
        const u = await Utilisateur.findUser(token);
        const pub = await Publication.findById(new mongoose.Types.ObjectId(req.body.id_pub));
        const comment = await pub.comment(u._id, new Comment(req.body));
        res.json(responseBuilder.success(comment._id));
    } catch(error){
        res.json(responseBuilder.error(error));
    }
});




module.exports = router;