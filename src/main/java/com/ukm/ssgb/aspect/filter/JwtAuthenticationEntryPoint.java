package com.ukm.ssgb.aspect.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ukm.ssgb.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@AllArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper objectMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
		log.info("[{}] {}", request.getMethod(), request.getRequestURI());

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());

		try {
			ErrorResponse result = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), authException.getMessage());
			response.getWriter().write(objectMapper.writeValueAsString(result));
		} catch (IOException ex) {
			log.error(ex.getMessage());
		}
	}
}