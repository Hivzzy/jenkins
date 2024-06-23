import { Kafka } from "kafkajs";
import mongoose from 'mongoose';

class KafkaConfig {
    constructor() {
        this.kafka = new Kafka({
            clientId: "nodejs-kafka",
            brokers: ['localhost:9092']
        });
        this.producer = this.kafka.producer();
        this.consumer = this.kafka.consumer({ groupId: "myGroup" });
    }

    async produce(topic, message) {
        try {
            await this.producer.connect();

            const messages = [
                {
                    value: JSON.stringify(message),
                },
            ];

            await this.producer.send({
                topic: topic,
                messages: messages
            });
        } catch (err) {
            console.log(err);
        } finally {
            await this.producer.disconnect();
        }
    }

    async consume(topic, callback) {
        try {
            await this.consumer.connect();
            await this.consumer.subscribe({ topic: topic, fromBeginning: true });
            await this.consumer.run({
                eachMessage: async ({ topic, partition, message }) => {
                    const value = JSON.parse(message.value.toString());
                    
                    if (!mongoose.models['info-lokomotif']) {
                        const InfoLokomotifSchema = new mongoose.Schema({
                            kodeLoko: String,
                            namaLoko: String,
                            dimensiLoko: String,
                            status: String,
                            waktu: String
                        });

                        mongoose.model('info-lokomotif', InfoLokomotifSchema);
                    }

                    const InfoLokomotif = mongoose.model('info-lokomotif');
                    const dataToSave = new InfoLokomotif({
                        kodeLoko: value.kodeLoko,
                        namaLoko: value.namaLoko,
                        dimensiLoko: value.dimensiLoko,
                        status: value.status,
                        waktu: value.waktu
                    });
                    
                    dataToSave.save()
                        .then(() => {
                            console.log('Data berhasil disimpan ke MongoDB');
                        })
                        .catch((err) => {
                            console.error('Gagal menyimpan data ke MongoDB:', err);
                        });
                    
                    callback(value);
                }
            });
        } catch (err) {
            console.log(err);
        }
    }
}

export default KafkaConfig;
