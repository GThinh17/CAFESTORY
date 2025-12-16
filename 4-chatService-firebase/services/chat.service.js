// const NotifcationService = require("./notification.service");
// const User = require("../models/user.model");
// const { link } = require("../controller/chats");

// class ChatService {
//   async sendMessage(senderId, receivers, message, chatId) {
//     const users = await User.find({ _id: { $in: receivers } });

//     const tokens = users.flatMap(u => u.fcmTokens || []);

//     if (tokens.length) {
//       await NotifcationService.sendToTokens(tokens, {
//         notification: {
//           title: "Tin nhắn mới",
//           body: message
//         },
//         data: {
//           type: "chat_message",
//           senderId,
//           chatId
//         },
//         webpush: {
//           fcmOptions: { link: "/chat/" + chatId }
//         }
//       });
//     }
//     return true;
//   }
// }

// module.exports = new ChatService();
