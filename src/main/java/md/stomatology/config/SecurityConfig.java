/**
 * 
 */
package md.stomatology.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import md.stomatology.service.CustomUserDetailsService;

/**
 * @author Siva
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
// @ImportResource("classpath:applicationContext-security.xml")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private DataSource dataSource;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder registry) throws Exception {
		/*
		 * registry .inMemoryAuthentication() .withUser("siva") // #1
		 * .password("siva") .roles("USER") .and() .withUser("admin") // #2
		 * .password("admin") .roles("ADMIN","USER");
		 */

		//registry.jdbcAuthentication().dataSource(dataSource);
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

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.antMatchers("/views/unsecure/**", "/javax.faces.resource/**").permitAll()
				//.antMatchers("/views/secure/admin/**").hasRole("ADMIN")
				.antMatchers("/views/secure/**").hasRole("USER").anyRequest().authenticated()
				.and().formLogin()
				.loginPage("/views/unsecure/login.xhtml")
				.loginProcessingUrl("/login")
				.failureUrl("/views/unsecure/login.xhtml?error").permitAll();
	}
}
