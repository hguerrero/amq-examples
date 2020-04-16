export default (window.config =
    {
        mq_username: process.env.MQ_USERNAME || "m_user",
        mq_password: process.env.MQ_PASSWORD || "m_password",
        mq_server: process.env.AMQ_URL || "wss://localhost:443",
        mq_destination: process.env.DESTINATION || "m_address"
    }
);