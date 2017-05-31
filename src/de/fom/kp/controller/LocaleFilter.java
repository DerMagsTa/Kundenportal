package de.fom.kp.controller;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LocaleFilter extends BaseFilter {

	static String KEY = "javax.servlet.jsp.jstl.fmt.locale.session";
	
	@Override
	public void httpDoFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		response.setCharacterEncoding("UTF-8");
		Locale l = request.getLocale();
		
		if(request.getParameter("locale")!=null){
			l = Locale.forLanguageTag(request.getParameter("locale"));
			Cookie cookie = new Cookie("mylocale", l.getLanguage());
			cookie.setMaxAge(Integer.MAX_VALUE);
			response.addCookie(cookie);
			request.getSession().setAttribute(KEY, l);
		}else if(request.getSession().getAttribute(KEY)==null){
			Cookie[] cookies = request.getCookies();
			if(cookies!=null){
				for (Cookie cookie : cookies) {
					if("mylocale".equals(cookie.getName())){
						l = Locale.forLanguageTag(cookie.getValue());
					}
				}
			}
			request.getSession().setAttribute(KEY, l);
		}
		
		
		
		
		chain.doFilter(request, response);
	}

}
