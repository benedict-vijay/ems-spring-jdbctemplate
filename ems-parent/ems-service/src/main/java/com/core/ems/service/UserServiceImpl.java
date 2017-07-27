package com.core.ems.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.ems.dao.UserDao;
import com.core.ems.model.UserDetailEntity;
import com.core.ems.model.UserSessionEntity;
import com.core.ems.util.DateUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	@Transactional
	public Map<String, String> getUserByName(String userName) {
		return entityToMap(userDao.getUserByName(userName));
	}

	@Override
	@Transactional
	public Map<String, String> getUserSessionById(String sessionId) {
		Map<String, String> userSessionMap = null;
		try {
			userSessionMap = entityToMap(userDao.getUserSessionById(Long.valueOf(sessionId)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userSessionMap;
	}

	@Override
	@Transactional
	public Map<String, String> getActiveUserSessionByUserId(String userId) {
		return entityToMap(userDao.getActiveUserSessionByUserId(Long.valueOf(userId)));
	}
	
	@Override
	@Transactional
	public Map<String, String> insertUserSession(String userId) {
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("success", "true");
		
		UserSessionEntity userSessionEntity = new UserSessionEntity();
		
		try {
			userSessionEntity.setUserId(Long.valueOf(userId));
			userSessionEntity.setLoginTime(new Date());
			userSessionEntity.setCreatedDate(new Date());
			userSessionEntity.setCreatedBy("ADMIN");
			userSessionEntity.setIsActive("1");
			
			boolean success = userDao.insertUserSession(userSessionEntity);
			resultMap.put("success", Boolean.toString(success));
			resultMap.put("message", "User session created successfully !!");
			
		} catch(Exception e) {
			e.printStackTrace();
			resultMap.put("message", e.getMessage());
			resultMap.put("success", "false");
		}
		
		return resultMap;
	}

	@Override
	@Transactional
	public Map<String, String> logoutUserSession(Map<String, String> inputMap) {

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("success", "true");

		try {
			UserDetailEntity userDetailEntity = userDao.getUserByName(inputMap.get("userName"));
			UserSessionEntity userSessionEntity = userDao.getActiveUserSessionByUserId(userDetailEntity.getUserId());
			if(userSessionEntity != null) {

				userSessionEntity.setLogoutTime(new Date());
				userSessionEntity.setIsActive("0");
				
				boolean success = userDao.updateUserSession(userSessionEntity);
				resultMap.put("success", Boolean.toString(success));
				resultMap.put("message", "User Logged out successfully !!");
			} else {
				resultMap.put("success", "false");
				resultMap.put("message", "user or session does not exist for id <" + inputMap.get("userName") + ">");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("message", e.getMessage());
			resultMap.put("success", "false");
		}
		
		return resultMap;
	}
	
	private Map<String, String> entityToMap(UserSessionEntity userSessionEntity){
		
		Map<String, String> map = new HashMap<String, String>();
		if(userSessionEntity != null) {
			map.put("sessionId", String.valueOf(userSessionEntity.getSessionId()));
			map.put("userId", String.valueOf(userSessionEntity.getUserId()));
			map.put("loginTime", DateUtil.getFormattedDateString(userSessionEntity.getLoginTime()));
			map.put("logoutTime", DateUtil.getFormattedDateString(userSessionEntity.getLogoutTime()));
			map.put("isActive", Boolean.toString(userSessionEntity.getIsActive().equals("1")));
		} 
		
		return map;
	}
	
	private Map<String, String> entityToMap(UserDetailEntity userDetailEntity){
		
		Map<String, String> map = new HashMap<String, String>();
		if(userDetailEntity != null) {
			map.put("userId", String.valueOf(userDetailEntity.getUserId()));
			map.put("userName", userDetailEntity.getUserName());
			map.put("password", userDetailEntity.getPassword());
			map.put("emailId", userDetailEntity.getEmailId());
			map.put("mobileNumber", userDetailEntity.getMobileNumber());
			map.put("userActive", Boolean.toString(userDetailEntity.getIsActive().equals("1")));
		} 
		
		return map;
	}
	
}
