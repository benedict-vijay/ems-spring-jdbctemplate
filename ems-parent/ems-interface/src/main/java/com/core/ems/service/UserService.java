package com.core.ems.service;

import java.util.Map;

public interface UserService {
	Map<String, String> getUserByName(String userName);
	Map<String, String> getUserSessionById(String sessionId);
	Map<String, String> getActiveUserSessionByUserId(String userId);
	Map<String, String> insertUserSession(String userId);
	Map<String, String> logoutUserSession(Map<String, String> inputMap);
}
