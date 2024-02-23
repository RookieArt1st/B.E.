package com.example.demo.config;

import com.example.demo.login.social.enums.Role;
import com.example.demo.login.social.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
   private final CustomOAuth2UserService customOAuth2UserService;

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

   @Bean
   public PasswordEncoder passwordEncoder(){
      return new BCryptPasswordEncoder();
   }
}
