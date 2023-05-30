package br.com.minsait.auth.authentication.basic.service;

import br.com.minsait.auth.authentication.basic.vo.request.AuthToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

/**
 * The JwtTokenBasicAuthenticationService class is responsible for generating JWT tokens for basic authentication.
 * <p>
 * It uses a JwtEncoder to encode the token with the necessary claims and expiration time.
 *
 * @author Leonardo Bernardino
 */
@Service
public class JwtTokenBasicAuthenticationService {
    private final JwtEncoder jwtEncoder;

    /**
     * Constructs a JwtTokenBasicAuthenticationService with the given JwtEncoder.
     *
     * @param jwtEncoder The JwtEncoder to be used for token encoding.
     */
    public JwtTokenBasicAuthenticationService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    /**
     * Generates a JWT token for the given authentication.
     *
     * @param authentication The authentication object representing the user.
     * @return An AuthToken object containing the generated token and its expiration time.
     */
    public AuthToken generateToken(Authentication authentication) {
        Instant now = Instant.now();
        Instant expiration = now.plus(1, ChronoUnit.HOURS);
        String scope = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder().issuer("self").issuedAt(now).expiresAt(expiration).subject(authentication.getName())
                .claim("scope", scope).build();
        return new AuthToken(this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue(), expiration);


    }


}
