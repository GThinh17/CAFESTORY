const { OpenAI } = require("openai");
const Knowledge = require("../models/Knowledge");
const cosineSimilarity = require("../utils/cosine");
const openai = new OpenAI({ apiKey: process.env.OPENAI_API_KEY });

const chat = async (req, res) => {
  const assistantId = process.env.OPENAI_ASSISTANT_ID;
  const threadId = req.body.thread_id;
  const message = req.body.message;

  if (!threadId) return res.status(400).json({ error: "Missing thread_id" });

  // Create embedding for question
  const embedQuery = await openai.embeddings.create({
    model: "text-embedding-3-small",
    input: message,
  });
  const queryVector = embedQuery.data[0].embedding;

  // Load all knowledge (demo — real should limit or filter)
  const allData = await Knowledge.find({});
  let bestMatch = null;
  let bestScore = 0;

  for (const item of allData) {
    const score = cosineSimilarity(queryVector, item.embedding);
    if (score > bestScore) {
      bestScore = score;
      bestMatch = item;
    }
  }

  let finalMessage = message;

  if (bestMatch && bestScore > 0.75) {
    finalMessage += `\n\nRelevant Information:\n${bestMatch.text}`;
  }

  await openai.beta.threads.messages.create(threadId, {
    role: "user",
    content: finalMessage,
  });

  const run = await openai.beta.threads.runs.createAndPoll(threadId, {
    assistant_id: assistantId,
  });

  const messages = await openai.beta.threads.messages.list(run.thread_id);
  const response = messages.data[0].content[0].text.value;

  return res.json({ response, matchedKnowledge: bestMatch?.text });
};

module.exports = chat;
