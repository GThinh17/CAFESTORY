const jwt = require("jsonwebtoken");

function parseJwt(token) {
    // const secret = process.env.JWT_SECRET; // trùng với Spring Boot
    const secret = Buffer.from(process.env.JWT_SECRET, "base64");
    // console.log(secret);
    try {
        const decoded = jwt.verify(token, secret, {
            algorithms: ["HS512"]
        });
        return decoded;
    } catch (err) {
        console.error("Invalid token", err);
        return null;
    }
}
module.exports = parseJwt;
