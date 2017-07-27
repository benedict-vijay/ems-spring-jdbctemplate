package com.core.ems.controller;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.core.ems.service.UserService;
import com.core.ems.util.CryptUtil;

public class EMSInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private UserService userService;
	
	private final Log logger= LogFactory.getLog(getClass());
	
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        String url = request.getRequestURL().toString();
        logger.info("INTERCEPT: Request URL::" + url + ":: Start Time=" + System.currentTimeMillis());
        request.setAttribute("startTime", startTime);
        boolean forward = true;
        
        // IF THE URL STIRNG CONTAINS /employee/login.rest THEN DONT DO ANY CHECK JUST PASS IT ON
        // IF THE URL STRING CONTAINS /employee/ and NOT /employee/login.rest THEN CHECK THE REQUEST HEADER FOR THE KEY "Authorization" AND FETCH THE VALUE FROM IT.
        // THIS VALUE IS THE TOKEN. NOW DECRYPT THE TOKEN AND WE WILL GET USERID, LABID AND DATE
        
        	
    	if(!url.contains("/employee/login.rest")) {
        	if(url.contains("/employee/")) {
        		String token = request.getHeader("Authorization");
        		logger.info("INTERCEPT: Authorization request Header: token: " + token);
        		
        		if(token == null || "".equals(token.trim())) {
	        		token = request.getParameter("Authorization");
	        		logger.info("INTERCEPT: Authorization request Parameter: token: " + token);
        		}
        		
        		boolean validToken = false; 
        		
        		if(token != null && !"".equals(token.trim())) {
	        		logger.info("INTERCEPT: TOKEN: "+ token);
	        		String decryptedToken = CryptUtil.decryptText(token);

	        		
	        		logger.info("INTERCEPT: DECRYPTED_TOKEN: "+ decryptedToken);
	        		if(decryptedToken != null) {
	        			String[] tokenArr = decryptedToken.split("\\|");
	        			int tokenArrLength = tokenArr.length;
	        			
	        			if(tokenArrLength == 3) {
	        				logger.info("INTERCEPT: TOKEN:SESSIONID "+ tokenArr[0]);
	        				logger.info("INTERCEPT: TOKEN:USERID "+ tokenArr[1]);
	        				logger.info("INTERCEPT: TOKEN:DATE "+ tokenArr[2]);
	        				
	        				Map<String, String> userSessionMap = userService.getUserSessionById(tokenArr[0]);
	        				
	        				validToken = userSessionMap != null && userSessionMap.get("userId") != null && userSessionMap.get("userId").equals(tokenArr[1]);
	        			}
	        		}
	        		
        		}
        		
        		
        		if(!validToken) {
	        		forward = false;
	        		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        		PrintWriter writer = response.getWriter();
	        		writer.write("Inalid Token Error");
	        		writer.flush();
        		}
        		
        	}
    	}
        
        return forward;
    }

}

