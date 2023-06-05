package core.stories;

import com.stories.RegisterUserStory;

import core.model.User;
import core.requestModel.UserRequestModel;
import core.responseModel.ResponseModel;

public class StoryHelper {
	public static ResponseModel<User> registerUser(String username) {
		UserRequestModel userRequestModel = new UserRequestModel();
		userRequestModel.userName = username;
		RegisterUserStory registerStory = new RegisterUserStory(userRequestModel);
		registerStory.execute();
		return registerStory.getResponseModel();
	}
}
