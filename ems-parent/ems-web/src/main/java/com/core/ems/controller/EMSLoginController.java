package com.core.ems.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core.ems.service.UserService;
import com.core.ems.util.CryptUtil;

@RestController
@RequestMapping("/employee")
public class EMSLoginController
{
	@Autowired
	private UserService userService;

	  @RequestMapping(value="/login.rest", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	  public Map<String, String> login(@RequestBody Map<String, String> loginRequest, HttpServletResponse response) {
		  Map<String, String> loginResponse = null;
		  try {
			  Map<String, String> userMap = userService.getUserByName(loginRequest.get("userName"));
			  
			  if(userMap != null && userMap.get("userId") != null) {
				  if (userMap.get("userName").equals(loginRequest.get("userName"))
						  && userMap.get("password").equals(loginRequest.get("password"))) {
					
					  loginResponse = userService.insertUserSession(userMap.get("userId"));
					  
				  } else {
					  loginResponse = new HashMap<String, String>();
				      loginResponse.put("message", "Invalid username/password!");
				  }
				  
			  } else {
				  loginResponse = new HashMap<String, String>();
			      loginResponse.put("message", "User Does Not exist!");
			  }
			  
			  if(loginResponse != null && "true".equals(loginResponse.get("success"))) {
				  Map<String, String> userSessionMap = userService.getActiveUserSessionByUserId(userMap.get("userId"));
				  String currentTime = String.valueOf(new Date().getTime());
				  String token = new StringBuffer().append(userSessionMap.get("sessionId")).append("|").append(userSessionMap.get("userId")).append("|").append(currentTime).toString();
				  
				  String encryptedToken = CryptUtil.encryptText(token);
				  System.out.println("encryptedToken ->  " + encryptedToken);
				  response.addHeader("Authorization", encryptedToken);
				  response.addHeader("Access-Control-Expose-Headers", "Authorization");
				  
			  }
		  } 
		  catch (Exception e) {
		      e.printStackTrace();
		      loginResponse = new HashMap<String, String>();
		      loginResponse.put("message", e.getMessage());
		  }
		  
		  return loginResponse;
	  }

	  @RequestMapping(value="/logout.rest", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	  public Map<String, String> logout(@RequestBody Map<String, String> logoutRequest) {
		  Map<String, String> logoutResponse = null;
		  try {
			  logoutResponse = userService.logoutUserSession(logoutRequest);
		  } 
		  catch (Exception e) {
		      e.printStackTrace();
		      logoutResponse = new HashMap<String, String>();
		      logoutResponse.put("message", e.getMessage());
		  }
		  
		  return logoutResponse;
	  }
	  
}