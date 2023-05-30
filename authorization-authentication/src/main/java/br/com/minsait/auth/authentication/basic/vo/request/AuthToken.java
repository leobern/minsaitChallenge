package br.com.minsait.auth.authentication.basic.vo.request;

import lombok.Getter;

import java.time.Instant;

/**
 * The AuthToken class represents an authentication token.
 * <p>
 * It contains the token value and its expiration time.
 *
 * @author Leonardo bernardino
 */
@Getter
public class AuthToken {

    private String token;
    private Instant expirationTime;

    /**
     * Constructs an AuthToken with the given token value and expiration time.
     *
     * @param tokenValue The value of the authentication token.
     * @param expiration The expiration time of the token.
     */
    public AuthToken(String tokenValue, Instant expiration) {
        this.token = tokenValue;
        this.expirationTime = expiration;
    }
}
