# Python SDK Implementation

This is a python based implementation of PayPay's SDK. For the demonstration purpose we have used a simple Flask server with polling library to create QR code link and to check the order status of a QR code

### Install Requirements

```sh
$ cd server/python
$ pip install -r requirements.txt
```

### Add API Keys to environment

```sh
$ export API_KEY="REPLACE_WITH_YOUR_API_KEY"
$ export API_SECRET="REPLACE_WITH_YOUR_SECRET_KEY" 
```

### Run Flask API server
```sh
$ flask run
```
You should now have the API server running on http://localhost:5000

