package com.flip.presenter.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;

public abstract class BaseController {
	protected String getTokenFromHeader(HttpHeaders requestHeader) {
		List<String> authorizations = requestHeader.get(HttpHeaders.AUTHORIZATION);
		String bearerToken = authorizations.get(0);
		if(bearerToken.startsWith("Bearer")) {
			bearerToken = bearerToken.substring(6).trim();
		}
		return bearerToken;
	}
}
