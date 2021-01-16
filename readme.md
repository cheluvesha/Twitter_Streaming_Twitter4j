## Project Title: Twitter Streaming And Publishing The Data Into Kafka

## Project Description

### Using Twitter API credentials to establish the connection with twitter through twitter4j to collect tweets data and
### publishing that data into kafka topic using kafka client
 
## Required Dependencies 

### Twitter4j - 4.0.7
    library which helps us to interact with twitter to query tweets.
    
### Kafka-clients - 2.4.1
    Kafka-clients is used to programtically create kafka producer and 
    helps us to publish data into topic.
    
## Plugin used

### Assembly - 0.15.0
    helps us to create a fat jar
        
### How To
    clone the repositary,
    create a twitter developer account and get the twitter API keys,
    use those keys as an environment variables with the name like which is declared as in program,
    and kafka has to be installed in your local system, set broker and topic name also as an environment variables,
    first run startkafka.sh file which will start kafka then run topic.sh which will create kafka topic.
    Use sbt to create jar by running: sbt assembly,
    now use this command to run the jar file: java -jar TwitterKafka-assembly-0.1.jar "Big Data"  
    which takes command line argument to filter required tweet data and run the application thats it.
    It will start streaming tweet data in json format.
    
    
      