package com.core.ems.dao;

import com.core.ems.model.UserDetailEntity;
import com.core.ems.model.UserSessionEntity;

public interface UserDao {
	UserDetailEntity getUserByName(String userName);
	UserSessionEntity getUserSessionById(Long sessionId);
	UserSessionEntity getActiveUserSessionByUserId(Long userId);
	boolean insertUserSession(UserSessionEntity userSession);
	boolean updateUserSession(UserSessionEntity userSession);
}