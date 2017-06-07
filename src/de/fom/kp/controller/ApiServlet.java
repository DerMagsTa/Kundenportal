package de.fom.kp.controller;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.internal.compiler.parser.ParserBasicInformation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.fom.kp.dao.MesswertDao;
import de.fom.kp.dao.PersonDao;
import de.fom.kp.dao.TestPersonDao;


@WebServlet("/api/*")
public class ApiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Inject 
	private PersonDao dao;
	private MesswertDao mDao;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new GsonBuilder().create();
		switch (request.getPathInfo()) {
		case "/personsearch":
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			gson.toJson(dao.list(), response.getWriter());
			break;
		case "/zaehlerstaende":
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			gson.toJson(mDao.listByZaehler( Integer.parseInt(request.getParameter("id"))), response.getWriter());
			break;
		default:
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
