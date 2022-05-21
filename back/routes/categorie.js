const express = require('express');
const { default: mongoose } = require('mongoose');
const { Categorie, Utilisateur } = require('../models');
const {responseBuilder, tools, mail} = require('../utils');
const { constantes } = require('../utils');
 const router = express.Router();

router.get('/', async function(req, res){
    try{
        const result = await Categorie.find();
        res.json(responseBuilder.success(result));
    } catch(error){
        res.json(responseBuilder.error(error));
    }
});

router.post('/save', async function(req, res){
    try{
        const token = tools.extractToken(req.headers.authorization);
        const u = await Utilisateur.findUser(token);
        if(!u.profile.equals(constantes.PROFILE_RESTAURANT)) 
            throw new Error("Pas d'autorisation");
        const categorie = new Categorie(req.body);
        categorie._id = new mongoose.Types.ObjectId();
        categorie.restaurant = u.restaurant;
        await categorie.save();
        res.json(responseBuilder.success(categorie._id));
    } catch(error){
        res.json(responseBuilder.error(error));
    }
});

router.get('/:id', async function(req, res){
    try{
        const result = await Categorie.getById(req.params.id);
        res.json(responseBuilder.success(result));
    } catch(error){
        res.json(responseBuilder.error(error));
    }
});

router.put('/:id', async function(req, res){
    try{
        const token = tools.extractToken(req.headers.authorization);
        const u = await Utilisateur.findUser(token);
        const categorie = await Categorie.getById(req.params.id);
        if(!u.profile.equals(constantes.PROFILE_RESTAURANT) || !u.restaurant.equals(categorie.restaurant)) 
            throw new Error("Pas d'autorisation");
        ["nom", "description", "cout", "prix", "img", "visible"].forEach(key => {
            categorie[key] = req.body[key];
        });
        await categorie.save();
        res.json(responseBuilder.success("Plat modifié"));
    } catch(error){
        res.json(responseBuilder.error(error));
    }
});


module.exports = router;