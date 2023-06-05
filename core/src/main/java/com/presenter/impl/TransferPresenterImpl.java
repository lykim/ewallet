package com.presenter.impl;

import com.presenter.TransferPresenter;
import com.stories.OveralTopTransactionStory;
import com.stories.TransferStory;
import com.stories.UserTopTransactionStory;

import core.requestModel.RequestModel;
import core.requestModel.TransferRequestModel;
import core.responseModel.ArrayResponseModel;
import core.responseModel.ResponseModel;
import core.responseModel.UserTransaction;

public class TransferPresenterImpl implements TransferPresenter{

	@Override
	public ResponseModel<String> transfer(TransferRequestModel requestModel) {
		TransferStory story = new TransferStory(requestModel);
		story.execute();
		return story.getResponseModel();
	}

	@Override
	public ArrayResponseModel<UserTransaction> topTransactionPerUser(RequestModel requestModel) {
		UserTopTransactionStory story = new UserTopTransactionStory(requestModel);
		story.execute();
		return story.getResponseModel();
	}

	@Override
	public ArrayResponseModel<UserTransaction> overallTopTransactionPerUser(RequestModel requestModel) {
		OveralTopTransactionStory story = new  OveralTopTransactionStory(requestModel);
		story.execute();
		return story.getResponseModel();
	}

}
