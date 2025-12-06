const { OpenAI } = require("openai");
const Knowledge = require("../models/Knowledge");
const openai = new OpenAI({ apiKey: process.env.OPENAI_API_KEY });

const saveKnowledge = async (req, res) => {
  try {
    const { text } = req.body;
    if (!text) return res.status(400).json({ error: "Missing text" });

    const embedding = await openai.embeddings.create({
      model: "text-embedding-3-small",
      input: text,
    });

    const vector = embedding.data[0].embedding;

    const doc = await Knowledge.create({ text, embedding: vector });

    return res.json({ message: "Saved", id: doc._id });
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

module.exports = saveKnowledge;
