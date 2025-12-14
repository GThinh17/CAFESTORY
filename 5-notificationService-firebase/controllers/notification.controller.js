 const NotifcationService = require("../services/notification.service");
const express = require("express");
const router = express.Router();


router.post("/send", async (req, res) => {
    try{
        const {token, title, body, data = {}, link = null} = req.body;
        await NotifcationService.sendToToken(token, title, body, data, link);
        res.json({message: "Notification sent"});
    }catch(err){
        return res.status(500).json({error: err.message});
    }

});


module.exports = router;f