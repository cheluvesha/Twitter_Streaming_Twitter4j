package org.twitter

import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

/***
  * Producer Class responsible for creating and sending data to Kafka topic
  */
object KafkaProducer {

  /***
    * Creates Kafka Producer
    * @param broker String
    * @return KafkaProducer[String, String]
    */
  def createProducer(broker: String): KafkaProducer[String, String] = {
    println("Creating a kafka producer")
    try {
      val props = new Properties()
      props.put("bootstrap.servers", broker)
      //Set acknowledgements for producer requests.//Set acknowledgements for producer requests.
      props.put("acks", "all")
      //If the request fails, the producer can automatically retry,
      props.put("retries", new Integer(0))
      //Specify buffer size in config
      props.put("batch.size", new Integer(16384))
      //Reduce the no of requests less than 0
      props.put("linger.ms", new Integer(1))
      //The buffer.memory controls the total amount of memory available to the producer for buffering.
      props.put("buffer.memory", new Integer(33554432))
      props.put(
        "key.serializer",
        "org.apache.kafka.common.serialization.StringSerializer"
      )
      props.put(
        "value.serializer",
        "org.apache.kafka.common.serialization.StringSerializer"
      )
      val producer = new KafkaProducer[String, String](props)
      producer
    } catch {
      case nullPointerException: NullPointerException =>
        println(nullPointerException.printStackTrace())
        throw new Exception("Broker data is null")
      case kafkaException: org.apache.kafka.common.KafkaException =>
        println(kafkaException.printStackTrace())
        throw new Exception("Unable to create kafka producer")
    }
  }

  /***
    * Sends Data to Kafka Topic
    * @param topic String
    * @param dataToBePassed String
    * @param kafkaProducer KafkaProducer[String, String]
    * @return Int
    */
  def sendingDataToKafkaTopic(
      topic: String,
      dataToBePassed: String,
      kafkaProducer: KafkaProducer[String, String]
  ): Boolean = {
    println("Publishing data to the kafka topic")
    try {
      var key = 0
      val record = new ProducerRecord[String, String](
        topic,
        key.toString,
        dataToBePassed
      )
      kafkaProducer.send(record)
      key += 1
      true
    } catch {
      case ex: Exception =>
        println(ex.printStackTrace())
        throw new Exception("Unable to send records to topic from Producers")
    }
  }
}
