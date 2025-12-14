const NotifcationService = require("./notification.service");
const UserService = require("./user.service");
const User = require("../models/user.model");

class ChatService {
    async sendMessage(senderId, receiverId, message){
        const receiver = await User.findById(receiverId);

        if(receiver?.fcmTokens?.length){
            await NotifcationService.sendToTokens(
                receiver.fcmTokens,
                {
                    notification: {
                        title: "Tin nhan moi",
                        body: message
                    },
                
                    data: {
                        type:"chat_message",
                        senderId,
                    }
                }   
            )
        }
        return true;
    }

}

module.exports = new ChatService();