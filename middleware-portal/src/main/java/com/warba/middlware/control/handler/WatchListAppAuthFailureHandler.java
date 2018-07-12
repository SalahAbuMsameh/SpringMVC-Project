package com.warba.middlware.control.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * 
 * @author Salah Abu Msameh
 */
@Deprecated
public class WatchListAppAuthFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException ex) throws IOException, ServletException {
		
//		request.setAttribute("error-msg", "Invalid username & password Ya M7traaaaam, " + ex.getMessage());
//		new DefaultRedirectStrategy().sendRedirect(request, response, Pages.LOGIN_PAGE);
 	}
}
