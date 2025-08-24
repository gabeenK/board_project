package com.ukm.ssgb.config;

import com.ukm.ssgb.aspect.filter.JwtAccessDeniedHandler;
import com.ukm.ssgb.aspect.filter.JwtAuthenticationEntryPoint;
import com.ukm.ssgb.aspect.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static com.ukm.ssgb.type.RoleType.*;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
                // URL 접근 권한 설정
                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                        // Test API
                        .requestMatchers("/api/test/**").permitAll()

                        // 전체 허용
                        .requestMatchers("/show/health").permitAll() // 헬스 체크
                        .requestMatchers("/api/v1/login/**").permitAll() // 로그인
                        .requestMatchers("/api/v1/register/mail/**").permitAll() // 회원가입
                        .requestMatchers("/api/v1/register").permitAll() // 회원가입
                        .requestMatchers("/api/v1/register/admin").permitAll() // 관리자 회원가입
                        .requestMatchers("/api/v1/password/**").permitAll() // 비밀번호 찾기
                        .requestMatchers("/api/v1/token/refresh").permitAll() // 액세스 토큰 재발급

                        // 준 회원 기능
                        .requestMatchers("/api/v1/register/*").hasAuthority(ROLE_PENDING_USER.name())

                        // 유저 기능
                        .requestMatchers("/api/v1/myPage/**").hasAnyAuthority(ROLE_USER.name(), ROLE_ADMIN.name())
                        .requestMatchers("/api/v1/user/**").hasAnyAuthority(ROLE_USER.name(), ROLE_ADMIN.name())
                        .requestMatchers("/api/v1/chatRoom/**").hasAnyAuthority(ROLE_USER.name(), ROLE_ADMIN.name())

                        // 관리자 기능
                        .requestMatchers("/show/**").hasAuthority(ROLE_ADMIN.name())
                        .requestMatchers("/api/v1/admin/**").hasAuthority(ROLE_ADMIN.name())

                        // 그 외
                        .requestMatchers(GET, "/api/v1/**").permitAll()
                        .anyRequest().hasAnyAuthority(ROLE_USER.name(), ROLE_ADMIN.name()))

                // JWT 인증 필터 적용
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // 에러 핸들링
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .exceptionHandling((exceptionHandling) ->
                        exceptionHandling
                                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                .accessDeniedHandler(jwtAccessDeniedHandler)
                );

        return http.build();
    }

    // CORS 설정 추가
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("*")); // Make the below setting as * to allow connection from any hos
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "OPTIONS", "DELETE", "PATCH", "PUT"));
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type", "Content-Length", "Host", "User-Agent", "Accept", "Accept-Encoding", "Connection", "Origin", "Referer"));
        corsConfiguration.setExposedHeaders(List.of(CONTENT_DISPOSITION)); // 다른 도메인에 특정 헤더를 허용하기 위해 필요
        corsConfiguration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
