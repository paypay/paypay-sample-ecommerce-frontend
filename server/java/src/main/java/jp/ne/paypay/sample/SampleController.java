package jp.ne.paypay.sample;

import jp.ne.paypay.ApiClient;
import jp.ne.paypay.ApiException;
import jp.ne.paypay.Configuration;
import jp.ne.paypay.api.PaymentApi;
import jp.ne.paypay.model.PaymentDetails;
import jp.ne.paypay.model.QRCodeDetails;
import jp.ne.paypay.sample.model.Item;
import jp.ne.paypay.sample.model.QrCodeRequest;
import jp.ne.paypay.sample.services.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SampleController {

    @Autowired
    private SampleService sampleService;

    private final ApiClient apiClient = new Configuration().getDefaultApiClient();

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
        String result = sampleService.updateApiClient(apiClient);
        if(!"Success".equals(result)){
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        QRCodeDetails qrCodeDetails = sampleService.createQrCode(new PaymentApi(apiClient), qrCodeRequest);
        return new ResponseEntity<>(qrCodeDetails, HttpStatus.OK);

    }

    @GetMapping("/order-status/{merchantId}")
    @ResponseBody
    public ResponseEntity<?> getPaymentStatus(@PathVariable String merchantId) {
        String result = sampleService.updateApiClient(apiClient);
        if(!"Success".equals(result)){
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        PaymentDetails paymentDetails = sampleService.fetchPaymentDetails(new PaymentApi(apiClient), merchantId);
        return ResponseEntity.ok(paymentDetails);
    }


}
