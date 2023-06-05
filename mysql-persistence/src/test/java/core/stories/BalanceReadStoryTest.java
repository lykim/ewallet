package core.stories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.stories.BalanceReadStory;
import com.stories.BalanceTopupStory;

import core.model.User;
import core.requestModel.BalanceTopupRequestModel;
import core.requestModel.RequestModel;
import core.responseModel.ResponseModel;

public class BalanceReadStoryTest extends BaseStoriesTest{
	private static ResponseModel<User> registerResponse;
	private static String registerdUser = "user1";
	@BeforeAll
	public static void beforeAll() {
		registerResponse = StoryHelper.registerUser(registerdUser);
	}
	
	@Test
	public void givenInvalidToken_returnTokenInvalid() {
		RequestModel requestModel = new RequestModel();
		ResponseModel<?> response = executeStoryAndGetResponse(requestModel);
		assertEquals(401, response.code);
		assertEquals("unauthorized", response.message);
		
	}
	
	@Test
	public void addBalance5000_thenReturnBalance() {
		addBalance("5000");
		RequestModel requestModel = new RequestModel();
		requestModel.token = registerResponse.token;
		ResponseModel<User> response = executeStoryAndGetResponse(requestModel);
		assertEquals(200, response.code);
		assertEquals("Balance read success", response.message);
		assertEquals(5000, response.content.getBalanceAmount());
	}
	
	private void addBalance(String balance) {
		BalanceTopupRequestModel requestModel = new BalanceTopupRequestModel();
		requestModel.amount = balance;
		requestModel.token = registerResponse.token;
		BalanceTopupStory registerStory = new BalanceTopupStory(requestModel);
		registerStory.execute();
	}
	
	private ResponseModel<User> executeStoryAndGetResponse(RequestModel requestModel){
		BalanceReadStory story = new BalanceReadStory(requestModel);
		story.execute();
		return story.getResponseModel();
	}
}
