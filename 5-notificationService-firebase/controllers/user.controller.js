const UserService = require("../services/user.service");
const express = require("express");
const router = express.Router();
router.post("/save-fcm-token", async (req, res) => {
    try{
        const {fcmToken} = req.body;
        const userId = req.user.id;
        await UserService.saveFcmToken(userId, fcmToken);
        res.json({message: "FCM token saved"}); 
    }catch(err){
        return res.status(500).json({error: err.message});
    }
   
});

module.exports = router;