package br.com.minsait.auth.authentication.basic.api;

import br.com.minsait.auth.authentication.basic.service.JwtTokenBasicAuthenticationService;
import br.com.minsait.auth.authentication.basic.vo.request.AuthToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The AuthBasicController class represents the RESTful API for basic authentication.
 * <p>
 * It handles token generation and retrieval of the principal user.
 * @author Leonardo Bernardino
 */
@RestController
public class AuthBasicController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthBasicController.class);
    private final JwtTokenBasicAuthenticationService tokenService;

    public AuthBasicController(JwtTokenBasicAuthenticationService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/token")
    public AuthToken token(Authentication authentication) {
        LOG.debug("Token request for user '{}'", authentication.getPrincipal());
        return tokenService.generateToken(authentication);
    }


    @GetMapping("/principal")
    public String getPrincipal(Authentication authentication) {
        if (authentication instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
            String cpf = jwtAuthenticationToken.getToken().getClaim("sub");
            return cpf;
        }
        throw new RuntimeException("Not Permited");
    }
}
