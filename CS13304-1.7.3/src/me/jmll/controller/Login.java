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
	private static final com.sun.media.jfxmedia.logging.Logger log = LogManager.getLogger();
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @SuppressWarnings("unchecked")
	public void init(){
    	/* 2(a) CrearÃ¡ un objeto de tipo Map llamado DB el cual
    	 *  estarÃ¡ compuesto de String como llaves 
    	 *  y me.jmll.model.User como valor.
    	 * */
    	Map<String, User> DB = null;
    	if (this.getServletContext().getAttribute("DB") == null){
    		DB = this.getServletContext();

    	} else {	
    		DB = (HashMap<String, User>) this.getServletContext().getAttribute("DB");
    		User user1 = new User("anakin", "deathStar2");
    		user1.setFullname("Anakin Skywalker");
    		User user2 = new User("kenobi", "starfighter");
    		user2.setFullname("Obi Wan Kenobi");
    		log.DEBUG("Se han creado usuarios jedi");
    		this.ServletContext.setAttribute("DB");
    	}
    	log.info("Usuarios en DB {}", DB.keySet());
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* 3. obtendrÃ¡ el request dispatcher y enviarÃ¡ la solicitud 
		 * a /WEB-INF/views/session.jsp si el atributo de la sesiÃ³n
		 *  user no es nulo. De lo contrario, llamarÃ¡ al mÃ©todo doPost()
		 * */
		if (request.getSession().getAttribute("user") != null){
			 request.getRequestDispatcher("/WEB-INF/views/session.jsp").forward(request, response);
		} else {
			doPost(request,response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> errors = new ArrayList<String>();
		/* 4(a) a.	ValidarÃ¡ que los parÃ¡metros de la solicitud inputPassword e 
		 * inputUsername no sean nulos, de lo contrario agregarÃ¡ un elemento 
		 * string a la lista errors con el mensaje â€œYou should login firstâ€�, 
		 * registrarÃ¡ un evento WARN con el log con el mismo mensaje y 
		 * reenviarÃ¡ a /WEB-INF/views/login.jsp utilizando el requestDispatcher.
		 * */
		if (request.getParameter("inputUsername") != null && request.getParameter("inputPassword") != null ){
			String username = request.getParameter("inputUsername");
			String password = request.getParameter("inputPassword");
			log.info("Autentificando a {}", username);
			// valida usuario y password
			User user = login(username, password);
			if (user != null){
				/* 4(b). a.	Si el usuario no es nulo, asigna el atributo 
				 * user a la sesiÃ³n con el valor de getUsername y crea
				 *  un Cookie llamado fullName con el valor de getFullName
				 * */
	    		
				user = user.getUsername();
				Cookie fullName = new Cookie("fullName", user.getFullname());
			} else{
				log.error("Invalid username or password.");
				errors.add("Invalid username or password.");
				request.setAttribute("errors", errors);
				request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
			}
		} else {
    		errors.add("You should login first");
    		log.WARNING("You should login first");
			request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
		}
	}
	
	private User login(String username, String password){
		/* 5(a). Obtiene el atributo DB del ServletContext, 
		 *  y lo referencia a la variable DB para posteriormente
		 *  validar la existencia del usuario en cuestiÃ³n 
		 *  utilizando el mÃ©todo get(username) de la
		 *   clase HashMap.
		 * */
		if (username == null || password == null){
            return null;
        }
		
		DB = this.getServletContext().getAttribute("DB");
		user = DB.get(username);
         
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
