const express = require("express");
const cors = require("cors");
const bodyParser = require("body-parser");
const app = express();
const chat = require("./controller/chat");
const createThread = require("./controller/createThread");
const saveKnowledge = require("./controller/saveKnowledge");

app.use(cors());
app.use(bodyParser.json());

(async () => {
  app.get("/start", createThread);
  app.post("/save-knowledge", saveKnowledge);
  app.post("/chat", chat);

  app.listen(5000, "0.0.0.0", () => {
    console.log("Server running on port 5000");
  });
})();
