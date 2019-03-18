package me.jmll.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import me.jmll.model.User;

/**
 * Servlet implementation class Login
 */
@WebServlet(
		description = "Login Servlet", 
		urlPatterns = { 
				"/Login", 
				"/Login.do"
		})
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger();
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @SuppressWarnings("unchecked")
	public void init(){
    	/* 2(a) Creará un objeto de tipo Map llamado DB el cual
    	 *  estará compuesto de String como llaves 
    	 *  y me.jmll.model.User como valor.
    	 * */
    	Map<String, User> DB = null;
    	if (this.getServletContext().getAttribute("DB") == null){
    		/*
    		 * Escribe aquí tu código
    		 * 
    		 * */

    	} else {
    		DB = (HashMap<String, User>) this.getServletContext().getAttribute("DB");
    	}
    	log.info("Usuarios en DB {}", DB.keySet());
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* 3. obtendrá el request dispatcher y enviará la solicitud 
		 * a /WEB-INF/views/session.jsp si el atributo de la sesión
		 *  user no es nulo. De lo contrario, llamará al método doPost()
		 * */
		if (request.getSession().getAttribute("user") != null){
    		/*
    		 * Escribe aquí tu código
    		 * 
    		 * */
			
		} else {
			doPost(request,response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> errors = new ArrayList<String>();
		/* 4(a) a.	Validará que los parámetros de la solicitud inputPassword e 
		 * inputUsername no sean nulos, de lo contrario agregará un elemento 
		 * string a la lista errors con el mensaje “You should login first”, 
		 * registrará un evento WARN con el log con el mismo mensaje y 
		 * reenviará a /WEB-INF/views/login.jsp utilizando el requestDispatcher.
		 * */
		if (request.getParameter("inputUsername") != null && request.getParameter("inputPassword") != null ){
			String username = request.getParameter("inputUsername");
			String password = request.getParameter("inputPassword");
			log.info("Autentificando a {}", username);
			// valida usuario y password
			User user = login(username, password);
			if (user != null){
				/* 4(b). a.	Si el usuario no es nulo, asigna el atributo 
				 * user a la sesión con el valor de getUsername y crea
				 *  un Cookie llamado fullName con el valor de getFullName
				 * */
	    		
				/*
	    		 * Escribe aquí tu código
	    		 * 
	    		 * */
			} else{
				log.error("Invalid username or password.");
				errors.add("Invalid username or password.");
				request.setAttribute("errors", errors);
				request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
			}
		} else {
    		/*
    		 * Escribe aquí tu código
    		 * 
    		 * */

			request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
		}
	}
	
	private User login(String username, String password){
		/* 5(a). Obtiene el atributo DB del ServletContext, 
		 *  y lo referencia a la variable DB para posteriormente
		 *  validar la existencia del usuario en cuestión 
		 *  utilizando el método get(username) de la
		 *   clase HashMap.
		 * */
		if (username == null || password == null){
            return null;
        }
		/*
		 * Escribe aquí tu código
		 * 
		 * */
         
        if (user == null){
            return null;
        }
         
        if (!user.getPassword().equals(password.trim())){
            return null;
        }
        return user;
         
	}
	
	private void doSession(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		// Obtiene la session actual
		HttpSession session = request.getSession();
		log.debug("Inicia doSession para {}", session.getId());
		// Crea un objeto Date a partir de un Long que es cuando se creo el cookie
		Date createTime = new Date(session.getCreationTime());
		// Obtiene la session ID (JSESSIONID)
		String sessionId = session.getId();
		// Obtiene el last Accessed time en objeto Date
		Date lastAccessedTime = new Date(session.getLastAccessedTime());
		// Obtiene max inactive interval (timeout) en segundos
		int maxInactiveInterval = session.getMaxInactiveInterval();

		// Guarda atributos en la session
		session.setAttribute("lastAccessedTime", lastAccessedTime);
		session.setAttribute("creationTime", createTime);
		session.setAttribute("sessionId", sessionId);
		session.setAttribute("maxInactiveInterval", maxInactiveInterval);
		log.debug("Termina doSession para {}", session.getId());
	}
}
