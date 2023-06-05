package com.flip.presenter.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.presenter.TransferPresenter;
import com.presenter.impl.TransferPresenterImpl;
import com.stories.UserTopTransactionStory;

import core.requestModel.RequestModel;
import core.requestModel.TransferRequestModel;
import core.responseModel.ArrayResponseModel;
import core.responseModel.ResponseModel;
import core.responseModel.UserTransaction;

@RestController
public class TransferController extends BaseController{
	
	TransferPresenter presenter = new TransferPresenterImpl();
	
	@PostMapping("/transfer")
	public ResponseEntity<String> transfer(@RequestBody InnerTransferRequestModel requestModel,
			@RequestHeader HttpHeaders requestHeader) {
		TransferRequestModel transferRequestModel = new TransferRequestModel();
		transferRequestModel.token = getTokenFromHeader(requestHeader);
		transferRequestModel.amount = requestModel.amount;
		transferRequestModel.toUsername = requestModel.to_username;
		
		ResponseModel<?> response = presenter.transfer(transferRequestModel);
		int responseCode = response.code > 0 ? response.code : 500;
		return new ResponseEntity<String>(response.message, HttpStatus.resolve(responseCode));
	}
	
	@GetMapping("/top_transaction_per_user")
	public ResponseEntity<UserTransaction[]> topTransaction(@RequestHeader HttpHeaders requestHeader) {
		RequestModel requestModel = new RequestModel();
		requestModel.token = getTokenFromHeader(requestHeader);
		ArrayResponseModel<UserTransaction> response =  presenter.topTransactionPerUser(requestModel);
		int responseCode = response.code > 0 ? response.code : 500;
		return new ResponseEntity<UserTransaction[]>(response.results, HttpStatus.resolve(responseCode));
	}
	
	@GetMapping("/top_users")
	public ResponseEntity<UserTransaction[]> overalTopTransaction(@RequestHeader HttpHeaders requestHeader) {
		RequestModel requestModel = new RequestModel();
		requestModel.token = getTokenFromHeader(requestHeader);
		ArrayResponseModel<UserTransaction> response =  presenter.overallTopTransactionPerUser(requestModel);
		int responseCode = response.code > 0 ? response.code : 500;
		return new ResponseEntity<UserTransaction[]>(response.results, HttpStatus.resolve(responseCode));
	}
	
	static class InnerTransferRequestModel{
		public String to_username;
		public int amount;
	}
}
