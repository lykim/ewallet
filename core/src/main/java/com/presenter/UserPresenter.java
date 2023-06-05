package com.presenter;

import core.model.User;
import core.requestModel.UserRequestModel;
import core.responseModel.ResponseModel;

public interface UserPresenter {
	ResponseModel<User> registerUser(UserRequestModel requestModel);
	ResponseModel<User> login(UserRequestModel requestModel);
}
