const { OpenAI } = require("openai");
const Knowledge = require("../models/Knowledge");
const openai = new OpenAI({ apiKey: process.env.OPENAI_API_KEY });
const createThread = async (req, res) => {
  try {
    const thread = await openai.beta.threads.create();
    return res.json({ thread_id: thread.id });
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

module.exports = createThread;
