package security;

import com.devlab.prodcatalog.backend.config.JwtConfig;
import com.devlab.prodcatalog.backend.dto.UserAuthenticationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

@Service
public class JwtUserPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private final JwtConfig jwtConfig;
    private final AuthenticationManager authenticationManager;

    public JwtUserPasswordAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig) {
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            UserAuthenticationDto authenticationUser = new ObjectMapper().readValue(request.getInputStream(), UserAuthenticationDto.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(authenticationUser.getEmail(), authenticationUser.getPassword());

            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException {

        SecretKey secretKey = jwtConfig.getSecretKeyForSigning();
        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationWeeks())))
                .signWith(secretKey)
                .compact();
        String jsonString = "{\"Authorization\": \"Bearer " + token + "\"}";
        response.setContentType("application/json");  // Set content type of the response so that jQuery knows what it can expect.
        response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
        response.getWriter().write(jsonString);

        response.addHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getAuthorizationHeader() + token);
    }
}
