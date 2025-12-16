const User = require("../models/user.model");

//Luu token FCM cho user
class UserService {
    async saveFcmToken(userId, fcmToken){
        return User.findByIdAndUpdate(
            userId,
            {$addToSet: {fcmTokens: fcmToken}},
            {new: true}
        );
    }
}
module.exports = new UserService();