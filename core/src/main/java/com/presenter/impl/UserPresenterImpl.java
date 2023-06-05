package com.presenter.impl;

import com.presenter.UserPresenter;
import com.stories.LoginStory;
import com.stories.RegisterUserStory;

import core.model.User;
import core.requestModel.UserRequestModel;
import core.responseModel.ResponseModel;

public class UserPresenterImpl implements UserPresenter{

	@Override
	public ResponseModel<User> registerUser(UserRequestModel requestModel) {
		RegisterUserStory story = new RegisterUserStory(requestModel);
		story.execute();
		return story.getResponseModel();
	}

	@Override
	public ResponseModel<User> login(UserRequestModel requestModel) {
		LoginStory story = new LoginStory(requestModel);
		story.execute();
		return story.getResponseModel();
	}

}
