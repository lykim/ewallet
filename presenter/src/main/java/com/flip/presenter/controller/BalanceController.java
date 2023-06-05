package com.flip.presenter.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.presenter.BalancePresenter;
import com.presenter.impl.BalancePresenterImpl;

import core.model.User;
import core.requestModel.BalanceTopupRequestModel;
import core.requestModel.RequestModel;
import core.responseModel.ResponseModel;

@RestController
public class BalanceController extends BaseController {
	BalancePresenter presenter = new BalancePresenterImpl();
	
	@PostMapping("/balance_topup")
	public ResponseEntity<String> topup(@RequestHeader HttpHeaders requestHeader, 
			@RequestBody BalanceTopupRequestModel requestModel) {
		requestModel.token = getTokenFromHeader(requestHeader);
		ResponseModel<?> response =  presenter.topup(requestModel);
		int responseCode = response.code > 0 ? response.code : 500;
		return new ResponseEntity<String>(response.message, HttpStatus.resolve(responseCode));
	}
	
	@GetMapping("/balance_read")
	public ResponseEntity<Map<String,Integer>> read(@RequestHeader HttpHeaders requestHeader) {
		RequestModel requestModel = new RequestModel();
		requestModel.token = getTokenFromHeader(requestHeader);
		ResponseModel<User> response =  presenter.read(requestModel);
		Map<String,Integer> map = new HashMap<String, Integer>();
		map.put("balance", response.content.getBalanceAmount());
		int responseCode = response.code > 0 ? response.code : 500;
		return new ResponseEntity<Map<String,Integer>>(map, HttpStatus.resolve(responseCode));
	}
}
