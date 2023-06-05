package com.stories.base;

import core.requestModel.RequestModel;
import core.responseModel.ResponseModel;

public abstract class BaseStory <I extends RequestModel, O extends ResponseModel<?>> {
	protected O responseModel;
	protected I requestModel;
	
	public abstract void execute();
	public O getResponseModel() {
		return responseModel;
	}
}
