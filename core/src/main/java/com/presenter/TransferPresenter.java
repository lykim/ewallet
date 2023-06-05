package com.presenter;

import core.requestModel.RequestModel;
import core.requestModel.TransferRequestModel;
import core.responseModel.ArrayResponseModel;
import core.responseModel.ResponseModel;
import core.responseModel.UserTransaction;

public interface TransferPresenter {
	ResponseModel<String> transfer(TransferRequestModel requestModel);
	ArrayResponseModel<UserTransaction> topTransactionPerUser(RequestModel requestModel);
	ArrayResponseModel<UserTransaction> overallTopTransactionPerUser(RequestModel requestModel);
}
