const express = require("express");
const router = express.Router();
const { db } = require("../config/admin-firebase");

// Gửi tin nhắn
router.post("/send", async (req, res) => {
    try {
        const { chatId, senderId, content, type = "text", mediaUrl = null } = req.body;

        const newMessage = {
            senderId,
            content,
            type,
            mediaUrl,
            timestamp: new Date(),
            seenBy: [],
        };

        // Lưu message vào sub-collection
        await db
            .collection("messages")
            .doc(chatId)
            .collection("items")
            .add(newMessage);

        // Update chat metadata
        await db.collection("chats").doc(chatId).update({
            lastMessage: content,
            lastMessageSender: senderId,
            updatedAt: new Date(),
        });

        return res.json({ message: "Message sent!", data: newMessage });
    } catch (err) {
        return res.status(500).json({ error: err.message });
    }
});

// Lấy tin nhắn theo chatId
router.get("/:chatId", async (req, res) => {
    try {
        const chatId = req.params.chatId;

        const snapshot = await db
            .collection("messages")
            .doc(chatId)
            .collection("items")
            .orderBy("timestamp", "asc")
            .get();

        const messages = snapshot.docs.map(doc => ({
            messageId: doc.id,
            ...doc.data()
        }));

        return res.json(messages);
    } catch (err) {
        return res.status(500).json({ error: err.message });
    }
});

module.exports = router;
