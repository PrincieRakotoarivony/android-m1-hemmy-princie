var admin = require("firebase-admin");

var serviceAccount = require("./../dark-axe-351022-firebase-adminsdk-gh0do-79ce2554b2.json");


admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
});

module.exports = admin;