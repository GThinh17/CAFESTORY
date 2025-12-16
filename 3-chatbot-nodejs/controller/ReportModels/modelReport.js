const axios = require("axios");
const { OpenAI } = require("openai");
require("dotenv").config();

const openai = new OpenAI({ apiKey: process.env.OPENAI_API_KEY });

const processReports = async (req, res) => {
    try {
        const token = req.token;
        console.log(">>>>>>>>>>>>>>TOKEN<<<<<<<<<<", token);
        const { reports, thread_id } = req.body;

        if (!thread_id) return res.status(400).json({ error: "Missing thread_id" });
        if (!Array.isArray(reports)) return res.status(400).json({ error: "reports must be an array" });

        const assistantId = process.env.OPENAI_REPORT_ASSISTANT_ID;
        console.log("🔥 BODY RECEIVED:", req.body);
        // =======================================
        // 🔥 1) Build enrichedReports FIRST
        // =======================================
        let enrichedReports = [];

        for (const r of reports) {
            let detail = null;
            let url = null;
            if (!r || !r.reportType) {
                console.log("❌ Bad report format:", r);
                continue;
            }
            console.log(">>>>>>>>>>>>Blog id<<<<<<<<<", r.reportedBlogId);
            console.log(">>>>>>>>>>>>User id<<<<<<<<<", r.reportedUserId);
            console.log("TYPE REPORT LÀ", r.reportType);
            // console.log(">>>>>>>>>>>>Blog id<<<<<<<<<", r.reportedPageId);
            switch (r.reportType) {
                case "BLOG":
                    url = `http://localhost:8080/api/blogs/${r.reportedBlogId}`;
                    break;
                case "PAGE":
                    url = `http://localhost:8080/api/pages/${r.reportedPageId}`;
                    break;
                case "USER":
                    url = `http://localhost:8080/users/${r.reportedUserId}`;
                    break;
                default:
                    console.log("Unknown reportType:", r.reportType);
                    break;
            }

            if (url) {
                try {
                    const response = await axios.get(url, {
                        headers: { Authorization: `Bearer ${token}` }
                    });
                    console.log("RESPONSE", response.data.data)
                    detail = response.data.data;
                } catch (err) {
                    console.error("❌ Failed to fetch detail for report:", r.id);
                }
            }

            enrichedReports.push({
                ...r,
                detail
            });
        }

        // =======================================
        // 🔥 2) Now send enrichedReports to OpenAI
        // =======================================
        await openai.beta.threads.messages.create(thread_id, {
            role: "user",
            content: `
                Dưới đây là danh sách REPORT cần đánh giá. 
                Mỗi report đã kèm theo dữ liệu chi tiết (detail). 
                Hãy phân tích kỹ dựa trên content gốc và dữ liệu detail.

                ⚠ Trả về JSON THUẦN, không chứa text bao ngoài.

                ${JSON.stringify(enrichedReports)}
                `
        });

        // =======================================
        // 🔥 3) Run assistant
        // =======================================
        const run = await openai.beta.threads.runs.createAndPoll(thread_id, {
            assistant_id: assistantId,
        });

        const messages = await openai.beta.threads.messages.list(run.thread_id);
        const resultJSON = messages.data[0]?.content?.[0]?.text?.value || "";

        // =======================================
        // 🔥 4) Parse JSON từ Assistant
        // =======================================
        let decisions;
        try {
            decisions = JSON.parse(resultJSON);
        } catch (err) {
            console.error("⚠ AI Response Parse Error:", resultJSON);
            return res.status(500).json({ error: "AI returned invalid JSON", raw: resultJSON });
        }

        // =======================================
        // 🔥 5) Apply AI decisions bằng Patch API
        // =======================================
        let success = [];
        let failed = [];

        for (const d of decisions) {
            try {
                console.log(">>>>>>>>>>>>>>>>>>ID <<<<<<<<<<", d.reportId)
                const response = await axios.patch(
                    `http://localhost:8080/api/report/${d.reportId}`,
                    {
                        feedback: d.Feedback,
                        isFlagged: d.isFlagged,
                        isDeleted: d.isDeleted,
                        isBanned: d.isBanned
                    },
                    {
                        headers: { Authorization: `Bearer ${token}` }
                    }
                );

                success.push({ id: d.reportId, status: response.status });
            } catch (err) {
                failed.push({ id: d.reportId, error: err.message });
            }
        }

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
