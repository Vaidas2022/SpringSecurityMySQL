package lt.codeacademy.learn.securitydb.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	 @Bean
	 SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http
	            .authorizeRequests(requests -> requests
	                .antMatchers("/admin/**").hasRole("ADMIN") // Assuming "ADMIN" is a constant or replaced with the actual role name
	                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN") // Assuming "USER" and "ADMIN" are constants or replaced with the actual role names
	                .antMatchers(HttpMethod.POST, "/registration").permitAll()
	                .antMatchers("/error").permitAll()
	                .antMatchers("/**").permitAll()
	                .anyRequest().authenticated()) // Changed from .anonymous() to .authenticated() for typical use cases
	            .formLogin( form -> form
	        			.loginPage("/login")
	        			.permitAll()
	        		  );

	        return http.build();
	 }
	


	@Bean 
    PasswordEncoder passwordEncoder() { 
        return new BCryptPasswordEncoder(); 
    }
}
