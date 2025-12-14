const express = require("express");
const app = express();

app.use(express.json());

app.use("/api/users", require("./controllers/user.controller"));
app.use("/api/notifications", require("./controllers/notification.controller"));

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
});

module.exports = app;