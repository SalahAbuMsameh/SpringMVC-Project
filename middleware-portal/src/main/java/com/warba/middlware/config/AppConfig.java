package com.warba.middlware.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.handler.MappedInterceptor;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.warba.middlware.control.interceptor.ThymeleafLayoutInterceptor;

/**
 * 
 * @author Salah Abu Msameh
 */
@Configuration
public class AppConfig {

	/**
	 * Register thymeleaf layout interceptor
	 * @return
	 */
	@Bean
	public MappedInterceptor thymeleafLayoutInterceptor() {
		return new MappedInterceptor(null, new ThymeleafLayoutInterceptor());
	}
	
	/**
	 * Local session resolver
	 * @return
	 */
	@Bean(name="localeResolver")
	public LocaleResolver localeResolver(){
		
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(Locale.US);
		
		return localeResolver;
	}
	
	/**
	 * Register locale change interceptor
	 * @return
	 */
	@Bean
	public MappedInterceptor localeChangeInterceptor() {
		
		LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
		interceptor.setParamName("lang");
		
		return new MappedInterceptor(null, interceptor);
	}
	
	/**
	 * Register messages resource bundles
	 * @return
	 */
	@Bean(name ="messageSource")
	public MessageSource messageSource() {
		
		ReloadableResourceBundleMessageSource msgSource = new ReloadableResourceBundleMessageSource();
		
		msgSource.setBasename("/i18n/messages");
		msgSource.setDefaultEncoding("UTF-8");
		
		return msgSource;
	}
	
	/**
	 * Register custom user details service
	 * @return
	 */
//	@Bean(name = "userDetailsService")
//	public UserDetailsService userDetailsService() {
//		return new CorpUserDetailsService();
//	}
}
