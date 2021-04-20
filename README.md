# acme-payment-system
Technical Test Inc. new centralized payment system

## :computer: How to execute
Build everything with  
```mvn clean install```  
Then you can startup the whole system with the provided docker-compose file  
```docker-compose up -d```  
When everything is running up you can trigger the technical test  
```curl localhost:9000/start```  
It starts technical test by deleting all payments in the PostgreSQL
database, reset logs and starts to produce payments data in the Kafka stream.  
To stop the system run  
```docker-compose down```

## :memo: Notes

### Design
__TODO__  
There are some high level design UML diagrams on the _diagrams_ folder, open them with (PlantUML)[https://plantuml.com].

### Tools
Watch topics coming from Kafka with ```tools/watch-topic.sh topic_name```.

## :pushpin: Things to improve
Integrate everything!
