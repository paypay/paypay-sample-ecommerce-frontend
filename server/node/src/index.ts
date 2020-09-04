import express from "express";
import bodyParser from 'body-parser';
import cors from 'cors';
import { v4 as uuidv4 } from 'uuid';
import PAYPAY from '@paypayopa/paypayopa-sdk-node';
import * as cakes from './cakes.json';

const port = 5000;

const API_KEY = "api_key";
const API_SECRET = "api_secret";
const MERCHANT_ID = "merchant_id";

const FRONTEND_PATH = "http://localhost:8080/orderpayment"

PAYPAY.Configure({
    clientId: API_KEY,
    clientSecret: API_SECRET,
    merchantId: MERCHANT_ID,
    productionMode: false
});

const app = express();

app.use(bodyParser.json())
app.use(cors())

app.get("/", (req, res) => {
    res.json({
        status: "running"
    });
});

app.get("/cakes", (req, res) => {

    const cakesList: any[] = [];

    // tslint:disable-next-line: forin
    for (const key in cakes) {
        cakesList.push(cakes[key]);
    }

    res.json(cakesList);
})

app.post("/create-qr", (req, res) => {

    const paymentId = uuidv4();

    const payload = {
        merchantPaymentId: paymentId,
        amount: req.body.amount,
        codeType: 'ORDER_QR',
        orderItems: req.body.orderItems,
        redirectUrl: FRONTEND_PATH + "/" + paymentId,
        redirectType: 'WEB_LINK',
    };

    PAYPAY.QRCodeCreate(payload, (ppResonse: any) => {
        res.json(ppResonse.BODY);
    });
})

app.get("/order-status/:merchantId", (req, res) => {
    res.send("Hello world?");
});


app.listen(port, () => {
    console.log(`server started at http://localhost:${port}`);
});