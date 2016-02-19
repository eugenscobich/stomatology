package md.stomatology.web.bean;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.savedrequest.SavedRequest;

import md.stomatology.model.User;
import md.stomatology.util.WebUtil;

@ManagedBean
@ViewScoped
public class LoginBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static Logger LOG = LoggerFactory.getLogger(LoginBean.class);
	
	private String userName = null; 
    private String password = null;
    private boolean rememberMe = false;
    private String redirectUrl;
    private User user;
    
    @ManagedProperty(value="#{authenticationManagerBean}")
    private AuthenticationManager authenticationManager = null;
    
    @ManagedProperty(value="#{persistentTokenBasedRememberMeServices}")
    private RememberMeServices rememberMeServices;
    
    @PostConstruct
    public void init() {
    	 HttpServletRequest httpServletRequest = WebUtil.getHttpServletRequest();
    	 SavedRequest savedRequest = (SavedRequest)httpServletRequest.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");
    	 if (savedRequest != null) {
    		 redirectUrl = savedRequest.getRedirectUrl();
    	 }
    }

    public String login() throws IOException {
        try {
        	
        	UsernamePasswordAuthenticationToken  requestToken = new UsernamePasswordAuthenticationToken(this.getUserName(), this.getPassword());
        	//requestToken.setDetails(new WebAuthenticationDetails(WebUtil.getHttpServletRequest()));  
            Authentication result = authenticationManager.authenticate(requestToken);
            user = (User) result.getPrincipal();
            if (result.isAuthenticated()) {
            	SecurityContextHolder.getContext().setAuthentication(result);
            	if (rememberMe) {
                	rememberMeServices.loginSuccess(WebUtil.getHttpServletRequest(), WebUtil.getHttpServletResponse(), result);
                }
            } else {
            	rememberMeServices.loginFail(WebUtil.getHttpServletRequest(), WebUtil.getHttpServletResponse());
            }
            
        } catch (BadCredentialsException e) {
            LOG.error(e.getMessage(), e);
            WebUtil.addErrorMessage("bad-credentials-exception");
            password = null;
            return "";
        } catch (AuthenticationException e) {
            LOG.error(e.getMessage(), e);
            WebUtil.addErrorMessage("authentication-exception");
            password = null;
            return "";
        }
        password = null;
        if (StringUtils.isNotBlank(redirectUrl)) {
	     	FacesContext.getCurrentInstance().getExternalContext().redirect(redirectUrl);
	    	return "";
	    }
        
        return "pretty:index";
    }

    public String cancel() {
        return null;
    }

    public String logout() {
    	user = null;
    	rememberMeServices.loginFail(WebUtil.getHttpServletRequest(), WebUtil.getHttpServletResponse());
        SecurityContextHolder.clearContext();
        return "pretty:index";
    }
 
    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean getRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	public RememberMeServices getRememberMeServices() {
		return rememberMeServices;
	}

	public void setRememberMeServices(RememberMeServices rememberMeServices) {
		this.rememberMeServices = rememberMeServices;
	}

}
