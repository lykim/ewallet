package com.stories;

import com.config.ApplicationConfig;
import com.stories.base.CommonStoryTemplate;

import core.gateway.GatewayConstant;
import core.gateway.TransferGateway;
import core.model.Authorizeable;
import core.requestModel.RequestModel;
import core.responseModel.ArrayResponseModel;
import core.responseModel.UserTransaction;

public class UserTopTransactionStory extends CommonStoryTemplate<RequestModel, 
			ArrayResponseModel<UserTransaction>>{
	TransferGateway transferGateway = (TransferGateway)ApplicationConfig.getInstance()
			.getGatewayConfig()
			.getGateway(GatewayConstant.TRANSFER_GATEWAY);
	
	public UserTopTransactionStory(RequestModel requestModel) {
		this.requestModel = requestModel;
		this.responseModel = new ArrayResponseModel<>();
		this.authorize = new Authorizeable();
	}
	
	@Override
	protected void doBusinessProcess() {
		UserTransaction[] results =  transferGateway.getTopTransaction();
		responseModel.results = results;
		responseModel.code = 200;
	}

}
