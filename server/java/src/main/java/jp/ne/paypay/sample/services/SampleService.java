package jp.ne.paypay.sample.services;

import com.dyngr.Polling;
import com.dyngr.core.AttemptResults;
import jp.ne.paypay.ApiClient;
import jp.ne.paypay.ApiException;
import jp.ne.paypay.api.PaymentApi;
import jp.ne.paypay.model.PaymentDetails;
import jp.ne.paypay.model.PaymentState;
import jp.ne.paypay.model.QRCode;
import jp.ne.paypay.model.QRCodeDetails;
import jp.ne.paypay.sample.model.QrCodeRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SampleService {

    @Autowired
    private Environment env;

    public String updateApiClient(ApiClient apiClient){
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

    public QRCodeDetails createQrCode(PaymentApi paymentApi, QrCodeRequest qrCodeRequest) throws ApiException {
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
        return paymentApi.createQRCode(qrCode);
    }

    public PaymentDetails fetchPaymentDetails(PaymentApi paymentApi, String merchantId) {
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
