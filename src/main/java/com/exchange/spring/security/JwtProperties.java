package com.exchange.spring.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {
    
    private String secretKey;

    // validity in milliseconds
    private long validityInMs = 1000L * 60 * 60 * 24 * 360; // 360 days

}
