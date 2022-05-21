const express = require('express');
const { Utilisateur, Token } = require('../models');
const {responseBuilder, tools} = require('../utils');
 const router = express.Router();

router.post('/login', async function(req, res){
    try{
        const u = new Utilisateur(req.body);
        const result = await u.login();
        res.json(responseBuilder.success(result));
    } catch(err){
        res.json(responseBuilder.error(err));
    }
});

router.post('/signUp', async function(req, res){
    try{
        const u = new Utilisateur(req.body);
        await u.signUp({confirmMdp: req.body.confirmMdp});
        res.json(responseBuilder.success(u._id));
    } catch(error){
        if (error.name === 'MongoServerError' && error.code === 11000) {
            error = new Error('Email déjà utilisé');
        } 
        res.json(responseBuilder.error(error));
    }
});

router.get('/user', async function(req, res){
    try{
        const token = tools.extractToken(req.headers.authorization);
        const u = await Utilisateur.findUser(token);
        res.json(responseBuilder.success(u));
    } catch(err){
        res.json(responseBuilder.error(err));
    }
});

router.delete('/logout', async function(req, res){
    try{
        const token = tools.extractToken(req.headers.authorization);
        await Token.rmvToken(token);
        res.json(responseBuilder.success("Utilisateur déconnecté"));
    } catch(error){
        console.log('error', error)
        res.json(responseBuilder.error(error));
    }
});


module.exports = router;