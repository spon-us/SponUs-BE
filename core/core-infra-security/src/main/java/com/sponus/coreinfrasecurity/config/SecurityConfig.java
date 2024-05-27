package com.sponus.coreinfrasecurity.config;

import java.util.Arrays;
import java.util.stream.Stream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sponus.coreinfraredis.util.RedisUtil;
import com.sponus.coreinfrasecurity.jwt.exception.JwtAccessDeniedHandler;
import com.sponus.coreinfrasecurity.jwt.exception.JwtAuthenticationEntryPoint;
import com.sponus.coreinfrasecurity.jwt.filter.CustomLoginFilter;
import com.sponus.coreinfrasecurity.jwt.filter.CustomLogoutHandler;
import com.sponus.coreinfrasecurity.jwt.filter.JwtAuthenticationFilter;
import com.sponus.coreinfrasecurity.jwt.filter.JwtExceptionFilter;
import com.sponus.coreinfrasecurity.jwt.util.HttpResponseUtil;
import com.sponus.coreinfrasecurity.jwt.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

	private final String[] swaggerUrls = {"/swagger-ui/**", "/v3/**"};
	private final String[] authUrls = {
		"/",
		"/payment",
		"/api/v2/organizations/join/**",
		"/api/v2/auth/login/**",
		"/api/v2/auth/email/**",
		"/api/v2/report/**",
		"/api/v2/s3/**",
		"/api/v2/payments/**",
		"/api/v2/auth/reissue/**",
		"/api/v2/auth/verify-email/**",
		"/api/v2/auth/send-code/**",
		"/api/v2/auth/verify-code/**"
	};
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
			.cors(cors -> cors
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
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(allowedUrls).permitAll()
				.requestMatchers("/**").authenticated()
				.anyRequest().permitAll()
			);

		// Jwt Filter (with login)
		CustomLoginFilter loginFilter = new CustomLoginFilter(
			authenticationManager(authenticationConfiguration), jwtUtil, redisUtil
		);
		loginFilter.setFilterProcessesUrl("/api/v2/auth/login");

		http
			.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);

		http
			.addFilterBefore(new JwtAuthenticationFilter(jwtUtil, redisUtil), CustomLoginFilter.class);

		http
			.addFilterBefore(new JwtExceptionFilter(), JwtAuthenticationFilter.class);

		http
			.exceptionHandling(exception -> exception
				.authenticationEntryPoint(jwtAuthenticationEntryPoint)
				.accessDeniedHandler(jwtAccessDeniedHandler)
			);

		// 세션 사용 안함
		http
			.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			);

		// Logout Filter
		http
			.logout(logout -> logout
				.logoutUrl("/api/v2/auth/logout")
				.addLogoutHandler(new CustomLogoutHandler(redisUtil, jwtUtil))
				.logoutSuccessHandler((request, response, authentication) ->
					HttpResponseUtil.setSuccessResponse(
						response,
						HttpStatus.OK,
						"로그아웃 성공"
					)
				)
			);

		return http.build();
	}
}
