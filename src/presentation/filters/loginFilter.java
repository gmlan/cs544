package presentation.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import presentation.data.Login;
import presentation.util.Constants;

/**
 * Servlet Filter implementation class loginFilter
 */
@WebFilter("/loginFilter")
public class loginFilter implements Filter {

    /**
     * Default constructor.
     */
    public loginFilter() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
    	 
        // TODO Auto-generated method stub
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        Login login = (Login) req.getSession().getAttribute(Constants.LOGGED_IN);
        if((login == null || !login.isAdmin()) && 
        		req.getRequestURI().startsWith(req.getContextPath() + "/admin")){//no-administrator   
        	
        	req.getSession().setAttribute(Constants.LAST_REQUEST_URL, req.getRequestURI());        	
        	resp.sendRedirect(req.getContextPath() + "/login");
        }
        else if(login == null && 
        		req.getRequestURI().startsWith(req.getContextPath() + "/my")){//no-login user
        	req.getSession().setAttribute(Constants.LAST_REQUEST_URL, req.getRequestURI());
        	resp.sendRedirect(req.getContextPath() + "/login");
        }
        else{
        	if(!req.getRequestURI().startsWith(req.getContextPath() + "/login"))
        		req.getSession().setAttribute(Constants.LAST_REQUEST_URL, null);//clear
            chain.doFilter(request, response);
        }
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
        // TODO Auto-generated method stub
    }

}
