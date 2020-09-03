# PHP SDK Implementation

This is a PHP based implementation of PayPay's SDK. For the demonstration purpose we have used a simple Flask server with polling library to create QR code link and to check the order status of a QR code

### Install Requirements

```sh
$ cd server/php
$ composer install
```
### Add API Keys to environment

```sh
$ export API_KEY="REPLACE_WITH_YOUR_API_KEY"
$ export API_SECRET="REPLACE_WITH_YOUR_SECRET_KEY" 
$ export MERCHID="REPLACE_WITH_YOUR_MERCHANT_ID"
```


### Run local PHP server
```sh
$ php -S localhost:5000
```
You should now have the API server running on http://localhost:5000
