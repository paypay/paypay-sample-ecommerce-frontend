<?php

use PayPay\OpenPaymentAPI\Models\CreateQrCodePayload;
use PayPay\OpenPaymentAPI\Models\OrderItem;

$json = file_get_contents('php://input');
$req = json_decode($json, true);

$payload = new CreateQrCodePayload();
$payload->setMerchantPaymentId(uniqid('MUNECAKE_'));
$payload->setCodeType();
$payload->setAmount($req['amount']);
$orderItems = [];
foreach ($req['orderItems'] as $key => $item) {
    $incomingItem = (new OrderItem())->setName($item['name'])->setQuantity($item['quantity'])->setUnitPrice($item['unitPrice']);
    if (isset($item['productId'])) {
        $incomingItem->setProductId("${item['productId']}");
    }
    if (isset($item['category'])) {
        $incomingItem->setCategory($item['category']);
    }
    $orderItems[] = $incomingItem;
}
$mpid = $payload->getMerchantPaymentId();
$payload->setOrderItems($orderItems)->setRequestedAt();
$payload->setRedirectType('WEB_LINK')->setRedirectUrl($_SERVER["HTTP_ORIGIN"] . "/orderpayment/$mpid");
try {
    $resp = $client->code->createQRCode($payload);
    header('Content-Type: application/json');
    echo json_encode($resp);
} catch (Exception $e) {
    http_response_code($e->getCode());
    $response = [
        "resultInfo" => [
            "code" => "string",
            "message" => $e->getMessage(),
            "codeId" => "string"
        ]
    ];
}
