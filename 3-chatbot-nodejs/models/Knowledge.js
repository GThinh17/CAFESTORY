const mongoose = require("mongoose");

const KnowledgeSchema = new mongoose.Schema({
  text: { type: String, required: true },
  embedding: { type: [Number], required: true },
  created_at: { type: Date, default: Date.now },
});

module.exports = mongoose.model("Knowledge", KnowledgeSchema);
