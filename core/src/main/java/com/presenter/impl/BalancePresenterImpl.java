package com.presenter.impl;

import com.presenter.BalancePresenter;
import com.stories.BalanceReadStory;
import com.stories.BalanceTopupStory;

import core.model.User;
import core.requestModel.BalanceTopupRequestModel;
import core.requestModel.RequestModel;
import core.responseModel.ResponseModel;

public class BalancePresenterImpl implements BalancePresenter {

	@Override
	public ResponseModel<String> topup(BalanceTopupRequestModel requestModel) {
		BalanceTopupStory story = new BalanceTopupStory(requestModel);
		story.execute();
		return story.getResponseModel();
	}

	@Override
	public ResponseModel<User> read(RequestModel requestModel) {
		BalanceReadStory story = new BalanceReadStory(requestModel);
		story.execute();
		return story.getResponseModel();
	}

}
