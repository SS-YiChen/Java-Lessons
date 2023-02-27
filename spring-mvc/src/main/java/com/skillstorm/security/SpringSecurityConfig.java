package com.skillstorm.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity //must enable this
@EnableGlobalMethodSecurity(jsr250Enabled = false, prePostEnabled = true, securedEnabled = false) //must enable this
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String url = "jdbc:mysql://localhost:3306/zulmak";
	private static final String username = "root";
	private static final String password = "root";
	
	//lets me configure my SpringSecurityFilterChain (what features am i enabling?)
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// tells SPring that any request needs to be authorized
		//http.authorizeRequests().antMatchers("/**").authenticated(); //will throw a 403, no way to login
		// tells SPring that any request needs to be authorized, and you login through a form
		//http.authorizeRequests().antMatchers("/**").authenticated().and().formLogin();
		//http.authorizeRequests().antMatchers("/**").authenticated().and().httpBasic(); //gives a popup
		
		//ant matchers
		// ant is very strict
		// /dinos -> "/dinos"
		
		//mvc matchers
		// a little less strict
		// /dinos -> "/dinos/" or "/dinos"
		
//		http.authorizeRequests().mvcMatchers("/dinos/**").hasRole("ADMIN")
//			.anyRequest().authenticated().and().formLogin();
//		http.authorizeRequests().antMatchers("/**").permitAll().anyRequest().anonymous();
		
//		http.authorizeRequests()
//			.mvcMatchers("/dinos/**").hasAnyRole("ADMIN", "USER").anyRequest().authenticated()
//			.antMatchers("/**").permitAll().anyRequest().anonymous().and().formLogin();
		
		http.authorizeRequests()
			.antMatchers("/**").permitAll().anyRequest().anonymous().and().formLogin()
			.and().logout().invalidateHttpSession(true).clearAuthentication(true).deleteCookies("custom");
		//for logout you would send a post request to http://localhost:8080/spring-mvc/logout
	}
	
	//store the Username/ password in a database
	//tells spring to just run this on startup
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authBuilder) throws Exception {
		//schema it expects is inside schema.sql
		//need encryption to protect user's passwords
		authBuilder.jdbcAuthentication().dataSource(mysqlDataSource()).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public DataSource mysqlDataSource() {
		DriverManagerDataSource datasource = new DriverManagerDataSource(url, username, password);
		datasource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		return datasource;
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		//this is a one-way hash (no going back)
		//by default BCrypt goes 10 rounds for password "strength"
		return new BCryptPasswordEncoder();
	}
	
	//used to setup our user
	@Override
	//@Bean
	public UserDetailsService userDetailsServiceBean() throws Exception {
		// would not want to setup our userdetails service in this way in prod, just dev/ learning
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		// are able to implement custom userdetails, since it's an interface
		//we're just going to use the built in one
		manager.createUser(User.withUsername("admin").password("admin").roles("ADMIN").build());
		return manager;
	}
}
