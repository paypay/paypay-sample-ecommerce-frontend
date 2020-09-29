import express from "express";
import PAYPAY from '@paypayopa/paypayopa-sdk-node';
import * as cakes from './cakes.json';
import { v4 as uuidv4 } from 'uuid';

const router = express.Router();

const FRONTEND_PATH = "http://localhost:8080/orderpayment"

router.get("/", (req, res) => {
    res.json({
        status: "running"
    });
});

router.get("/cakes", (req, res) => {

    const cakesList: any[] = [];

    // tslint:disable-next-line: forin
    for (const key in cakes) {
        cakesList.push(cakes[key]);
    }

    res.json(cakesList);
})

router.post("/create-qr", (req, res) => {

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
        res.send(ppResonse.BODY);
    });
})

const sleep = (ms: any) => {
    return new Promise((resolve) => {
        setTimeout(resolve, ms);
    });
};

const getOrderStatus = (merchantId: any) => {
    return new Promise((resolve, reject) => {
        PAYPAY.GetPaymentDetails([merchantId], (response: any) => {
            resolve(response);
        });
    });
}

router.get("/order-status/:merchantId", async (req, res) => {

    let success: boolean = false;
    let orderStatusResponse: any;

    for (let i = 0; i < 3; i++) {
        orderStatusResponse = await getOrderStatus(req.params.merchantId);

        if (orderStatusResponse != null && orderStatusResponse.BODY != null) {
            const jsonResponse = JSON.parse(orderStatusResponse.BODY);
            if (jsonResponse.data.status === 'COMPLETED') {
                success = true;
                break;
            } else if (jsonResponse.data.status === 'FAILED') {
                success = true;
                break;
            } else {
                await sleep(2000);
                continue;
            }
        } else {
            await sleep(2000);
            continue;
        }
    }

    if (success) {
        res.send(orderStatusResponse.BODY);
    } else {
        res.status(400);
        res.send();
    }
    res.end();
});

export const apiRouter = router;