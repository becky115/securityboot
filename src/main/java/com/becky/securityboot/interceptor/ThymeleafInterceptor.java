package com.becky.securityboot.interceptor;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.plugin.Intercepts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.becky.securityboot.controller.TestController;
import com.becky.securityboot.domain.UserDomain;
import com.becky.securityboot.service.MenuService;
import com.becky.securityboot.service.UserService;

public class ThymeleafInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(ThymeleafInterceptor.class);
	
	private static final String BASIC_LAYOUT = "layouts/basic";
	private static final String BASICMENU_LAYOUT = "layouts/basicMenu";
	private static final String BLANK_LAYOUT = "layouts/blank";
	private static final String DEFAULT_VIEW_ATTRIBUTE_NAME = "view";
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private UserService userService;
	

	private List<String> basicList;
	private List<String> basicMenuList;
	private List<String> blankList;
	private List<String> popupList;
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		if(modelAndView == null || !modelAndView.hasView()) return;
		System.out.println("modelAndView" + modelAndView.getModelMap());
		System.out.println("modelAndView" + modelAndView.getViewName());
		String originalViewName = modelAndView.getViewName();
		logger.debug("originalViewName: ", originalViewName);
		if(originalViewName == null || originalViewName.toString().trim().equals("") || isRedirectOrForward(originalViewName)) return;
		
		
		String requestUrl = request.getRequestURI().substring(request.getContextPath().length());
		Map<?, ?> paramMap = request.getParameterMap();
		
		Character popupFlag = 'n';//StringUtil.isEmpty(paramMap.get("popup")) ? 'n': paramMap.get("popup").toString().toLowerCase().charAt(0);
		
		logger.debug("requestUrl: ", requestUrl);
		logger.debug("paramMap: ", paramMap);
		System.out.println(request.getSession());
		System.out.println(userService);
		System.out.println("menuService " + menuService);
		UserDomain userDomain = userService.getUserDomain(request.getSession());
		Boolean depthFlag = false;
	//	String firstUrl = null;
		if(userDomain != null ){
			Integer groupSeq = userDomain.getGroupSeq();
			depthFlag = menuService.checkDepth(groupSeq, requestUrl);
			//firstUrl = menuService.getFirstMenu(groupSeq);
		}
		
		boolean popupListFlag = this.getPopupList().indexOf(requestUrl) > -1;
		boolean basicListFlag = this.getBasicList().indexOf(requestUrl) > -1;
		boolean basicMenuListFlag = this.getBasicMenuList().indexOf(requestUrl) > -1;
		
		if(popupFlag.equals('n') && !popupListFlag){
			if(depthFlag || basicMenuListFlag){
				modelAndView.setViewName(BASICMENU_LAYOUT);
			}else if(basicListFlag){
				modelAndView.setViewName(BASIC_LAYOUT);
			}else{
				modelAndView.setViewName(BLANK_LAYOUT);
			}
		}else{
			modelAndView.setViewName(BLANK_LAYOUT);
		}
		
		
		
		modelAndView.addObject(DEFAULT_VIEW_ATTRIBUTE_NAME, originalViewName);
	}
	
	
	private boolean isRedirectOrForward(String viewName) {
		return viewName.startsWith("redirect:") || viewName.startsWith("forward:");
	}


	public List<String> getBasicList() {
		return basicList;
	}


	public void setBasicList(List<String> basicList) {
		this.basicList = basicList;
	}


	public List<String> getBasicMenuList() {
		return basicMenuList;
	}


	public void setBasicMenuList(List<String> basicMenuList) {
		this.basicMenuList = basicMenuList;
	}


	public List<String> getBlankList() {
		return blankList;
	}


	public void setBlankList(List<String> blankList) {
		this.blankList = blankList;
	}


	public List<String> getPopupList() {
		return popupList;
	}


	public void setPopupList(List<String> popupList) {
		this.popupList = popupList;
	}


	
}
