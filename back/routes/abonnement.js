const express = require('express');
const { default: mongoose } = require('mongoose');
const { Theme, Utilisateur, Abonnement } = require('../models');
const { responseBuilder, tools, mail } = require('../utils');
const { constantes } = require('../utils');
const router = express.Router();

router.post('/', async function (req, res) {
    try {
        const token = tools.extractToken(req.headers.authorization);
        const u = await Utilisateur.findUser(token);
        const result = await Theme.findAll(req.body);
        res.json(responseBuilder.success(result));
    } catch (error) {
        res.json(responseBuilder.error(error));
    }
});

router.post('/save', async function (req, res) {
    try {
        const token = tools.extractToken(req.headers.authorization);
        const u = await Utilisateur.findUser(token);
        const theme = new Theme(req.body);
        theme._id = new mongoose.Types.ObjectId();
        await theme.save();
        res.json(responseBuilder.success(theme._id));
    } catch (error) {
        res.json(responseBuilder.error(error));
    }
});

router.put('/:id/settingNotification', async function(req, res){
    try{
        const token = tools.extractToken(req.headers.authorization);
        const u = await Utilisateur.findUser(token);
        const result = await Abonnement.findById(req.params.id);
        result.notif = !result.notif;
        await result.save();
        res.json(responseBuilder.success(result));
    } catch(error){
        res.json(responseBuilder.error(error));
    }
});

router.put('/:id/settingStatus', async function(req, res){
    try{
        const token = tools.extractToken(req.headers.authorization);
        const u = await Utilisateur.findUser(token);
        const result = await Abonnement.findById(req.params.id);
        result.status = result.status === 1 ? 0 : 1;
        await result.save();
        res.json(responseBuilder.success(result));
    } catch(error){
        res.json(responseBuilder.error(error));
    }
});


module.exports = router;