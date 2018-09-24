package com.soft.middlware.webapp.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.soft.middlware.webapp.config.security.encoder.AESPasswordEncoder;


/**
 * Web security configurations
 * 
 * @author Salah Abu Msameh
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    @Qualifier("userDetailsService")
	private UserDetailsService userDetailsSrv;
	
	/**
	 * @param http
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//register charset encoding filter before all the security filters
		registerCharsetEncodingFilter(http);
		
		http.authorizeRequests()
			//1. grant access to the resource
			.antMatchers("/resources/**").permitAll()
			//2. require authentication for any url
			.anyRequest().authenticated()
			.and().formLogin().loginPage("/login").permitAll()
			.and().logout().permitAll()
			.and().httpBasic();
		
		//register authentication handler
		//http.formLogin().failureHandler(new WatchListAppAuthFailureHandler());
	}

	/**
	 * Configure users authentication
	 * 
	 * @param auth
	 * @throws Exception
	 */
	@Autowired
	public void configureUsersAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsSrv).passwordEncoder(passwordEncoder());
	}
	
	/**
	 * Register charset encoding filter
	 */
	private void registerCharsetEncodingFilter(HttpSecurity http) {
		
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
        
		filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter, CsrfFilter.class);
	}
	
	/**
	 * 
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
	    //implements PasswordEncoder and overide encode method with the MD5 protocol
	    return new AESPasswordEncoder();
	}
}
