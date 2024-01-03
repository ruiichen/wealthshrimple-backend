package com.wealthshrimple.WealthShrimple.security;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity

public class SecurityConfiguration {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((authz) -> authz
					.anyRequest().authenticated()
					
					
			)
			.cors(withDefaults())
			.oauth2ResourceServer(oauth2 -> oauth2.jwt());
		http.cors();
		return http.build();
	}
	
//	@Bean
//	CorsConfigurationSource corsConfigurationSource() {
//	    CorsConfiguration configuration = new CorsConfiguration();
//	    configuration.setAllowedOrigins(Collections.singletonList("*"));
//	    configuration.setAllowedHeaders(Collections.singletonList("*"));
//	    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH", "HEAD"));
//	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//	    source.registerCorsConfiguration("/**", configuration);
//	    return source;
//	}
	
} 