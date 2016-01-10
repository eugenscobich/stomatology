package md.stomatology.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

@ManagedBean(name="loginMgmtBean")
@RequestScoped
public class LoginBean {
  
    private String userName = null; 
    private String password = null;

    @ManagedProperty(value="#{authenticationManagerBean}")
    private AuthenticationManager authenticationManager = null;

    public String login() {
        try {
            Authentication request = new UsernamePasswordAuthenticationToken(this.getUserName(), this.getPassword());
            Authentication result = authenticationManager.authenticate(request);
            SecurityContextHolder.getContext().setAuthentication(result);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return "/views/unsecure/login.xhtml?faces-redirect=true";
        }
        return "/views/secure/list2.xhtml?faces-redirect=true";
    }

    public String cancel() {
        return null;
    }

    public String logout(){
        SecurityContextHolder.clearContext();
        return "/views/unsecure/login.xhtml?faces-redirect=true";
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
 
}
