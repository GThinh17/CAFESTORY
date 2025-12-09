const { OpenAI } = require("openai");
require("dotenv").config();

const openai = new OpenAI({ apiKey: process.env.OPENAI_API_KEY });

const chat = async (req, res) => {
    try {
        const assistantId = process.env.OPENAI_CHATBOT_ASSISTANT_ID;
        const threadId = req.body.thread_id;
        const message = req.body.message;

        if (!threadId) {
            return res.status(400).json({ error: "Missing thread_id" });
        }

        // Send message to assistant
        await openai.beta.threads.messages.create(threadId, {
            role: "user",
            content: message,
        });

        // Run
        const run = await openai.beta.threads.runs.createAndPoll(threadId, {
            assistant_id: assistantId,
            // Nếu bạn có vector store attach vào assistant thì bật retrieval
            // retrieval: { vector_store_ids: [process.env.OPENAI_VECTOR_ID] }
        });

        const messages = await openai.beta.threads.messages.list(run.thread_id);
        const responseText = messages.data[0]?.content?.[0]?.text?.value;

        return res.json({
            response: responseText,
        });

    } catch (error) {
        console.error("❌ Chat Error:", error.message);
        return res.status(500).json({ error: error.message });
    }
};

module.exports = chat;
