/**        
 * Copyright (c) 2013 by 苏州科大国创信息技术有限公司.    
 */    
package com.github.diamond.web.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.github.diamond.web.common.SuperDiamondEnumCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Create on @2013-8-28 @下午8:37:39 
 * @author bsli@ustcinfo.com
 */
abstract public class BaseController {
	protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	protected static final int pageSize=20;
	@ExceptionHandler()
	public void handleException(Exception exception, HttpServletRequest request, HttpServletResponse response) {
		logger.error(exception.getMessage(), exception);
		HttpSession httpSession = request.getSession();
		
		if(exception instanceof CannotGetJdbcConnectionException) {
			httpSession.setAttribute("message", SuperDiamondEnumCode.DATABASE_CONNECT_FAILED.getMsg());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} else {
			httpSession.setAttribute("message", exception.getMessage());
		}
		RequestDispatcher rd =  request.getSession().getServletContext().getRequestDispatcher("/error"); 
        try {
			rd.forward(request, response);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
