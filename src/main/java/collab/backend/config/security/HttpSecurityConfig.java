package collab.backend.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import collab.backend.util.Role;

@Component
@EnableWebSecurity
@EnableMethodSecurity
public class HttpSecurityConfig {
    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtAuthenticationFilter authenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable())
        .sessionManagement(sessionManagment -> sessionManagment.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .authorizeHttpRequests(builderRequests());

        return httpSecurity.build();
    }

    private static Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> builderRequests() {
        return authConfig -> {
            authConfig.requestMatchers(HttpMethod.GET, "/").permitAll();
            authConfig.requestMatchers(HttpMethod.POST, "/user/signup").permitAll();
            authConfig.requestMatchers(HttpMethod.POST, "/user/login").permitAll();
            authConfig.requestMatchers(HttpMethod.POST, "/user/logout").hasRole(Role.STUDENT.name());
            authConfig.requestMatchers(HttpMethod.GET, "/welcome").permitAll();
            authConfig.requestMatchers(HttpMethod.GET, "/error").permitAll();
            authConfig.anyRequest().denyAll();
        };
    }
}
