package me.jmll.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Servlet implementation class Session
 */
@WebServlet("/Sessions")
public class Session extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Session() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		List<String> errors = new ArrayList<String>();
		if (request.getSession().getAttribute("user") != null){
			request.getRequestDispatcher("/WEB-INF/views/session.jsp").forward(request, response);
		} else {
			log.warn("You should login first.");
			errors.add("You should login first.");
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
		}
		
		// Incrementa o inicializa el contador de visitas
		doVisit(request, response);

		// doCookie
		doCookie(request, response);
		
		fakeException();
	}
	
	private void fakeException(){
		try {
			throw new Exception("Excepción creada manualmente");
		}
		catch(Exception ex){
			log.error("fakeException: {}", ex.getMessage());
		}
	}	
	
	private void doVisit(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		// Incrementa o inicializa el contador de visitas
		if (session.getAttribute("visits") != null){
			// Reinicia contador si se encuentra el parámetro reset
			if (request.getParameter("reset") != null){
				session.setAttribute("visits", 0);
			} else {
				int visits = (int) session.getAttribute("visits");
				session.setAttribute("visits", visits+=1);
				}
		    }
		else if(request.getParameter("reset") != null){
			 session.setAttribute("visits", 0);
		}
		else {
		    session.setAttribute("visits", 1);
		}		
	}
	private void doCookie(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Crea nuevo Cookie
		Cookie cs13304 = new Cookie("CS13304", this.getServletContext().getInitParameter("tema"));
		cs13304.setComment(this.getServletContext().getInitParameter("tema"));
		// Asigna el path del cookie
		cs13304.setPath(request.getRequestURI());
		// Si es HTTP only
		cs13304.setHttpOnly(true);
		// Asigna máximo de 30 seg de vida
		cs13304.setMaxAge(30);
		//agrega cookie a la respuesta
		response.addCookie(cs13304);
		log.warn("Agregando cookie {} ({}) a {}", cs13304.getName(), cs13304.getComment(), request.getRemoteAddr());
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
