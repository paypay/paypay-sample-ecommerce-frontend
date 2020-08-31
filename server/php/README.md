# Python SDK Implementation

This is a python based implementation of PayPay's SDK. For the demonstration purpose we have used a simple Flask server with polling library to create QR code link and to check the order status of a QR code

### Install Requirements

```sh
$ cd server/php
$ composer install
```

### Add API Keys to environment

Open index.php and modify the block with your own values

```php
$client = new Client([
    'API_KEY' => '<ADD_DEVELOPER_KEY>',
    'API_SECRET'=>'<ADD_DEVELOPER_SECRET>',
    'MERCHANT_ID'=>'<ADD_MERCHANT_ID>'
],
false//set to true for production mode
);
```

### Run local PHP server
```sh
$ php -S localhost:5000
```
You should now have the API server running on http://localhost:5000
