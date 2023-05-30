package br.com.minsait.auth.config;//package br.com.minsait.auth.config;

import br.com.minsait.auth.authentication.basic.jwt.properties.RsaKeyProperties;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

/**
 * The SecurityConfig class is responsible for configuring the security settings
 * <p>
 * for the authentication and authorization process.
 *
 * @author Leonardo Bernardino
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final RsaKeyProperties rsaKeys;
    private static final String BASIC_REALM_NAME = new String("Authorization");


    /**
     * Constructs a SecurityConfig with the RSA key properties.
     *
     * @param rsaKeys The RSA key properties containing the public and private keys.
     */
    public SecurityConfig(RsaKeyProperties rsaKeys) {
        this.rsaKeys = rsaKeys;

    }

    /**
     * Configures the user details manager for in-memory user authentication.
     *
     * @return The InMemoryUserDetailsManager bean.
     */
    @Bean
    public InMemoryUserDetailsManager users() {
        return new InMemoryUserDetailsManager(User.withUsername("Leonardo.Bernardino")
                .password(encoder().encode("123456"))
                .authorities("read").build());
    }

    /**
     * Configures the password encoder.
     *
     * @return The PasswordEncoder bean.
     */
    @Bean
    public PasswordEncoder encoder() {

        return new BCryptPasswordEncoder(11);
    }

    /**
     * Configures the security filter chain.
     *
     * @param http The HttpSecurity object.
     * @return The SecurityFilterChain bean.
     * @throws Exception If an error occurs during the configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csfr -> csfr.disable())
                .authorizeRequests(auth -> auth.antMatchers("/webhook").permitAll().anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())


                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .build();
    }

    /**
     * Creates a BasicAuthenticationEntryPoint with the realm name.
     *
     * @return The BasicAuthenticationEntryPoint bean.
     */
    private BasicAuthenticationEntryPoint basicAuthenticationEntryPoint() {
        BasicAuthenticationEntryPoint basicAuthenticationEntryPoint = new BasicAuthenticationEntryPoint();
        basicAuthenticationEntryPoint.setRealmName(BASIC_REALM_NAME);
        return basicAuthenticationEntryPoint;
    }

    /**
     * Creates a JwtDecoder using the RSA public key.
     *
     * @return The JwtDecoder bean.
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
    }

    /**
     * Creates a JwtEncoder using the RSA key pair.
     *
     * @return The JwtEncoder bean.
     */
    @Bean
    public JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

}