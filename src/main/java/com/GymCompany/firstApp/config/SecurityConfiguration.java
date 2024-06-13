package com.GymCompany.firstApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.GymCompany.firstApp.jwt.JwtAuthenticationFilter;
import com.GymCompany.firstApp.jwt.JwtTokenProvider;

@Configuration
public class SecurityConfiguration {
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .httpBasic(httpBasic -> httpBasic.disable()) // REST API는 UI를 사용하지 않으므로 기본설정을 비활성화
            .csrf(csrf -> csrf.disable()) // REST API는 csrf 보안이 필요 없으므로 비활성화
            .sessionManagement(sessionManagement -> 
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // JWT Token 인증방식으로 세션은 필요 없으므로 비활성화
            )
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/getSecurityContext", "/swagger-ui/**", "/v3/api-docs/**", "/sign-api/sign-in", "/sign-api/sign-up", "/sign-api/exception", "/favicon.ico").permitAll() // swagger 
                .requestMatchers("/auth/**").permitAll() // 가입 및 로그인 주소는 허용
                
                .requestMatchers("/normalUser/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN") // 일반유저 로그인
                .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN") // 일반유저 로그인
                .requestMatchers("/gamePage").permitAll() // 가입 및 로그인 주소는 허용

                .requestMatchers("**exception**").permitAll()
                .anyRequest().hasAuthority("ROLE_ADMIN") // 나머지 요청은 인증된 ADMIN만 접근 가능
            )
            .exceptionHandling(exceptionHandling -> 
		            exceptionHandling
		            .authenticationEntryPoint(customAuthenticationEntryPoint)  // 여기서 loginMenu 로 리다이렋트
            )
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); // JWT Token 필터를 id/password 인증 필터 이전에 추가

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
