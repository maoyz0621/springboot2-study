package com.myz.springboot2.config;

/**
 * @author maoyz
 */
public class JwtConfigProperties {

    private String header = "Authorization";

    private String tokenPrefix;

    private String secret;

    /**
     * 失效时间
     */
    private Long expirationHours = 2L;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getExpirationHours() {
        return expirationHours;
    }

    public void setExpirationHours(Long expirationHours) {
        this.expirationHours = expirationHours;
    }
}
