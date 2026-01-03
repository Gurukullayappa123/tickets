package com.project.tickets.config;

import com.project.tickets.filter.UserProvisioningFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, UserProvisioningFilter userProvisioningFilter)
			throws Exception {

		http.authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.GET, "/api/v1/published/events/**")
			.permitAll()
			.anyRequest()
			.authenticated())
			.csrf(csrf -> csrf.disable())

			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

			.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))

			.addFilterAfter(userProvisioningFilter, BearerTokenAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public JwtDecoder jwtDecoder() {
		return NimbusJwtDecoder.withJwkSetUri("https://your-auth-domain/.well-known/jwks.json").build();
	}

}
