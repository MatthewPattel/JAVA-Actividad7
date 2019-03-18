package me.jmll.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Servlet implementation class Logout
 */
@WebServlet({ "/Logout", "/Logout.do" })
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Logout() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> errors = new ArrayList<String>();
		String user = (String) request.getSession().getAttribute("user");
		if (user != null){
			/*El método doGet obtiene el atributo de la sesión user,
			 *  el cual si existe utiliza el método removeAttribute() 
			 *  de la sesión para eliminarlo, posteriormente busca entre 
			 *  las Cookies la llamada fullName para asignarle un valor 
			 *  nulo y maxAge de 0. Finalmente, se invalida la sesión 
			 *  con el método invalidate()
			 * */
			
			/*
			 * Escribe aquí tu código
			 * 
			 * */
			errors.add("Logged out");
			request.setAttribute("errors", errors);
			log.warn("User {} has been logged out",user );
			request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
			} else {
				errors.add("You are not logged in.");
				request.setAttribute("errors", errors);
				request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
			}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
