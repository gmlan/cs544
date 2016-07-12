package presentation.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import business.externalinterfaces.Address;
import business.externalinterfaces.CreditCard;
import business.externalinterfaces.UserSubsystem;
import business.usersubsystem.AddressImpl;
import business.usersubsystem.CreditCardImpl;
import business.usersubsystem.UserImpl;
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
	
	@RequestMapping(value = "/login", method = {RequestMethod.GET})
	public String index(Model model) {
		Login login = new Login(0, "");
		model.addAttribute("login", login);
		return "login";
	}
	
    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public String authenticate(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("login") Login login, Model model, BindingResult result) {
    	int authorizationLevel = 0;
		if("root".equals(login.getLoginId().toLowerCase()) &&
			request.getServletContext().getInitParameter("root_password").equals(login.getPassword())){
			authorizationLevel = 1;
		}
		
		login.setLoggedIn(true);//TODO: dev phase only!
		login.setAdmin(authorizationLevel == 1);
		
		request.getSession().setAttribute(Constants.LOGGED_IN, login);
		
		String lastVisitedUrl = (String)request.getSession().getAttribute(Constants.LAST_REQUEST_URL);
		if(lastVisitedUrl != null && lastVisitedUrl.length() > 0){
			try {
				response.sendRedirect(lastVisitedUrl);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{    		
			return authorizationLevel == 1 ? "redirect:/admin" : "redirect:/";
		}	
    	
        return "login";
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpSession session) {
		Login login = (Login)session.getAttribute(Constants.LOGGED_IN);
		if(login != null) {
    		session.setAttribute(Constants.LOGGED_IN, null);
		}
		
        return "redirect:/";
    }
}
