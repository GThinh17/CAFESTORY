const express = require("express");
const router = express.Router();
const { db } = require("../config/admin-firebase");
const { Timestamp } = require("firebase-admin/firestore");
const parseJwt = require("./../controller/userSession");
const notificationService = require("../services/notification.service");


// Tạo chat

router.post("/createchat", async (req, res) => {
    try {
        const token = req.token;
        if (!token) {
            return res.status(500).json({ "error": "Missing token You're not logged in" });
        }
        const payload = parseJwt(token);
        const id = payload.id;
        const { members, isGroup = false, groupName = "" } = req.body;
        members.push(id)
        const newChat = {
            members,
            lastMessage: "",
            lastMessageSender: "",
            updatedAt: Timestamp.now(),
            createdAt: Timestamp.now(),
            isGroup,
            groupName,
        };

       const chatRef = await db.collection("chats").add(newChat);


        //Notification all members created chat
        const snapshot = await db.collection("users").where("userId", "in", members).get(); //Lay user
        const users = snapshot.docs.map(doc => doc.data()); //Chuyen ve mang user
        const notifyMembers = users.filter(u => u.userId !== id);//Loai bo nguoi tao chat
        const tokens  = notifyMembers.flatMap(u => u.fcmTokens || []); //Lay tat ca token FCM


        if(tokens.length){
            await notificationService.sendToTokens(tokens, {
            notification: {
                title: "Bạn đã bắt đầu một cuộc trò chuyện mới",
                body: isGroup
                ? `Bạn đã được thêm vào nhóm: ${groupName}`
                : "Bạn đã được thêm vào cuộc trò chuyện cá nhân"
            },
            data: {
                type: "chat_created",
                chatId: chatRef.id
            },
            webpush: {
                fcmOptions: { link: "/chat/" + chatRef.id }
            }
            });

        }
        
    


        return res.json({ chatId: chatRef.id, ...newChat });

    } catch (error) {
        console.error(error);
        return res.status(500).json({ error: error.message });
    }
});

// Danh sách chat của user
router.get("/list", async (req, res) => {
    try {
        const token = req.token;
        if (!token) {
            return res.status(500).json({ "error": "Missing token You're not logged in" });
        }
        const userId = parseJwt(token).id;
        const snapshot = await db
            .collection("chats")
            .where("members", "array-contains", userId)
            .orderBy("updatedAt", "desc")
            .get();

        const chats = snapshot.docs.map(doc => ({
            chatId: doc.id,
            ...doc.data(),
        }));

        res.json(chats);

    } catch (error) {
        console.error(error);
        res.status(500).json({ error: error.message });
    }
});

module.exports = router;
