const { OpenAI } = require("openai");
const Knowledge = require("../../models/Knowledge");
const cosineSimilarity = require("../../utils/cosine");
require("dotenv").config();

const openai = new OpenAI({ apiKey: process.env.OPENAI_API_KEY });

const chat = async (req, res) => {
  try {
    const assistantId = process.env.OPENAI_CHATBOT_ASSISTANT_ID;
    const threadId = req.body.thread_id;
    const message = req.body.message;

    if (!threadId) return res.status(400).json({ error: "Missing thread_id" });

    // Create embedding for question
    const embedQuery = await openai.embeddings.create({
      model: "text-embedding-3-small",
      input: message,
    });
    const queryVector = embedQuery.data[0].embedding;

    // Load knowledge
    const allData = await Knowledge.find({});
    if (allData.length === 0) {
      console.warn("⚠ No knowledge found in MongoDB.");
    }

    // Compute cosine on each item
    const scored = allData
      .map((item) => ({
        text: item.text,
        score: cosineSimilarity(queryVector, item.embedding),
      }))
      .sort((a, b) => b.score - a.score)
      .slice(0, 3); // Top-3 matches

    console.log("🔍 Top matches:", scored);

    let finalMessage = message;

    // Append Relevant Information block
    const filtered = scored.filter((i) => i.score > 0.70);

    if (filtered.length > 0) {
      finalMessage += `\n\nRelevant Information:\n`;
      filtered.forEach(
        (info, idx) =>
          (finalMessage += `- (${idx + 1}) ${info.text}\n`)
      );
    }

    console.log("📩 Message sent to AI:\n", finalMessage);

    // Send to AI
    await openai.beta.threads.messages.create(threadId, {
      role: "user",
      content: finalMessage,
    });

    const run = await openai.beta.threads.runs.createAndPoll(threadId, {
      assistant_id: assistantId,
    });

    const messages = await openai.beta.threads.messages.list(run.thread_id);
    const response = messages.data[0]?.content?.[0]?.text?.value || "";

    return res.json({
      response,
      knowledgeUsed: filtered.map((f) => ({
        text: f.text,
        score: f.score,
      })),
    });
  } catch (error) {
    console.error("❌ Chat Error:", error.message);
    return res.status(500).json({ error: error.message });
  }
};

module.exports = chat;
