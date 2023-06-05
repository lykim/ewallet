package com.presenter;

import core.model.User;
import core.requestModel.BalanceTopupRequestModel;
import core.requestModel.RequestModel;
import core.responseModel.ResponseModel;

public interface BalancePresenter {
	ResponseModel<String> topup(BalanceTopupRequestModel requestModel);
	ResponseModel<User> read(RequestModel requestModel);
}
