const express = require('express');
const { default: mongoose } = require('mongoose');
const { Publication, Utilisateur } = require('../models');
const {responseBuilder, tools, mail} = require('../utils');
const { constantes } = require('../utils');
 const router = express.Router();

router.post('/', async function(req, res){
    try{
        const token = tools.extractToken(req.headers.authorization);
        const u = await Utilisateur.findUser(token);
        const result = await Publication.findAll(req.body);
        res.json(responseBuilder.success(result));
    } catch(error){
        res.json(responseBuilder.error(error));
    }
});

router.post('/save', async function(req, res){
    try{
        const token = tools.extractToken(req.headers.authorization);
        const u = await Utilisateur.findUser(token);
        req.body.img = 'imgs/publication/'+req.body.img;
        const publication = new Publication(req.body);
        publication._id = new mongoose.Types.ObjectId();
        publication.id_user = u._id;
        publication.id_theme = new mongoose.Types.ObjectId(req.body.id_theme); 
        await publication.save();
        res.json(responseBuilder.success(publication._id));
    } catch(error){
        res.json(responseBuilder.error(error));
    }
});

router.get('/:id', async function(req, res){
    try{
        const token = tools.extractToken(req.headers.authorization);
        const u = await Utilisateur.findUser(token);
        const result = await Publication.findById(req.params.id);
        res.json(responseBuilder.success(result));
    } catch(error){
        res.json(responseBuilder.error(error));
    }
});


module.exports = router;