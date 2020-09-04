# Java SDK Implementation

This is a java based implementation of PayPay's SDK. 

It creates QR code link and checks the status of the payment.

### Configurations

update the API Key and Secret with actual values in application.properties file.
Replace your front end url in the cors.urls property
```properties
cors.urls=http://localhost:8080
API_KEY=API_KEY
API_SECRET=API_SECRET
```

## How to Run the API Server

```sh
$ cd server/java
$ ./gradlew bootRun
```

You should now have the API server running on http://localhost:5000

Note: To change the port number update the below property in application.properties

```properties
server.port=5000
```
