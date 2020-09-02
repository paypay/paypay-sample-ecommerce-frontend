<?php
require_once('./vendor/autoload.php');
use PayPay\OpenPaymentAPI\Client;

header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Headers: *");
if ($_SERVER["REQUEST_METHOD"]==='OPTIONS') {
    http_response_code(204);
    exit(0);
}
function site_url($echome = false)
{
    if ($echome) {
        echo($_SERVER['HTTP_HOST'] . '/');
    }
    return ('http://' . $_SERVER['HTTP_HOST'] . '/');
}

function url_path($getArr=false)
{
    $k=$_SERVER['REQUEST_URI'];
    $k_arr=explode('/', $k);
    return $getArr?$k_arr:$k;
}

function load_route($client)
{
    $intended_file="./routes".url_path().".php";
    $urlparam='';
    if (file_exists($intended_file)) {
        require_once($intended_file);
    } else {
        $urlparts = url_path(true);
        $len = sizeof($urlparts);
        $intended_file="./routes/".$urlparts[$len-2].".php";
        if (file_exists($intended_file)) {
            $urlparam = $urlparts[$len-1];
            require_once($intended_file);
        }
    }
}


$client = new Client([
    'API_KEY' => getenv('API_KEY'),
    'API_SECRET'=> getenv('API_SECRET'),
    'MERCHANT_ID'=>getenv('MERCHID')
],
false//set to true for production mode
);
load_route($client);
