package br.com.minsait.auth.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OAuth2TokenVO {
    String token;
    int expiredToken;
}
