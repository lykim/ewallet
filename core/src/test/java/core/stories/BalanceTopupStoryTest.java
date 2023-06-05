package core.stories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.stories.BalanceTopupStory;

import core.model.User;
import core.requestModel.BalanceTopupRequestModel;
import core.responseModel.ResponseModel;

@TestMethodOrder(OrderAnnotation.class)
public class BalanceTopupStoryTest extends BaseStoriesTest {
	private static ResponseModel<User> registerResponse;
	private static String registerdUser = "user1";
	@BeforeAll
	public static void beforeAll() {
		registerResponse = StoryHelper.registerUser(registerdUser);
	}
	
	@Test
	public void givenInvalidToken_returnTokenInvalid() {
		BalanceTopupRequestModel requestModel = new BalanceTopupRequestModel();
		requestModel.amount = "10000000";
		ResponseModel<?> response = executeStoryAndGetResponse(requestModel);
		assertEquals(401, response.code);
		assertEquals("unauthorized", response.message);
	}
	
	@Test
	public void givenInvalidInteger_returnInvalidAmount() {
		BalanceTopupRequestModel requestModel = new BalanceTopupRequestModel();
		requestModel.amount = "10000000Abc";
		requestModel.token = registerResponse.token;
		ResponseModel<?> response = executeStoryAndGetResponse(requestModel);
		assertEquals(400, response.code);
		assertEquals("invalid topup amount", response.message);
	}
	
	@Test
	public void givenAmountIs0_returnInvalidAmount() {
		BalanceTopupRequestModel requestModel = new BalanceTopupRequestModel();
		requestModel.amount = "0";
		requestModel.token = registerResponse.token;
		ResponseModel<?> response = executeStoryAndGetResponse(requestModel);
		assertEquals(400, response.code);
		assertEquals("invalid topup amount", response.message);
	}
	
	@Test
	public void givenAmountIs10000000_returnInvalidAmount() {
		BalanceTopupRequestModel requestModel = new BalanceTopupRequestModel();
		requestModel.amount = "10000000";
		requestModel.token = registerResponse.token;
		ResponseModel<?> response = executeStoryAndGetResponse(requestModel);
		assertEquals(400, response.code);
		assertEquals("invalid topup amount", response.message);
	}
	
	@Test
	@Order(1)
	public void givenAmount5000_thenAmountAdded5000() {
		BalanceTopupRequestModel requestModel = new BalanceTopupRequestModel();
		requestModel.amount = "5000";
		requestModel.token = registerResponse.token;
		ResponseModel<?> response = executeStoryAndGetResponse(requestModel);
		User user = userGateway.findUserByUsername(registerdUser);
		assertEquals(5000, user.getBalanceAmount());
		assertEquals(204, response.code);
		assertEquals("Topup successfull", response.message);
	}
	
	@Test
	@Order(2)
	public void givenAmount15000_thenAmountAdded15000() {
		BalanceTopupRequestModel requestModel = new BalanceTopupRequestModel();
		requestModel.amount = "15000";
		requestModel.token = registerResponse.token;
		ResponseModel<?> response = executeStoryAndGetResponse(requestModel);
		User user = userGateway.findUserByUsername(registerdUser);
		assertEquals(20000, user.getBalanceAmount());
		assertEquals(204, response.code);
		assertEquals("Topup successfull", response.message);
	}
	
	private ResponseModel<?> executeStoryAndGetResponse(BalanceTopupRequestModel requestModel){
		BalanceTopupStory story = new BalanceTopupStory(requestModel);
		story.execute();
		return story.getResponseModel();
	}
}
