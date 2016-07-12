package presentation.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import business.exceptions.BackendException;
import business.externalinterfaces.User;
import business.externalinterfaces.UserSubsystem;
import presentation.data.Login;
import presentation.data.UserPres;
import presentation.util.Constants;

@Controller
public class LoginController {

	@Autowired
	@Qualifier("UserCacheService")
	UserSubsystem userSubsystem;

	@RequestMapping("/register")
	public String user(ModelMap modelMap) throws BackendException {
		modelMap.addAttribute("newUser", new UserPres());
		return "admin_user_add";
	}

	@RequestMapping(value = "/loginuser", method = { RequestMethod.GET })
	public String index(Model model) {
		Login login = new Login(0, "");
		model.addAttribute("login", login);
		return "login";
	}

	@RequestMapping(value = "/loginuser", method = { RequestMethod.POST })
	public String authenticate(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("login") Login login, Model model, BindingResult result) {
		String username = login.getLoginId();
		String password = login.getPassword();
		
		//We need to set a default administrator in web.xml in case Users table is empty
        if("root".equals(username.toLowerCase()) &&
                request.getServletContext().getInitParameter("root_password").equals(password)){
            login.setAdmin(true);
        }
        else{
        	User user = userSubsystem.getUserFromUsernameAndPassword(username, password);
    		if (user == null)
    			return "login";
    		
    		login.setAdmin(user.getAuthority() != null && user.getAuthority().equals("ROLE_ADMIN"));

    		//kept in session
    		request.getSession().setAttribute(Constants.LOGGED_IN_USERINFO, user);
        }     
        
        login.setLoggedIn(true);
		request.getSession().setAttribute(Constants.LOGGED_IN, login);
		
		String lastVisitedUrl = (String) request.getSession().getAttribute(Constants.LAST_REQUEST_URL);

		if (lastVisitedUrl != null && lastVisitedUrl.length() > 0) {
			try {
				response.sendRedirect(lastVisitedUrl);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			return "redirect:/";
		}
		return "login";
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		Login login = (Login) session.getAttribute(Constants.LOGGED_IN);
		if (login != null) {
			session.setAttribute(Constants.LOGGED_IN, null);
		}

		return "redirect:/";
	}
}
