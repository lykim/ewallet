package core.stories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.stories.BalanceTopupStory;
import com.stories.OveralTopTransactionStory;
import com.stories.TransferStory;

import core.model.User;
import core.requestModel.BalanceTopupRequestModel;
import core.requestModel.RequestModel;
import core.requestModel.TransferRequestModel;
import core.responseModel.ArrayResponseModel;
import core.responseModel.ResponseModel;
import core.responseModel.UserTransaction;

public class OverallTopTransactionTest extends BaseStoriesTest{
	private static ResponseModel<User> registerResponse1;
	private static ResponseModel<User> registerResponse2;
	private static ResponseModel<User> registerResponse3;
	private static ResponseModel<User> registerResponse4;
	private static String registerUser1 = "user1";
	private static String registerUser2 = "user2";
	private static String registerUser3 = "user3";
	private static String registerUser4 = "user4";
	
	@BeforeAll
	public static void beforeAll() {
		registerResponse1 = StoryHelper.registerUser(registerUser1);
		registerResponse2 = StoryHelper.registerUser(registerUser2);
		registerResponse3 = StoryHelper.registerUser(registerUser3);
		registerResponse4 = StoryHelper.registerUser(registerUser4);
		addBalance(registerResponse1.token, "9000000");
		addBalance(registerResponse2.token, "9000000");
		addBalance(registerResponse3.token, "9000000");
		addBalance(registerResponse4.token, "9000000");
		transfer(registerResponse1.token, registerUser2 , 100000);
		transfer(registerResponse1.token, registerUser3 , 200000);
		transfer(registerResponse1.token, registerUser4 , 300000);
		transfer(registerResponse2.token, registerUser4 , 400000);
		transfer(registerResponse3.token, registerUser4 , 500000);
	}
	
	@Test
	public void givenInvalidToken_returnTokenInvalid() {
		RequestModel requestModel = new RequestModel();
		ResponseModel<?> response = executeStoryAndGetResponse(requestModel);
		assertEquals(401, response.code);
		assertEquals("unauthorized", response.message);
	}
	
	@Test
	public void givenToken_thenReturnResultDesc() {
		RequestModel requestModel = new RequestModel();
		requestModel.token = registerResponse1.token;
		ArrayResponseModel<UserTransaction> response = executeStoryAndGetResponse(requestModel);
		assertEquals(200, response.code);
		UserTransaction[] topTransactions = response.results;
		assertEquals(600000, topTransactions[0].amount);
		assertEquals(registerUser1, topTransactions[0].username);
		assertEquals(500000, topTransactions[1].amount);
		assertEquals(registerUser3, topTransactions[1].username);
		assertEquals(400000, topTransactions[2].amount);
		assertEquals(registerUser2, topTransactions[2].username);
	}
	
	private ArrayResponseModel<UserTransaction> executeStoryAndGetResponse(RequestModel requestModel){
		OveralTopTransactionStory story = new OveralTopTransactionStory(requestModel);
		story.execute();
		return story.getResponseModel();
	}
	
	private static void addBalance(String token, String balance) {
		BalanceTopupRequestModel requestModel = new BalanceTopupRequestModel();
		requestModel.amount = balance;
		requestModel.token = token;
		BalanceTopupStory registerStory = new BalanceTopupStory(requestModel);
		registerStory.execute();
	}
	
	private static void transfer(String tokenFrom, String userNameTo, int amount) {
		TransferRequestModel requestModel = new TransferRequestModel();
		requestModel.token = tokenFrom;
		requestModel.toUsername = userNameTo;
		requestModel.amount = amount;
		TransferStory story = new TransferStory(requestModel);
		story.execute();
	}
}
