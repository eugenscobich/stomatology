/**
 * 
 */
package md.stomatology.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import md.stomatology.service.CustomUserDetailsService;
import md.stomatology.service.PersistentTokenService;

/**
 * @author Siva
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private PersistentTokenRepository persistentTokenService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder registry) throws Exception {
		 registry.userDetailsService(customUserDetailsService);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**"); // #3
	}
	
	protected AuthenticationSuccessHandler authenticationSuccessHandler(){
		SimpleUrlAuthenticationSuccessHandler simpleUrlAuthenticationSuccessHandler = new SimpleUrlAuthenticationSuccessHandler();
		simpleUrlAuthenticationSuccessHandler.setUseReferer(true);
		return simpleUrlAuthenticationSuccessHandler;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.antMatchers("/", "/logout", "/error", "/invalid-session", "/javax.faces.resource/**").permitAll()

				.antMatchers("/customer-list/**", "/edit-customer/**", "/view-customer/**", "/edit-visit/**", "/view-visit/**").hasRole("EMPLOYEE")
				//.antMatchers("/edit-customer/**").hasRole("ADMIN")
				.and()
				.exceptionHandling().accessDeniedPage("/access-denied")
				.and()
				.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/login-page-which-does-not-exist")
				.failureUrl("/login?error").permitAll()
				//.successHandler(authenticationSuccessHandler())
				.and()
				.sessionManagement().invalidSessionUrl("/invalid-session")
				.and().rememberMe().key("secret").rememberMeServices(persistentTokenBasedRememberMeServices())
				.and()
				.logout().disable();
	}

	
	@Bean
	public PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices(){
		PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices = new PersistentTokenBasedRememberMeServices("secret", customUserDetailsService, persistentTokenService);
		persistentTokenBasedRememberMeServices.setParameter("mainForm:rememberMe_input");
		return persistentTokenBasedRememberMeServices;
	}
}
