package br.com.minsait.auth.authentication.basic.jwt.properties;//package br.com.minsait.auth.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
/**

 The RsaKeyProperties class represents the RSA key properties used for JWT authentication.
 It holds the public and private keys for RSA encryption and decryption.
 @author Leonardo Bernardino
 */
@ConfigurationProperties(prefix= "rsa")
public record RsaKeyProperties(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
}
