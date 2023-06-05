package core.stories;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.stories.LoginStory;
import com.stories.RegisterUserStory;

import core.model.User;
import core.requestModel.UserRequestModel;
import core.responseModel.ResponseModel;

public class LoginStoryTest extends BaseStoriesTest{
	@Test
	public void givenEmptyUsername_willReturnBadRequest() {
		UserRequestModel requestModel = new UserRequestModel();
		requestModel.userName = "";
		LoginStory story = new LoginStory(requestModel);
		story.execute();
		ResponseModel response = story.getResponseModel();
		assertEquals(400, response.code);
		assertEquals("bad request", response.message);
	}
	
	@Test
	public void givenValidUsername_willReturnAuthToken() {
		UserRequestModel requestModel = new UserRequestModel();
		requestModel.userName = "user1";
		RegisterUserStory registerStory = new RegisterUserStory(requestModel);
		registerStory.execute();
		LoginStory story = new LoginStory(requestModel);
		story.execute();
		ResponseModel<User> response = story.getResponseModel();
		assertNotNull(response.token);
		assertEquals(201, response.code);
	}
}
