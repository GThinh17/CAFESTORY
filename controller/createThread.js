const createThread = async (req, res) => {
  try {
    const thread = await openai.beta.threads.create();
    return res.json({ thread_id: thread.id });
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

module.exports = createThread;
