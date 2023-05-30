package br.com.minsait.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.reactive.ReactiveManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
@ConfigurationPropertiesScan("br.com.minsait.auth")
@SpringBootApplication(exclude = {ReactiveSecurityAutoConfiguration.class, ReactiveManagementWebSecurityAutoConfiguration.class })
public class AuthApplication {


	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

}
