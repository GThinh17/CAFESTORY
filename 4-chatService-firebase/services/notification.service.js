const admin = require("../../4-chatService-firebase/config/admin-firebase");

class NotifcationService {
    async sendToToken(token, title, body, data , link ) {
        const message = {
            token, // token cua 1 thiet bi
            notification: {
                title, //tieu de cau thong bao
                body // noi dung cau thong bao
            },
            data,//Data custorm,.
            webpush:  link &&{ //cau hinh rieng cho webpush
                fcmOptions: {
                    link
                }
            }
        }
        return admin.messaging().send(message);
    }

    async sendToTokens(tokens, payload = {}) {
        return admin.messaging().sendMulticast({
            tokens,
            ...payload
        });
    }


    async sendToTopic(topic, title, body){ //Gui theo nhom 
        return admin.messaging().send({
            topic, 
            notification:{
                title,
                body
            }
        });
    }
}

module.exports = new NotifcationService();