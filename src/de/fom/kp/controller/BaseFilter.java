package de.fom.kp.controller;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public abstract class BaseFilter implements Filter {

	@Override
	public void destroy() {
		
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		this.httpDoFilter((HttpServletRequest)request, (HttpServletResponse)response, chain);
	}

	public abstract void httpDoFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException;

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

	
}
