const moongoose = require("mongoose");

const UserSchema = new moongoose.Schema({
    name: String,
    email: String,
    fcmTokens: [String] // Danh sach token FCM
})

module.exports = moongoose.model("User", UserSchema);