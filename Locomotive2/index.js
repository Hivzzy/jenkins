import KafkaConfig from "./config.js";
import bodyParser from "body-parser";
import express from "express";
import controllers from './controller.js';
import mongoose from 'mongoose';

const app = express();
const jsonParser = bodyParser.json();

app.listen(8081, () => {
    console.log("Server is running on port 8081.");
});

// API post data to Kafka
app.post("/api/send", jsonParser, (req, res) => {
    // Log the messages before sending to Kafka
    console.log("Messages to be sent to Kafka:", req.body);
    controllers.sendMessageToKafka(req, res);
});

// Consume message from Kafka
const kafkaConfig = new KafkaConfig();
kafkaConfig.consume('info-lokomotif', (value) => {
    console.log("Received value from Kafka:", value);
});


mongoose.connect('mongodb://127.0.0.1:27017/lokomotif')
    .then(() => console.log('Connected to MongoDB'))
    .catch(err => console.error('Failed to connect to MongoDB:', err));

const db = mongoose.connection;

db.on('error', console.error.bind(console, 'MongoDB connection error:'));
db.once('open', function() {
    console.log('Connected to MongoDB');
});