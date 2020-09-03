<?php

$resp =  $client->payment->getPaymentDetails($urlparam);
header('Content-Type: application/json');
echo json_encode($resp);