const express = require('express');
const { default: mongoose } = require('mongoose');
const { Animal, Utilisateur, Cours } = require('../models');
const {responseBuilder, tools, mail} = require('../utils');
const { constantes } = require('../utils');
 const router = express.Router();

router.get('/', async function(req, res){
    try{
        const token = tools.extractToken(req.headers.authorization);
        const u = await Utilisateur.findUser(token);
        const result = await Cours.findAll(req.params);
        res.json(responseBuilder.success(result));
    } catch(error){
        res.json(responseBuilder.error(error));
    }
});


module.exports = router;