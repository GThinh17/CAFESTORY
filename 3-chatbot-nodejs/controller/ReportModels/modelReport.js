const axios = require("axios");
const { OpenAI } = require("openai");
require("dotenv").config();

const openai = new OpenAI({ apiKey: process.env.OPENAI_API_KEY });

const processReports = async (req, res) => {
    try {
        const token = req.token;
        const { reports, thread_id } = req.body;

        if (!thread_id) return res.status(400).json({ error: "Missing thread_id" });
        if (!Array.isArray(reports)) return res.status(400).json({ error: "reports must be an array" });

        const assistantId = process.env.OPENAI_REPORT_ASSISTANT_ID;

        // Send reports to AI
        await openai.beta.threads.messages.create(thread_id, {
            role: "user",
            content: `Dưới đây là danh sách Report cần phân tích, hãy trả về JSON thuần không chứa text bao ngoài.\n${JSON.stringify(reports)}`
        });

        const run = await openai.beta.threads.runs.createAndPoll(thread_id, {
            assistant_id: assistantId,
        });

        const messages = await openai.beta.threads.messages.list(run.thread_id);
        const resultJSON = messages.data[0]?.content?.[0]?.text?.value || "";

        // Assistant must return valid JSON
        let decisions;
        try {
            decisions = JSON.parse(resultJSON);
        } catch (err) {
            console.error("⚠ AI Response Parse Error:", resultJSON);
            return res.status(500).json({ error: "AI returned invalid JSON", raw: resultJSON });
        }

        let success = [];
        let failed = [];

        for (const d of decisions) {
            try {
                console.log(d);
                const response = await axios.patch(`http://localhost:8080/api/report/${d.reportId}`, {
                    feedback: d.Feedback,
                    isFlagged: d.isFlagged,
                    isDeleted: d.isDeleted,
                    isBanned: d.isBanned
                }, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });


                success.push({ id: d.reportId, status: response.status });
            } catch (err) {
                failed.push({
                    id: d.reportId,
                    error: err.message
                });
            }
        }
        // console.log(success);
        return res.json({
            status: "DONE",
            processed: decisions.length,
            success,
            failed,
            rawAIResponse: resultJSON
        });

    } catch (error) {
        console.error("❌ processReports Error:", error);
        return res.status(500).json({ error: error.message });
    }
};

module.exports = processReports;
