package jp.ne.paypay.sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.ne.paypay.api.PaymentApi;
import jp.ne.paypay.model.MerchantOrderItem;
import jp.ne.paypay.model.MoneyAmount;
import jp.ne.paypay.model.QRCode;
import jp.ne.paypay.model.QRCodeDetails;
import jp.ne.paypay.model.ResultInfo;
import jp.ne.paypay.sample.model.QrCodeRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SampleController.class)
class SampleApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private SampleController sampleController;

	@MockBean
	PaymentApi paymentApi;

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
}
