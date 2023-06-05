package com.stories.base;

import core.exception.BusinessException;
import core.model.Authorizeable;
import core.requestModel.RequestModel;
import core.responseModel.ResponseModel;
import core.utils.StringUtils;
import core.utils.TokenUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public abstract class CommonStoryTemplate <I extends RequestModel, O extends ResponseModel<?>> extends BaseStory<I,O>{
	protected Authorizeable authorize;

	@Override
	public void execute() {
		try {
			authorizing();
			checkRequestModel();
			doBusinessProcess();
		}catch (BusinessException e) {
			responseModel.code = e.getCode();
			responseModel.message = e.getMessage();
		}catch (Exception e) {
			e.printStackTrace();
			responseModel.code = 500;
			responseModel.message = e.getMessage();
		}
		
	}
	
	private void authorizing() {
		if(authorize != null) {
			if(StringUtils.isNotEmpty(requestModel.token)) {
				Jws<Claims> jwsClaims = TokenUtils.parseClaims(requestModel.token);
				if(jwsClaims == null) throw new BusinessException(401, "unauthorized");
			}else {
				throw new BusinessException(401, "unauthorized");
			}
		}
	}
	
	private void checkRequestModel() {
		if(requestModel == null) {
			throw new BusinessException(400, "bad request");
		}		
	}

	
	protected abstract void doBusinessProcess();
}
