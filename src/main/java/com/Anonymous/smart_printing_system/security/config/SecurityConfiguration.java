package com.Anonymous.smart_printing_system.security.config;


import com.Anonymous.smart_printing_system.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration
{
    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    private final AuthenticationEntryPoint authenticationEntryPoint;


    private final AccessDeniedHandler accessDeniedHandler;


    @Bean
    public SecurityFilterChain createSecurityFilterChain(HttpSecurity httpSecurity)
            throws Exception
    {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(AMRMRegistry ->
                        AMRMRegistry
                                .requestMatchers("api/v1/auth/**")
                                .permitAll()

                                .requestMatchers("public/**")
                                .permitAll()

                                .requestMatchers("api/v1/users/**")
                                .hasAnyRole("ADMIN", "STUDENT", "SPSO")

                                .requestMatchers("api/v1/admins/**")
                                .hasRole("ADMIN")

                                .requestMatchers("api/v1/spsos/**")
                                .hasAnyRole("SPSO", "ADMIN")

                                .requestMatchers("api/v1/students/**")
                                .hasAnyRole("STUDENT", "ADMIN")

                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(sessionManagementConfigurer ->
                        sessionManagementConfigurer
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptionHandlingConfigurer ->
                        exceptionHandlingConfigurer
                                .authenticationEntryPoint(authenticationEntryPoint)
                                .accessDeniedHandler(accessDeniedHandler))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    public PasswordEncoder createPasswordEncoder()
    {
        return new BCryptPasswordEncoder(12);
    }


    @Bean
    public AuthenticationManager createAuthenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception
    {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
