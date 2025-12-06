const express = require("express");
const cors = require("cors");
const bodyParser = require("body-parser");
const app = express();
const chat = require("./controller/chat");
const createThread = require("./controller/createThread");

app.use(cors());
app.use(bodyParser.json());

(async () => {
  app.get("/start", createThread);

  app.post("/chat", chat);

  app.listen(8080, () => {
    console.log("Server running on port 8080");
  });
})();
