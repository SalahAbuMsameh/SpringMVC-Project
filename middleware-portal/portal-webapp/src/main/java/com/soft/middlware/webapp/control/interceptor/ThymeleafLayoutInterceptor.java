package com.soft.middlware.webapp.control.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.soft.middlware.webapp.presentation.Pages;



/**
 * <p>Custom interceptor for thymeleaf template engine</p>
 * 
 * <p>This interceptor gets the original view name which returned from the handler method of the controller & 
 * replace it with the main view name, then the original view name will be set as variable for the main layout 
 * in order to be replace the view content at the layout content fragment</p>
 * 
 * @author Salah Abu Msameh
 */
public class ThymeleafLayoutInterceptor extends HandlerInterceptorAdapter {
	
	private static final String MAIN_LAYOUT = "templates/main";
	private static final String VIEW_VARIABLE_NAME = "view";
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		if (modelAndView == null || !modelAndView.hasView()) {
            return;
        }
		
        String originalViewName = modelAndView.getViewName();
        
        if(login(originalViewName) ||isRedirectOrForward(originalViewName)) {
        	return;
		}
        
        modelAndView.setViewName(MAIN_LAYOUT);
        modelAndView.addObject(VIEW_VARIABLE_NAME, originalViewName);
	}
	
	/**
	 * 
	 * @param viewName
	 * @return
	 */
	private boolean login(String viewName) {
		return viewName.startsWith(Pages.LOGIN_PAGE);
	}

	/**
	 * 
	 * @param viewName
	 * @return
	 */
	private boolean isRedirectOrForward(String viewName) {
        return viewName.startsWith("redirect:") || viewName.startsWith("forward:");
    }   
}
