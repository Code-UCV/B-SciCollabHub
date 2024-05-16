package collab.backend.config.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import collab.backend.config.Services.JwtService;
import collab.backend.mod.login.model.User;
import collab.backend.mod.login.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request, 
        HttpServletResponse response, 
        FilterChain filterChain)
            throws ServletException, IOException {
        //Optional<String> jwt = parseAccessToken(request);
        
        /*if (!jwt.isPresent()) {
            filterChain.doFilter(request, response);
            return;
        }*/
        String authHeader = request.getHeader("Authorization"); // Bearer jwt

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.split(" ")[1];

        String username = jwtService.extractUsername(jwt.toString());

        User user = userService.findByUsername(username).get();

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            username, null, user.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authToken);
        
        filterChain.doFilter(request, response);
    }
    
}
