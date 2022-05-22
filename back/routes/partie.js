const express = require('express');
const { default: mongoose } = require('mongoose');
const { Categorie, Utilisateur, Partie, Animal } = require('../models');
const { responseBuilder, tools, mail } = require('../utils');
const { constantes } = require('../utils');
const router = express.Router();

router.get('/', async function (req, res) {
    try {
        const token = tools.extractToken(req.headers.authorization);
        const u = await Utilisateur.findUser(token);
        const result = await Categorie.find();
        res.json(responseBuilder.success(result));
    } catch (error) {
        res.json(responseBuilder.error(error));
    }
});

router.post('/save', async function (req, res) {
    try {
        const token = tools.extractToken(req.headers.authorization);
        const u = await Utilisateur.findUser(token);
        const categorie = await Categorie.findById(req.body.id_categorie);
        const data = {
            id_utilisateur: u._id,
            id_categorie: categorie._id,
            detail: [],
        }
        //const partie = new Partie(data);
        //partie._id = new mongoose.Types.ObjectId();
        const detail = await Partie.createPartie(categorie._id);
        //partie.detail = detail;
        //await partie.save();
        res.json(responseBuilder.success(detail));
    } catch (error) {
        console.error(error);
        res.json(responseBuilder.error(error));
    }
});

router.get('/:id', async function (req, res) {
    try {
        const token = tools.extractToken(req.headers.authorization);
        const u = await Utilisateur.findUser(token);
        const result = await Categorie.getById(req.params.id);
        res.json(responseBuilder.success(result));
    } catch (error) {
        res.json(responseBuilder.error(error));
    }
});

router.put('/:id', async function (req, res) {
    try {
        const token = tools.extractToken(req.headers.authorization);
        const u = await Utilisateur.findUser(token);
        const categorie = await Categorie.getById(req.params.id);
        if (!u.profile.equals(constantes.PROFILE_RESTAURANT) || !u.restaurant.equals(categorie.restaurant))
            throw new Error("Pas d'autorisation");
        ["nom", "description", "cout", "prix", "img", "visible"].forEach(key => {
            categorie[key] = req.body[key];
        });
        await categorie.save();
        res.json(responseBuilder.success("Plat modifié"));
    } catch (error) {
        res.json(responseBuilder.error(error));
    }
});


module.exports = router;