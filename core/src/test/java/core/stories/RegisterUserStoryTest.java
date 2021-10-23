package core.stories;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.stories.RegisterUserStory;

import core.gateway.UserGateway;
import core.gateway.inmemory.UserGatewayMock;
import core.model.User;
import core.requestModel.UserRequestModel;
import core.responseModel.ResponseModel;

public class RegisterUserStoryTest extends BaseStoriesTest{
	/**
	 * Response code 201 success
	 * 400 bad request
	 * 409 username already exist
	 */
	
	@Test
	public void givenEmptyUsername_willReturnBadRequest() {
		UserRequestModel requestModel = new UserRequestModel();
		requestModel.userName = "";
		RegisterUserStory story = new RegisterUserStory(requestModel);
		story.execute();
		ResponseModel response = story.getResponseModel();
		assertEquals(400, response.code);
		assertEquals("bad request", response.message);
	}
	
	@Test
	public void givenValidUsername_willRegisterUser() {
		String expectedUsername = "user1";
		UserRequestModel requestModel = new UserRequestModel();
		requestModel.userName = expectedUsername;
		RegisterUserStory story = new RegisterUserStory(requestModel);
		story.execute();
		User user = userGateway.findUserByUsername(expectedUsername);
		assertEquals(expectedUsername, user.getUsername());	
	}
	
	@Test
	public void givenValidUsername_willReturnAuthToken() {
		UserRequestModel requestModel = new UserRequestModel();
		requestModel.userName = "user2";
		RegisterUserStory story = new RegisterUserStory(requestModel);
		story.execute();
		ResponseModel<User> response = story.getResponseModel();
		assertNotNull(response.token);
		assertEquals(201, response.code);
	}
	
	@Test
	public void givenExistingUsername_willReturnErrorMessage() {
		UserRequestModel requestModel = new UserRequestModel();
		requestModel.userName = "user2";
		RegisterUserStory story = new RegisterUserStory(requestModel);
		story.execute();
		ResponseModel<User> response = story.getResponseModel();
		assertEquals("username already exist", response.message);
		assertEquals(409, response.code);
	}
}
