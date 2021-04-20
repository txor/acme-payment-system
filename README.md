# acme-payment-system
Technical Test Inc. new centralized payment system

## :computer: How to execute
Build everything with  
```mvn clean install```  
Then you can startup the whole system with the provided docker-compose file  
```docker-compose up -d```  
When everything is running up you can trigger the technical test  
```curl localhost:9000/start```  
It performs a test by deleting all payments in the PostgreSQL
database, reset logs and starts to produce payments data in the Kafka stream.  
To stop the system run  
```docker-compose down```

## :memo: Notes

### Design
The _diagrams_ folder includes three PlantUML diagrams which depicts the architecture of the solution.
There are three components with limited responsibility which collaborate to provide a scalable solution:
* _payment-dispatcher_: Reacts to a Kafka message, reads the payment and sends it to an _update payment_ endpoint. The topic name and the endpoint URL are configurable so two groups of instances can be started one for each topic (online, offline).  
* _payment-updater_: Provides an _update payment_ endpoint which updates both the payment data, and the account last payment date into the database.
* _payment-checker_:  When receives a payment, it makes a call to the Payment API and can send the payment to _payment-updater_ for successful payments. It also provides the same _update payment_ endpoint than _payment-updater_, so a _payment-dispatcher_ can be configured to send the payment data to any of them, providing flexibility that allows to each micro-service to independently scale up.

### Data loss prevention    
The following measures are put in place to prevent the loss of a payment:
* The Kafka client of _payment-dispatcher_ only commits a message (mark as consumed) when it receives back the Ok from the update API call, _payment-updater_ and _payment-checker_ behaves like this too, assuring that after returning Ok, the payment has been saved.
* The _payment-updater_ performs the database operations as transactional operations, so if it is unable to complete one, the writes are rolled back, and an error response is sent back.

### Tools
* Watch topics coming from Kafka with  
```tools/watch-topic.sh topic_name```  
* Browse PostgreSQL with a dockerized client with  
```postges-client.sh```  
* Start the tests with  
```start-test.sh```  

## :pushpin: Things to improve
* Report errors to the log API
* Performance tests
* Scalability tests
