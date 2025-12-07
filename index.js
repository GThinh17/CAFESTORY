const express = require("express");
const cors = require("cors");
const bodyParser = require("body-parser");
const app = express();
const chat = require("./controller/chat");
const createThread = require("./controller/createThread");
const saveKnowledge = require("./controller/saveKnowledge");
const connectDB = require("./config/mongoConfig");

app.use(cors());
app.use(bodyParser.json());
connectDB(); // KẾT NỐI TRƯỚC

(async () => {
  app.get("/start", createThread);
  app.post("/save-knowledge", saveKnowledge);
  app.post("/chat", chat);

  app.listen(8070, "0.0.0.0", () => {
    console.log("Server running on port 8070");
  });
})();
