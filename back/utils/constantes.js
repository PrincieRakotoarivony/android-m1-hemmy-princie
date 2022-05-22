const { default: mongoose } = require("mongoose");

module.exports = {
    MATHEMATIQUE: new mongoose.Types.ObjectId("6288ed94500c7da9eb7e6650"),
    ANIMAL: new mongoose.Types.ObjectId("6288ed9a500c7da9eb7e6654"),
    PAYS: new mongoose.Types.ObjectId("6288edb7500c7da9eb7e6658"),
    ETATS_COMMANDE: {COMMANDEE: 0, EN_PREPARATION: 1, PREPAREE: 2, ASSIGNEE_LIVREUR: 3, EN_LIVRAISON: 4, LIVREE: 5}
}