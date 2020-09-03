package jp.ne.paypay.sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.ne.paypay.model.MerchantOrderItem;
import jp.ne.paypay.model.MoneyAmount;
import jp.ne.paypay.model.PaymentDetails;
import jp.ne.paypay.model.QRCodeDetails;
import jp.ne.paypay.model.ResultInfo;
import jp.ne.paypay.sample.model.QrCodeRequest;
import jp.ne.paypay.sample.services.SampleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SampleController.class)
class SampleControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private SampleController sampleController;

	@MockBean
	SampleService sampleService;


	@Test
	@DisplayName("Get all cakes")
	void getCakesTest() throws Exception {
		this.mockMvc
				.perform(
						get("/cakes")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(
						jsonPath("$").isArray())
				.andExpect(
						jsonPath("$[0].title").value("cake_shop.mississippi"));

	}

	@Test
	@DisplayName("Create QR code")
	void createQRCodeTest() throws Exception {
		MoneyAmount amount = new MoneyAmount().amount(10).currency(MoneyAmount.CurrencyEnum.JPY);
		MerchantOrderItem orderItem = new MerchantOrderItem();
		orderItem.setCategory("Dessert");
		orderItem.setName("Red Velvet Cake");
		orderItem.setProductId("100");
		orderItem.setQuantity(1);
		orderItem.setUnitPrice(amount);
		QrCodeRequest qrCodeRequest = new QrCodeRequest();
		qrCodeRequest.setAmount(amount);
		List<MerchantOrderItem> orderItems = new ArrayList();
		orderItems.add(orderItem);
		qrCodeRequest.setOrderItems(orderItems);

		QRCodeDetails qrCodeDetails = new QRCodeDetails();
		qrCodeDetails.setResultInfo(new ResultInfo().message("Success"));
		Mockito.when(sampleService.createQrCode(Mockito.any(), Mockito.any())).thenReturn(qrCodeDetails);
		Mockito.when(sampleService.updateApiClient(Mockito.any())).thenReturn("Success");
		this.mockMvc
				.perform(
						post("/create-qr")
								.content(new ObjectMapper().writeValueAsBytes(qrCodeRequest))
								.contentType(MediaType.APPLICATION_JSON))
								.andExpect(status().isOk());


	}

	@Test
	@DisplayName("Get Payment Details")
	void getPaymentDetailsTest() throws Exception {
		String merchantId = UUID.randomUUID().toString();
		Mockito.when(sampleService.updateApiClient(Mockito.any())).thenReturn("Success");
		PaymentDetails paymentDetails = new PaymentDetails();
		paymentDetails.setResultInfo(new ResultInfo().message("Success"));
		Mockito.when(sampleService.fetchPaymentDetails(Mockito.any(), Mockito.anyString())).thenReturn(paymentDetails);
		this.mockMvc
				.perform(
						get("/order-status/"+merchantId)
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());


	}
}
