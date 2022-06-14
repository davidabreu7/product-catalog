package com.devlab.prodcatalog.backend.config;

import com.google.common.net.HttpHeaders;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@ConfigurationProperties(prefix = "application.jwt")
@EnableConfigurationProperties(JwtConfig.class)
@Component
public class JwtConfig {

    private String secretKey;
    private String tokenPrefix;
    private String tokenExpirationWeeks;

    public JwtConfig() {
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    public Integer getTokenExpirationWeeks() {
        return Integer.valueOf(tokenExpirationWeeks);
    }

    public void setTokenExpirationWeeks(String tokenExpirationWeeks) {
        this.tokenExpirationWeeks = tokenExpirationWeeks;
    }

    @Bean
    public SecretKey getSecretKeyForSigning() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    @Bean
    public String getAuthorizationHeader() {
        return HttpHeaders.AUTHORIZATION;
    }
}
