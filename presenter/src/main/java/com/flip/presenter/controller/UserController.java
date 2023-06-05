package com.flip.presenter.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.presenter.UserPresenter;
import com.presenter.impl.UserPresenterImpl;

import core.model.User;
import core.requestModel.UserRequestModel;
import core.responseModel.ResponseModel;
import core.utils.StringUtils;

@RestController
public class UserController {
	UserPresenter userPresenter = new UserPresenterImpl();
	
	@PostMapping("/create_user")
	public ResponseEntity<Map<String,String>> registerUser(@RequestBody UserRequestModel requestModel) {
		ResponseModel<User> response = userPresenter.registerUser(requestModel);
		Map<String,String> map = new HashMap<String, String>();
		if(StringUtils.isNotEmpty(response.token)) {
			map.put("token", response.token);
		}else {
			map.put("message", response.message);
		}
		int responseCode = response.code > 0 ? response.code : 500;
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.resolve(responseCode));
	}
	
	@PostMapping("/login")
	public ResponseEntity<Map<String,String>> login(@RequestBody UserRequestModel requestModel) {
		ResponseModel<User> response = userPresenter.login(requestModel);
		Map<String,String> map = new HashMap<String, String>();
		if(StringUtils.isNotEmpty(response.token)) {
			map.put("token", response.token);
		}else {
			map.put("message", response.message);
		}
		int responseCode = response.code > 0 ? response.code : 500;
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.resolve(responseCode));
	}
}
