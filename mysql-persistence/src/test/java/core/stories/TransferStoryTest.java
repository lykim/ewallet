package core.stories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.stories.BalanceTopupStory;
import com.stories.TransferStory;

import core.model.User;
import core.requestModel.BalanceTopupRequestModel;
import core.requestModel.TransferRequestModel;
import core.responseModel.ResponseModel;

@TestMethodOrder(OrderAnnotation.class)
public class TransferStoryTest extends BaseStoriesTest{
	private static ResponseModel<User> registerResponse1;
	private static ResponseModel<User> registerResponse2;
	private static String registerUser1 = "user1";
	private static String registerUser2 = "user2";
	@BeforeAll
	public static void beforeAll() {
		registerResponse1 = StoryHelper.registerUser(registerUser1);
		registerResponse2 = StoryHelper.registerUser(registerUser2);
	}
	
	@Test
	public void givenInvalidToken_returnTokenInvalid() {
		TransferRequestModel requestModel = new TransferRequestModel();
		ResponseModel<?> response = executeStoryAndGetResponse(requestModel);
		assertEquals(401, response.code);
		assertEquals("unauthorized", response.message);
	}
	
	@Test
	public void givenWrongDestination_thenReturnErrorMessage() {
		TransferRequestModel requestModel = new TransferRequestModel();
		requestModel.token = registerResponse1.token;
		requestModel.toUsername = "wronguser";
		requestModel.amount = 5000;
		ResponseModel<?> response = executeStoryAndGetResponse(requestModel);
		assertEquals(404, response.code);
		assertEquals("Destination user not found", response.message);
	}
	
	@Test
	@Order(1)
	public void givenBiggerAmountThanBalance_thenReturnErrorMessage() {
		addBalance(registerResponse1.token, "5000");
		TransferRequestModel requestModel = new TransferRequestModel();
		requestModel.token = registerResponse1.token;
		requestModel.toUsername = registerUser2;
		requestModel.amount = 6000;
		ResponseModel<?> response = executeStoryAndGetResponse(requestModel);
		assertEquals(400, response.code);
		assertEquals("Insufficient balance", response.message);
	}
	
	@Test
	@Order(2)
	public void givenAmount_thenSourceIsDeducted() {
		TransferRequestModel requestModel = new TransferRequestModel();
		requestModel.token = registerResponse1.token;
		requestModel.toUsername = registerUser2;
		requestModel.amount = 2000;
		ResponseModel<?> response = executeStoryAndGetResponse(requestModel);
		assertEquals(200, response.code);
		assertEquals("Transfer success", response.message);
		User sourceUser = userGateway.findUserByUsername(registerUser1);
		assertEquals(5000-2000, sourceUser.getBalanceAmount());
	}
	
	@Test
	@Order(3)
	public void givenAmount_thenDestinationIsAdded() {
		TransferRequestModel requestModel = new TransferRequestModel();
		requestModel.token = registerResponse1.token;
		requestModel.toUsername = registerUser2;
		requestModel.amount = 2000;
		ResponseModel<?> response = executeStoryAndGetResponse(requestModel);
		assertEquals(200, response.code);
		assertEquals("Transfer success", response.message);
		User destinationUser = userGateway.findUserByUsername(registerUser2);
		assertEquals(4000, destinationUser.getBalanceAmount());
	}
	
	private void addBalance(String token, String balance) {
		BalanceTopupRequestModel requestModel = new BalanceTopupRequestModel();
		requestModel.amount = balance;
		requestModel.token = token;
		BalanceTopupStory registerStory = new BalanceTopupStory(requestModel);
		registerStory.execute();
	}
	
	private ResponseModel<?> executeStoryAndGetResponse(TransferRequestModel requestModel){
		TransferStory story = new TransferStory(requestModel);
		story.execute();
		return story.getResponseModel();
	}
}
