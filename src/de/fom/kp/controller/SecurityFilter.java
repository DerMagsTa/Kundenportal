package de.fom.kp.controller;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.lang3.*;

//In dieser Webanwendung wird für alle Links die auf html enden ein authentifizierter Benutzer benötigt 
//@WebFilter(urlPatterns="*.html")
public class SecurityFilter extends BaseFilter {

	@Override
	public void httpDoFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String registerURL = request.getContextPath() + "/register.html";

		if (!request.getRequestURI().equals(registerURL)) {
			if(request.getSession().getAttribute("user")==null){
				if(request.getMethod().toLowerCase().equals("get")){
					String path = request.getRequestURI();
					if(StringUtils.isNotBlank(request.getQueryString())){
						path += "?"+request.getQueryString();
					}
					request.getSession().setAttribute("path", path);
				}
				response.sendRedirect(request.getContextPath()+"/login.jsp");
				return;
			}
		}
		
		chain.doFilter(request, response);
	}
}
