package org.yapp.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatchers;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.yapp.domain.auth.application.jwt.JwtFilter;

import java.util.Collections;

import lombok.RequiredArgsConstructor;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtFilter jwtFilter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.csrf(AbstractHttpConfigurer::disable)
               .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
               .httpBasic(AbstractHttpConfigurer::disable)
               .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .authorizeHttpRequests(registry -> registry.requestMatchers(getMatcherForUserAndAdmin())
                                                          .hasAnyRole("USER", "ADMIN")
                                                          .requestMatchers(getMatcherForAnyone())
                                                          .permitAll()
                                                          .anyRequest()
                                                          .hasAnyRole("ADMIN"))
               .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
               .build();
  }

  private CorsConfigurationSource corsConfigurationSource() {
    return request -> {
      CorsConfiguration config = new CorsConfiguration();
      config.setAllowedHeaders(Collections.singletonList("*"));
      config.setAllowedMethods(Collections.singletonList("*"));
      config.setAllowedOriginPatterns(Collections.singletonList("*"));
      return config;
    };
  }

  private RequestMatcher getMatcherForAnyone() {
    return RequestMatchers.anyOf(antMatcher("/api/login/**"), antMatcher("/api/**"), antMatcher("/swagger-ui/**"),
        antMatcher("/v3/api-docs/**"), antMatcher("/swagger-ui.html"));
  }

  private RequestMatcher getMatcherForRegister() {
    return RequestMatchers.anyOf(antMatcher("/api/profiles/init"));
  }

  private RequestMatcher getMatcherForUserAndAdmin() {
    return RequestMatchers.anyOf(antMatcher("/user") //TODO: 임시이며 추후 url에 따라 수정해야.
    );
  }
}