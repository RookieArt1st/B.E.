package com.example.demo.config;

import com.example.demo.google.enums.Role;
import com.example.demo.google.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
   @Autowired
   private final CustomOAuth2UserService customOAuth2UserService;


   protected void configure(HttpSecurity http) throws Exception{
      CharacterEncodingFilter filter = new CharacterEncodingFilter();
      filter.setEncoding("UTF-8");
      filter.setForceEncoding(true);
      http.addFilterBefore(filter, CsrfFilter.class);
   }
   @Bean
   SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http.csrf(AbstractHttpConfigurer::disable)
              .headers(AbstractHttpConfigurer::disable)
              .authorizeHttpRequests((authorizeRequests)->
                      authorizeRequests.requestMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                              .requestMatchers("/api/v1/**").hasRole(Role.ARTIST.name())
                              .anyRequest().authenticated())
              .logout((logout)-> logout.logoutSuccessUrl("/"))
              .oauth2Login(oauth2Login -> oauth2Login.userInfoEndpoint(
                      userInfoEndpointConfig -> userInfoEndpointConfig.userService(customOAuth2UserService)));

      return http.build();
   }
}
