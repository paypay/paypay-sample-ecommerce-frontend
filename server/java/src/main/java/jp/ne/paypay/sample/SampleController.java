package jp.ne.paypay.sample;

import com.dyngr.Polling;
import com.dyngr.core.AttemptResults;
import jp.ne.paypay.ApiClient;
import jp.ne.paypay.ApiException;
import jp.ne.paypay.Configuration;
import jp.ne.paypay.api.PaymentApi;
import jp.ne.paypay.model.PaymentDetails;
import jp.ne.paypay.model.PaymentState;
import jp.ne.paypay.model.QRCode;
import jp.ne.paypay.model.QRCodeDetails;
import jp.ne.paypay.sample.model.Item;
import jp.ne.paypay.sample.model.QrCodeRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class SampleController {

    @Autowired
    private Environment env;

    @GetMapping("/cakes")
    @ResponseBody
    public ResponseEntity<List<Item>> getCakes() {
        List<Item> items = new ArrayList();
        items.add(new Item(1, 120, "cake_shop.mississippi", "darkforest.png"));
        items.add(new Item(2, 190, "cake_shop.red_velvet", "redvelvet.png"));
        items.add(new Item(3, 100, "cake_shop.dark_forest", "darkforestcake.png"));
        items.add(new Item(4, 200, "cake_shop.rainbow", "rainbow.png"));
        items.add(new Item(5, 80, "cake_shop.lemon", "lemon.png"));
        items.add(new Item(6, 110, "cake_shop.pineapple", "pineapple.png"));
        items.add(new Item(7, 90, "cake_shop.banana", "banana.png"));
        items.add(new Item(8, 165, "cake_shop.carrot", "carrot.png"));
        items.add(new Item(9, 77, "cake_shop.choco", "choco.png"));
        items.add(new Item(10, 130, "cake_shop.chocochip", "chocochip.png"));
        items.add(new Item(11, 140, "cake_shop.orange", "orange.png"));
        items.add(new Item(12, 155, "cake_shop.butterscotch", "butterscotch.png"));
        return ResponseEntity.ok(items);
    }

    @PostMapping("/create-qr")
    @ResponseBody
    public ResponseEntity<?> createQrCode(@RequestBody QrCodeRequest qrCodeRequest) throws ApiException {
        ApiClient apiClient = new Configuration().getDefaultApiClient();
        String result = updateApiClient(apiClient);
        if(!"Success".equals(result)){
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        String redirectUrl = env.getProperty("REDIRECT_PATH");
        String merchantPaymentId = UUID.randomUUID().toString();
        QRCode qrCode = new QRCode();
        qrCode.setMerchantPaymentId(merchantPaymentId);
        qrCode.setCodeType("ORDER_QR");
        qrCode.setOrderItems(qrCodeRequest.getOrderItems());
        qrCode.setAmount(qrCodeRequest.getAmount());
        qrCode.setRequestedAt(Instant.now().getEpochSecond());
        qrCode.setRedirectUrl(redirectUrl+"/"+merchantPaymentId);
        qrCode.setRedirectType(QRCode.RedirectTypeEnum.WEB_LINK);
        PaymentApi paymentApi = new PaymentApi(apiClient);
        QRCodeDetails qrCodeDetails = paymentApi.createQRCode(qrCode);
        return new ResponseEntity<>(qrCodeDetails, HttpStatus.OK);

    }

    @GetMapping("/order-status/{merchantId}")
    @ResponseBody
    public ResponseEntity<?> getPaymentStatus(@PathVariable String merchantId) {
        ApiClient apiClient = new Configuration().getDefaultApiClient();
        String result = updateApiClient(apiClient);
        if(!"Success".equals(result)){
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        PaymentApi paymentApi = new PaymentApi(apiClient);
        PaymentDetails paymentDetails = fetchPaymentDetails(paymentApi, merchantId);
        return ResponseEntity.ok(paymentDetails);
    }
    private String updateApiClient(ApiClient apiClient){
        String apiKey = env.getProperty("API_KEY");
        String apiSecret = env.getProperty("API_SECRET");
        String assumeMerchantKey = env.getProperty("MERCHANT_KEY");
        if(StringUtils.isEmpty(apiKey) || StringUtils.isEmpty(apiSecret)){
            return "No API Key/Secret set";
        }
        apiClient.setProductionMode(false);
        apiClient.setApiKey(apiKey);
        apiClient.setApiSecretKey(apiSecret);
        apiClient.setAssumeMerchant("MUNE_CAKE_SHOP");
        if(StringUtils.isNotEmpty(assumeMerchantKey)){
            apiClient.setAssumeMerchant(assumeMerchantKey);
        }
        return "Success";
    }

    private PaymentDetails fetchPaymentDetails(PaymentApi paymentApi, String merchantId) {
        return Polling
                .waitPeriodly(1, TimeUnit.SECONDS)
                .stopAfterAttempt(3)
                .run(() -> {
                    PaymentDetails paymentDetails = paymentApi.getCodesPaymentDetails(merchantId);
                    if (paymentDetails.getData() != null && (paymentDetails.getData().getStatus() == PaymentState.StatusEnum.COMPLETED
                            || paymentDetails.getData().getStatus() == PaymentState.StatusEnum.FAILED)) {
                        return AttemptResults.finishWith(paymentDetails);
                    } else {
                        return AttemptResults.justContinue();
                    }
                });
    }

}
