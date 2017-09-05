package com.becky.securityboot.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class RequestUtil {
	
	public static final String AJAX_HEADER_NAME = "X-Requested-With";
	public static final String AJAX_HEADER_VALUE = "XMLHttpRequest";
	
	public static boolean isAjax(HttpServletRequest request){
		String requestedWith = request.getHeader(AJAX_HEADER_NAME);
		return requestedWith != null && AJAX_HEADER_VALUE.equals(requestedWith);
	}
	
	public static void responseWrite(HttpServletResponse response, JSONObject jsonObject){
		PrintWriter out = null; 
		try{
			out = response.getWriter();
			out.print(jsonObject);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			out.flush(); 
			out.close();
		}
		
	
	}
}
