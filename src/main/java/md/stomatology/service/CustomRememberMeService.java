package md.stomatology.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

public class CustomRememberMeService extends PersistentTokenBasedRememberMeServices {

	public CustomRememberMeService(String key, UserDetailsService userDetailsService, PersistentTokenRepository tokenRepository) {
		super(key, userDetailsService, tokenRepository);
	}

}
