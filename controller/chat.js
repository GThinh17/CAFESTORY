const openai = new OpenAI({
  apiKey: process.env.OPENAI_API_KEY,
});
const chat = async (req, res) => {
  const assistantId = "asst_yD9LBPnkPG5geoogHrVdA9Z2";
  const threadId = req.body.thread_id;
  const message = req.body.message;
  if (!threadId) {
    return res.status(400).json({ error: "Missing thread_id" });
  }
  console.log(`Received message: ${message} for thread ID: ${threadId}`);
  await openai.beta.threads.messages.create(threadId, {
    role: "user",
    content: message,
  });
  const run = await openai.beta.threads.runs.createAndPoll(threadId, {
    assistant_id: assistantId,
  });
  const messages = await openai.beta.threads.messages.list(run.thread_id);
  const response = messages.data[0].content[0].text.value;
  return res.json({ response });
};
module.exports = chat;
