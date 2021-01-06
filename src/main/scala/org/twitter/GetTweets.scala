package org.twitter

import org.apache.kafka.clients.producer.KafkaProducer
import twitter4j.conf.ConfigurationBuilder
import twitter4j._

/** *
  * Connects to twitter using provided configuration keys and Gets tweets from twitter using specified topic
  */
object GetTweets {

  /** *
    * Establish the configuration to have connection with twitter
    * @param consumerKey       String
    * @param consumerSecret    String
    * @param accessToken       String
    * @param accessTokenSecret String
    * @return ConfigurationBuilder
    */
  def setConfiguration(
      consumerKey: String,
      consumerSecret: String,
      accessToken: String,
      accessTokenSecret: String
  ): ConfigurationBuilder = {
    try {
      val configurationBuilder = new ConfigurationBuilder()
      configurationBuilder
        .setDebugEnabled(true)
        .setJSONStoreEnabled(true)
        .setOAuthConsumerKey(consumerKey)
        .setOAuthConsumerSecret(consumerSecret)
        .setOAuthAccessToken(accessToken)
        .setOAuthAccessTokenSecret(accessTokenSecret)
      configurationBuilder
    } catch {
      case nullPointerException: NullPointerException =>
        println(nullPointerException.printStackTrace())
        throw new Exception("Null fields")
      case twitterException: TwitterException =>
        println(twitterException.printStackTrace())
        throw new Exception("Twitter connection error")
    }
  }
  def getStreamTweets(
      configurationBuilder: ConfigurationBuilder,
      topicToQuery: String,
      kafkaProducer: KafkaProducer[String, String],
      topic: String
  ): Unit = {
    println("Streaming Twitter Data From Twitter")
    try {
      val twitterStream =
        new TwitterStreamFactory(configurationBuilder.build()).getInstance
      val statusListener: StatusListener = new StatusListener() {

        def onStatus(status: Status) {
          val statusJson = TwitterObjectFactory.getRawJSON(status)
          println(statusJson)
          KafkaProducer.sendingDataToKafkaTopic(
            topic,
            statusJson,
            kafkaProducer
          )
        }

        def onException(ex: Exception) {
          println(ex.printStackTrace())
          ex.printStackTrace()
        }

        def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) {}

        def onTrackLimitationNotice(numberOfLimitedStatuses: Int) {}

        def onScrubGeo(userId: Long, upToStatusId: Long) {}

        def onStallWarning(warning: StallWarning) {}

      }
      twitterStream.addListener(statusListener)
      twitterStream.filter(topicToQuery)
    } catch {
      case nullPointerException: NullPointerException =>
        println(nullPointerException.printStackTrace())
        throw new Exception("instance is initialized with null")
      case twitterException: TwitterException =>
        println(twitterException.printStackTrace())
        throw new Exception("Failed to search tweets")
    }
  }
}
