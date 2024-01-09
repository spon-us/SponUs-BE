package com.sponus.sponusbe.global.config;

import java.util.Arrays;
import java.util.stream.Stream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import com.sponus.sponusbe.auth.jwt.filter.CustomLoginFilter;
import com.sponus.sponusbe.auth.jwt.filter.CustomLogoutHandler;
import com.sponus.sponusbe.auth.jwt.filter.JwtExceptionFilter;
import com.sponus.sponusbe.auth.jwt.filter.JwtFilter;
import com.sponus.sponusbe.auth.jwt.util.JwtUtil;
import com.sponus.sponusbe.auth.jwt.util.RedisUtil;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final String[] swaggerUrls = {"/swagger-ui/**", "/v3/**"};
	private final String[] authUrls = {"/", "/api/groups/join/**", "/api/groups/login/**"};
	private final String[] allowedUrls = Stream.concat(Arrays.stream(swaggerUrls), Arrays.stream(authUrls))
		.toArray(String[]::new);

	private final AuthenticationConfiguration authenticationConfiguration;
	private final JwtUtil jwtUtil;
	private final RedisUtil redisUtil;

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
			.cors((cors) -> cors
				.configurationSource(CorsConfig.apiConfigurationSource()));

		// csrf disable
		http
			.csrf(AbstractHttpConfigurer::disable);

		// form 로그인 방식 disable
		http
			.formLogin(AbstractHttpConfigurer::disable);

		// http basic 인증 방식 disable
		http
			.httpBasic(AbstractHttpConfigurer::disable);

		// 경로별 인가 작업
		http
			.authorizeHttpRequests((auth) -> auth
				.requestMatchers(allowedUrls).permitAll()
				.requestMatchers("/").permitAll()
				.anyRequest().authenticated()
			);

		// Jwt Filter (with login)
		CustomLoginFilter loginFilter = new CustomLoginFilter(
			authenticationManager(authenticationConfiguration), jwtUtil
		);
		loginFilter.setFilterProcessesUrl("/api/groups/login");

		http
			.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);

		http
			.addFilterBefore(new JwtFilter(jwtUtil, redisUtil), CustomLoginFilter.class);

		// Exception Handle filter
		http
			.addFilterBefore(new JwtExceptionFilter(), LogoutFilter.class);

		// 세션 설정
		http
			.sessionManagement((session) -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			);

		// Logout Filter
		http
			.logout((logout) -> logout
				.logoutUrl("/api/groups/logout")
				.logoutSuccessUrl("/")
				.addLogoutHandler(new CustomLogoutHandler(redisUtil, jwtUtil))
			);

		return http.build();
	}
}
