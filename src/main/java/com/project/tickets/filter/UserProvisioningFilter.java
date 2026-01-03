package com.project.tickets.filter;

import com.project.tickets.domain.entities.User;
import com.project.tickets.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserProvisioningFilter extends OncePerRequestFilter {

	private final UserRepository userRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// TODO--

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.isAuthenticated()
				&& authentication.getPrincipal() instanceof Jwt jwt) {
			UUID KeyCloaked = UUID.fromString(jwt.getSubject());

			if (!userRepository.existsById(KeyCloaked)) {
				User newUser = User.builder()
					.id(KeyCloaked)
					.name(jwt.getClaimAsString("preferred_username"))
					.email(jwt.getClaimAsString("email"))
					.build();

				userRepository.save(newUser);

			}
		}

		filterChain.doFilter(request, response);

	}

}
