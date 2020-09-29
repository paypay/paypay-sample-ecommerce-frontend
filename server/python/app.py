from flask import Flask, request, jsonify
from flask_cors import CORS

import paypayopa
import polling
import uuid
import os
import json

_DEBUG = os.environ.get("_DEBUG", default=True)
API_KEY = os.environ.get("API_KEY")
API_SECRET = os.environ.get("API_SECRET")
FRONTEND_PATH = "http://localhost:8080/orderpayment"

if not API_KEY:
    raise ValueError("No API_KEY set for Flask application")
if not API_SECRET:
    raise ValueError("No API_SECRET set for Flask application")

client = paypayopa.Client(
    auth=(API_KEY, API_SECRET),
    production_mode=False)  # Set True for Production Environment. By Default this is set False for Sandbox Environment

client.set_assume_merchant("MUNE_CAKE_SHOP")

app = Flask(__name__)
cors = CORS(app)
app.config['CORS_HEADERS'] = 'Content-Type'

CAKES = [
    {
        "title": "cake_shop.mississippi",
        "id": 1,
        "price": 120,
        "image": "darkforest.png"
    }, {
        "title": "cake_shop.red_velvet",
        "id": 2,
        "price": 190,
        "image": "redvelvet.png"
    }, {
        "title": "cake_shop.dark_forest",
        "id": 3,
        "price": 100,
        "image": "darkforestcake.png"
    }, {
        "title": "cake_shop.rainbow",
        "id": 4,
        "price": 200,
        "image": 'rainbow.png'
    }, {
        "title": "cake_shop.lemon",
        "id": 5,
        "price": 80,
        "image": 'lemon.png'
    }, {
        "title": "cake_shop.pineapple",
        "id": 6,
        "price": 110,
        "image": 'pineapple.png'
    }, {
        "title": "cake_shop.banana",
        "id": 7,
        "price": 90,
        "image": 'banana.png'
    }, {
        "title": "cake_shop.carrot",
        "id": 8,
        "price": 165,
        "image": 'carrot.png'
    }, {
        "title": "cake_shop.choco",
        "id": 9,
        "price": 77,
        "image": 'choco.png'
    }, {
        "title": "cake_shop.chocochip",
        "id": 10,
        "price": 130,
        "image": 'chocochip.png'
    }, {
        "title": "cake_shop.orange",
        "id": 11,
        "price": 140,
        "image": 'orange.png'
    }, {
        "title": "cake_shop.butterscotch",
        "id": 12,
        "price": 155,
        "image": 'butterscotch.png'
    },
]


@app.route('/', methods=['GET', 'OPTIONS'])
def index():
    return jsonify(apiStatus="running")


# sanity check route
@app.route('/cakes', methods=['GET', 'OPTIONS'])
def get_cakes():
    return jsonify(CAKES)


@app.route('/create-qr', methods=['POST'])
def creat_qr():
    req = request.json
    print(req)
    client = paypayopa.Client(auth=(API_KEY, API_SECRET), production_mode=False)
    client.set_assume_merchant("MUNE-CAKE-SHOP")
    merchant_payment_id = uuid.uuid4().hex
    payment_details = {
        "merchantPaymentId": merchant_payment_id,
        "codeType": "ORDER_QR",
        "orderItems": req["orderItems"],
        "amount": req["amount"],
        "redirectUrl": "{}/{}".format(FRONTEND_PATH, merchant_payment_id),
        "redirectType": "WEB_LINK",
    }
    resp = client.Code.create_qr_code(data=payment_details)
    return json.dumps(resp)


def is_correct_response(resp):
    print(resp)
    return resp


def fetch_payment_details(merchant_id):
    resp = client.Payment.get_payment_details(merchant_id)
    if (resp['data'] == 'None'):
        return {
            'error': 'true'
        }
    return resp['data']['status']


@app.route('/order-status/<merch_id>', methods=['GET', 'OPTIONS'])
def order_status(merch_id):
    print(merch_id)
    polling.poll(
        lambda: fetch_payment_details(merch_id) == 'COMPLETED' or fetch_payment_details(merch_id) == 'FAILED',
        check_success=is_correct_response,
        step=2,
        timeout=240)
    return client.Payment.get_payment_details(merch_id)


if __name__ == '__main__':
    app.run(debug=_DEBUG)
