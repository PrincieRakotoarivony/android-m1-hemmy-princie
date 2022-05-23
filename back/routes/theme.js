const express = require('express');
const { default: mongoose } = require('mongoose');
const { Theme, Utilisateur, Abonnement } = require('../models');
const {responseBuilder, tools, mail} = require('../utils');
const { constantes } = require('../utils');
 const router = express.Router();

router.post('/', async function(req, res){
    try{
        const token = tools.extractToken(req.headers.authorization);
        const u = await Utilisateur.findUser(token);
        const result = await Theme.findAll(u._id, req.body);
        res.json(responseBuilder.success(result));
    } catch(error){
        res.json(responseBuilder.error(error));
    }
});

router.post('/save', async function(req, res){
    try{
        const token = tools.extractToken(req.headers.authorization);
        const u = await Utilisateur.findUser(token);
        req.body.img = 'imgs/theme/'+req.body.img;
        const theme = new Theme(req.body);
        theme._id = new mongoose.Types.ObjectId();
        await theme.save();
        res.json(responseBuilder.success(theme._id));
    } catch(error){
        res.json(responseBuilder.error(error));
    }
});

router.post('/:id/subscribe', async function(req, res){
    try{
        const token = tools.extractToken(req.headers.authorization);
        const u = await Utilisateur.findUser(token);
        const theme = await Theme.findById(req.params.id);
        const abonm = await theme.subscribe(u._id);
        res.json(responseBuilder.success(abonm._id));
    } catch(error){
        res.json(responseBuilder.error(error));
    }
});

router.post('/:id/notif', async function(req, res){
    try{
        const token = tools.extractToken(req.headers.authorization);
        const u = await Utilisateur.findUser(token);
        const theme = await Theme.findById(req.params.id);
        await theme.changeNotif(u._id, req.body.notif);
        console.log("change notif");
        res.json(responseBuilder.success("ok"));
    } catch(error){
        res.json(responseBuilder.error(error));
    }
});

router.delete('/:id/unsubscribe', async function(req, res){
    try{
        const token = tools.extractToken(req.headers.authorization);
        const u = await Utilisateur.findUser(token);
        const theme = await Theme.findById(req.params.id);
        await theme.unsubscribe(u._id);
        res.json(responseBuilder.success("ok"));
    } catch(error){
        res.json(responseBuilder.error(error));
    }
});

router.get('/:id', async function(req, res){
    try{
        const token = tools.extractToken(req.headers.authorization);
        const u = await Utilisateur.findUser(token);
        const result = await Theme.findDetailsById(u._id, req.params.id);
        res.json(responseBuilder.success(result));
    } catch(error){
        res.json(responseBuilder.error(error));
    }
});


module.exports = router;