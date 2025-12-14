import  {initializeApp} from "firebase-admin/app";
import {getMessaging} from "firebase-admin/messaging";  
import {getFirestore} from "firebase-admin/firestore";
import serviceAccount from "./serviceAccountKey.json" assert {type: "json"};


const admin = require("firebase-admin");
const serviceAccount = require("./serviceAccountKey.json");

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount)
});
const messaging = getMessaging();
const db = admin.firestore();
module.exports = { admin, db };