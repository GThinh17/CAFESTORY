require("dotenv").config();
const express = require("express");
const cors = require("cors");
const bodyParser = require("body-parser");
const app = express();


app.use(cors());
app.use(bodyParser.json());
app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use((req, res, next) => {
    const authHeader = req.headers['authorization']; // Lấy header Authorization

    if (authHeader && authHeader.startsWith('Bearer ')) {
        const token = authHeader.split(' ')[1]; // Tách lấy token
        req.token = token;
    } else {
        req.token = null;
    }

    next();
});


app.use("/chat", require("./controller/chats"));
app.use("/message", require("./controller/messages"));
app.use("/notification", require("./controller/notifications"));


app.get("/", (req, res) => {

    res.send("Hello from Node server!");
});

app.listen(8081, () => {
    console.log("Node server is running on port 8081");
});