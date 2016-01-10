/**
 * 
 */
package md.stomatology.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import md.stomatology.model.User;
import md.stomatology.service.UserService;

/**
 * @author Siva
 *
 */
@Controller
public class UserController 
{
	private static UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		UserController.userService = userService;
	}
	
	public static User getCurrentUser()
	{
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    if (principal instanceof UserDetails) 
	    {
	    	String username = ((UserDetails) principal).getUsername();
	    	User loginUser = userService.getByUsername(username);
	    	return loginUser;
	    }

	    return null;
	}
}

