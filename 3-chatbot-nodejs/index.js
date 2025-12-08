const express = require("express");
const cors = require("cors");
const bodyParser = require("body-parser");
const app = express();
const chat = require("./controller/ChatByUsingMongoDB/chat");
const createThread = require("./controller/createThread");
const saveKnowledge = require("./controller/ChatByUsingMongoDB/saveKnowledge");
const connectDB = require("./config/mongoConfig");
const ChatbotAssistant = require("./controller/ChatbotAssistant/ChatBotAssistant")
const ModelCheckReport = require("./controller/ReportModels/modelReport");

app.use(cors());
app.use(bodyParser.json());
connectDB(); // KẾT NỐI TRƯỚC

(async () => {
  app.get("/start", createThread);
  app.post("/save-knowledge", saveKnowledge);
  app.post("/modelcheckreport", ModelCheckReport)
  app.post("/chat-mongdb", chat);
  app.post("/chatbot", ChatbotAssistant)

  app.listen(8082, "0.0.0.0", () => {
    console.log("Server running on port 8082");
  });
})();
