package com.auth.base.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);
	
	@Value("${user.credentials.file.path}")
	private String credentialsFilePath;

	@Bean
	public AuthenticationManager getAuthenticationManager() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(inMemoryUserDetailsManager());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/show-info1/**","/filter-shows1/**").authenticated()
		.anyRequest().permitAll().and()
		.formLogin()
			.loginPage("/")
				.defaultSuccessUrl("/home")
					.failureUrl("/error")
						.and()
							.logout()
								.logoutUrl("/logout")
									.logoutSuccessUrl("/")
										.and().
											csrf()
												.disable();
		
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
		List<UserDetails> userDetailsList = new ArrayList<>();
		try(Stream<String> stream = Files.lines(Paths.get(credentialsFilePath))) {
			stream.forEach(line->{
				String username = line.split(":")[0];
				String password = line.split(":")[1];
				userDetailsList.add(User.withUsername(username).password(passwordEncoder().encode(password))
						.roles("EMPLOYEE", "USER", "ADMIN").build());
			});
		} catch (IOException e) {
			logger.error(e.getMessage());
		}

		return new InMemoryUserDetailsManager(userDetailsList);
	}

}
