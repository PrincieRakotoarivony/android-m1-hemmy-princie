const express = require('express');
const { Utilisateur, Cours } = require('../models');
const {responseBuilder, tools} = require('../utils');
 const router = express.Router();

router.post('/', async function(req, res){
    try{
        const token = tools.extractToken(req.headers.authorization);
        const u = await Utilisateur.findUser(token);
        const result = await Cours.findAll(req.body);
        res.json(responseBuilder.success(result));
    } catch(error){
        res.json(responseBuilder.error(error));
    }
});


module.exports = router;