package com.core.ems.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.core.ems.model.UserDetailEntity;
import com.core.ems.model.UserSessionEntity;

@Repository
public class UserDaoImpl implements UserDao 
{

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public UserDetailEntity getUserByName(String userName) {
		String sql = "SELECT * FROM ems_user_detail WHERE USER_NAME = ?";

		UserDetailEntity userDetailEntity = (UserDetailEntity)jdbcTemplate.queryForObject(
				sql, new Object[] { userName },
				new BeanPropertyRowMapper<UserDetailEntity>(UserDetailEntity.class));

		return userDetailEntity;
	}
	
	@Override
	public UserSessionEntity getUserSessionById(Long sessionId) {
		String sql = "SELECT * FROM ems_user_session WHERE SESSION_ID = ? and IS_ACTIVE = ?";

		UserSessionEntity userSessionEntity = (UserSessionEntity)jdbcTemplate.queryForObject(
				sql, new Object[] { sessionId, "1" },
				new BeanPropertyRowMapper<UserSessionEntity>(UserSessionEntity.class));

		return userSessionEntity;
	}

	@Override
	public UserSessionEntity getActiveUserSessionByUserId(Long userId) {
		String sql = "SELECT * FROM ems_user_session WHERE USER_ID = ? and IS_ACTIVE = ?";

		UserSessionEntity userSessionEntity = (UserSessionEntity)jdbcTemplate.queryForObject(
				sql, new Object[] { userId, "1" },
				new BeanPropertyRowMapper<UserSessionEntity>(UserSessionEntity.class));

		return userSessionEntity;
	}
	
	@Override
	public boolean insertUserSession(UserSessionEntity userSession) {
		
		String query = "insert into ems_user_session (USER_ID, LOGIN_TIME, LOGOUT_TIME, IS_ACTIVE, CREATED_BY, CREATED_DATE) VALUES (?,?,?,?,?,?)";
		
		int rows = jdbcTemplate.update(query, new Object[] { userSession.getUserId(),userSession.getLoginTime(),userSession.getLogoutTime(),userSession.getIsActive(),
				                                             userSession.getCreatedBy(),userSession.getCreatedDate()});
		return rows > 0;
	}
	
	@Override
	public boolean updateUserSession(UserSessionEntity userSession) {
		String query = "update ems_user_session set LOGOUT_TIME = ?, IS_ACTIVE = ? WHERE USER_ID = ?";
		
		int rows = jdbcTemplate.update(query, new Object[] { userSession.getLogoutTime(),userSession.getIsActive(),userSession.getUserId() });
		return rows > 0;
	}
	
}