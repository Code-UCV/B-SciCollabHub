package collab.backend.config.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import collab.backend.util.Permission;
import collab.backend.util.Role;

@Component
@EnableWebSecurity
@EnableMethodSecurity
public class HttpSecurityConfig {
    @Value("#{'${cors.allowed-methods}'.split(',')}")
    private List<String> allowedMethods;

    @Value("#{'${cors.allowed-headers}'.split(',')}")
    private List<String> allowedHeaders;

    @Value("#{'${cors.exposed-headers}'.split(',')}")
    private List<String> expectedHeaders;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtAuthenticationFilter authenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable())
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .exceptionHandling(customizer -> customizer.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
        .sessionManagement(sessionManagment -> sessionManagment.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .authorizeHttpRequests(builderRequests());

        return httpSecurity.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        config.setAllowedMethods(allowedMethods);
        config.setAllowedHeaders(allowedHeaders);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    private static Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> builderRequests() {
        /*
         * 'Verify-jwt' it's just a jwt verification for the frontend not the backend.
         */
        return authConfig -> {
            authConfig.requestMatchers(HttpMethod.GET, "/").permitAll();
            authConfig.requestMatchers(HttpMethod.POST, "/auth/signup").permitAll();

            authConfig.requestMatchers(HttpMethod.PUT, "/email/unknown/forgotpassword").permitAll();
            authConfig.requestMatchers(HttpMethod.PUT, "/email/validatecode").permitAll();
            authConfig.requestMatchers(HttpMethod.PUT, "/email/known/setpassword").permitAll();
            
            // Unable 'cause the password was encrypted by hash
            //authConfig.requestMatchers(HttpMethod.PUT, "/email/unknown/recoverypassword").permitAll();

            authConfig.requestMatchers(HttpMethod.POST, "/auth/login").permitAll();
            authConfig.requestMatchers(HttpMethod.POST, "/auth/logout").hasRole(Role.STUDENT.name());
            authConfig.requestMatchers(HttpMethod.POST, "/auth/verify-jwt").permitAll();
            
            /*
             * Profile Data
             */
            authConfig.requestMatchers(HttpMethod.GET, "/usr/data/rankbyuser").hasAuthority(Permission.LOGGED.name());
            authConfig.requestMatchers(HttpMethod.GET, "/usr/data/pointsbyusername").hasAuthority(Permission.LOGGED.name());

            /*
             * Profile Edit
             */
            authConfig.requestMatchers(HttpMethod.PUT, "/usr/data/edit/addurls").hasAuthority(Permission.LOGGED.name());
            authConfig.requestMatchers(HttpMethod.PUT, "/usr/data/edit/bio").hasAuthority(Permission.LOGGED.name());

            /*
             * Testing POO
             */
            authConfig.requestMatchers(HttpMethod.POST, "/test/poo/clasealumno").hasAuthority(Permission.LOGGED.name());
            
            /*
             * Podio
             */
            authConfig.requestMatchers(HttpMethod.GET, "/q/rankingpositions").hasAuthority(Permission.LOGGED.name());

            authConfig.requestMatchers(HttpMethod.GET, "/error").permitAll();
            authConfig.anyRequest().denyAll();
        };
    }
}
