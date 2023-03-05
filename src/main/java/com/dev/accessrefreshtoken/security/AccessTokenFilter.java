package com.dev.accessrefreshtoken.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dev.accessrefreshtoken.helper.JwtHelper;
import com.dev.accessrefreshtoken.model.User;
import com.dev.accessrefreshtoken.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AccessTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtHelper jwtHelper;

	@Autowired
	private UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			Optional<String> accessToken = parseAccessToken(request);
			if (accessToken.isPresent() && jwtHelper.validateAccessToken(accessToken.get())) {
				String userId = jwtHelper.getUserIdFromAccessToken(accessToken.get());
				User user = userService.findById(userId);
				UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(user, null,
						user.getAuthorities());
				upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(upat);
			}

		} catch (Exception e) {
			log.error("cannot set authentication", e);
		}
		filterChain.doFilter(request, response);
	}

	private Optional<String> parseAccessToken(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
			return Optional.of(authHeader.replace("Bearer ", ""));
		}
		return Optional.empty();
	}

}
