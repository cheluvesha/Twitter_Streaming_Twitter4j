package org.twitter

/***
  * Driver Class performs call operation to fetch data from twitter using twitter4j API and publishes Data into kafka
  */
object TwitterStream {
  val consumerKey: String = System.getenv("CONSUMER_KEY")
  val consumerSecret: String = System.getenv("CONSUMER_SECRET")
  val accessToken: String = System.getenv("ACCESS_TOKEN")
  val accessTokenSecret: String = System.getenv("ACCESS_TOKEN_SECRET")
  val broker: String = System.getenv("BROKER")
  val topic: String = System.getenv("TOPIC")
  // Entry point to the Application
  def main(args: Array[String]): Unit = {
    val topicToQuery: String = args(0)
    println("Twitter streaming is started")
    val configurationBuilder = GetTweets.setConfiguration(
      consumerKey,
      consumerSecret,
      accessToken,
      accessTokenSecret
    )
    val kafkaProducer = KafkaProducer.createProducer(broker)
    GetTweets.getStreamTweets(
      configurationBuilder,
      topicToQuery,
      kafkaProducer,
      topic
    )
  }
}
