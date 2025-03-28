package edu.icet.filter;

import edu.icet.entity.UserEntity;
import edu.icet.repository.UserDao;
import edu.icet.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserDao userDao;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if(authorization==null){
            System.out.println("authorization was null");
            filterChain.doFilter(request,response);
            return;
        }

        if(!authorization.startsWith("Bearer ")) {
            System.out.println("Bearer was null");

            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.split(" ")[1];
        String username = jwtService.getUsername(token);

        if(username==null){
            System.out.println(username+"is username was null");

            filterChain.doFilter(request,response);
            return;
        }

        UserEntity userDetails = userDao.findUserByEmail(username);

        if (userDetails==null){
            filterChain.doFilter(request,response);
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication()!=null){
            filterChain.doFilter(request,response);
            return;
        }
        UserDetails build = User.builder()
                                .username(userDetails.getEmail())
                                .password(userDetails.getPassword())
                                .build();

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(build, null, build.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        System.out.println(authorization);
        filterChain.doFilter(request,response);
    }
}
