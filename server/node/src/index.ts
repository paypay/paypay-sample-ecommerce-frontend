import express from "express";
import bodyParser from 'body-parser';
import { v4 as uuidv4 } from 'uuid';
import PAYPAY from '@paypayopa/paypayopa-sdk-node';
import * as cakes from './cakes.json';

const app = express();
const port = 8080;

const API_KEY = "api_key";
const API_SECRET = "api_secret";
const MERCHANT_ID = "merchant_id";

PAYPAY.Configure({
    clientId: API_KEY,
    clientSecret: API_SECRET,
    merchantId: MERCHANT_ID,
    productionMode: false
});

app.use(bodyParser.json())

app.get("/", (req, res) => {
    res.json({
        status: "running"
    });
});

app.get("/cakes", (req, res) => {
    res.json(cakes);
})

app.post("/create-qr", (req, res) => {

    const payload = {
        merchantPaymentId: uuidv4(),
        amount: req.body.amount,
        codeType: 'ORDER_QR',
        orderItems: req.body.orderItems,
        redirectUrl: "redirect_url",
        redirectType: 'WEB_LINK',
    };

    PAYPAY.QRCodeCreate(payload, (ppResonse:any) => {
        res.json(ppResonse.BODY);
    });
})

app.get("/order-status/:merchantId", (req, res) => {
    res.send("Hello world?");
});


app.listen(port, () => {
    console.log(`server started at http://localhost:${port}`);
});