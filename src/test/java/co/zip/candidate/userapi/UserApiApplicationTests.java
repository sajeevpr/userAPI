package co.zip.candidate.userapi;

import co.zip.candidate.userapi.constants.AccountType;
import co.zip.candidate.userapi.constants.CurrencyType;
import co.zip.candidate.userapi.model.User;
import co.zip.candidate.userapi.model.ui.AccountRequest;
import co.zip.candidate.userapi.model.ui.UIResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserApiApplicationTests extends BaseIT{

	@Autowired
	private MockMvc mvc;

	@Test
	void zipITTest() throws Exception {
		final String email = "saju_rrk@yahoo.com";
		//create User
		User user = User.builder()
				.email(email)
				.userName("saju_rrk")
				.monthlyIncome(BigDecimal.valueOf(10000))
				.monthlyExpense(BigDecimal.valueOf(8000))
				.build();

		ObjectMapper mapper = new ObjectMapper();
		String requestBody = mapper.writeValueAsString(user);

		ResultActions result = mvc.perform(MockMvcRequestBuilders.post("/user")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestBody))
				.andExpect(status().isOk());

		UIResponse createUserResponse = assertOutput(result,true);
		String userId = createUserResponse.getUserId();



		//create Account
		AccountRequest accountRequest = AccountRequest.builder()
				.userId(userId)
				.accountType(AccountType.ZIP_MONEY)
				.accountName("account1")
				.currency(CurrencyType.AUD)
				.build();

		requestBody = mapper.writeValueAsString(accountRequest);
		result = mvc.perform(MockMvcRequestBuilders.post("/account")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestBody))
				.andExpect(status().isOk());

		assertOutput(result, true);

		//list users
		result = mvc.perform(MockMvcRequestBuilders.get("/user"))
				.andExpect(status().isOk());
		assertOutput(result, true);

		//get User
		result = mvc.perform(MockMvcRequestBuilders.get("/user/{userId}",userId))
				.andExpect(status().isOk());
		assertOutput(result, true);

		//get User by email
		result = mvc.perform(MockMvcRequestBuilders.get("/user/email/{email}",email))
				.andExpect(status().isOk());
		assertOutput(result, true);

		//list accounts
		result = mvc.perform(MockMvcRequestBuilders.get("/account"))
				.andExpect(status().isOk());
		assertOutput(result, true);



		//create User exception - invalid email address
		User user1 = User.builder()
				.email("saju_rrk")
				.userName("saju_rrk")
				.monthlyIncome(BigDecimal.valueOf(10000))
				.monthlyExpense(BigDecimal.valueOf(8000))
				.build();

		requestBody = mapper.writeValueAsString(user1);

		result = mvc.perform(MockMvcRequestBuilders.post("/user")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestBody))
				.andExpect(status().isOk());
		assertOutput(result, false);

		//create User exception - email already present
		requestBody = mapper.writeValueAsString(user);

		result = mvc.perform(MockMvcRequestBuilders.post("/user")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestBody))
				.andExpect(status().isOk());
		assertOutput(result, false);

		//create User with less credit limit
		User user2 = User.builder()
				.email("sajurrk@gmail.com")
				.userName("sajurrk@gmail.com")
				.monthlyIncome(BigDecimal.valueOf(10000))
				.monthlyExpense(BigDecimal.valueOf(9200))
				.build();

		requestBody = mapper.writeValueAsString(user2);

		result = mvc.perform(MockMvcRequestBuilders.post("/user")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestBody))
				.andExpect(status().isOk());
		final String userIdInsufficientCredit = assertOutput(result, true).getUserId();

		//create account for userIdInsufficientCredit to get exception
		AccountRequest accountRequest1 = AccountRequest.builder()
				.userId(userIdInsufficientCredit)
				.accountType(AccountType.ZIP_MONEY)
				.accountName("account2")
				.currency(CurrencyType.AUD)
				.build();

		requestBody = mapper.writeValueAsString(accountRequest1);
		result = mvc.perform(MockMvcRequestBuilders.post("/account")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestBody))
				.andExpect(status().isOk());

		assertOutput(result, false);


		//create account with invalid userId
		AccountRequest accountRequest2 = AccountRequest.builder()
				.userId(UUID.randomUUID().toString())
				.accountType(AccountType.ZIP_MONEY)
				.accountName("account")
				.currency(CurrencyType.AUD)
				.build();

		requestBody = mapper.writeValueAsString(accountRequest2);
		result = mvc.perform(MockMvcRequestBuilders.post("/account")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestBody))
				.andExpect(status().isOk());

		assertOutput(result, false);


	}

	UIResponse assertOutput(ResultActions result, boolean expected) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		final String actualOutput = result.andReturn().getResponse().getContentAsString().trim();
		System.out.println(actualOutput);
		UIResponse actualRes = mapper.readValue(actualOutput, UIResponse.class);
		Assertions.assertEquals(expected, actualRes.getSuccess());
		return actualRes;
	}
}
