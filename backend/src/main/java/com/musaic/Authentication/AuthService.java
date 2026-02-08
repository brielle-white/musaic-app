package com.musaic.Authentication;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class AuthService {
    private final String issuer;
    private final String audience;
    private final DefaultJWTProcessor<SecurityContext> jwtProcessor;

    public AuthService(String issuer, String audience) {
        this.issuer = issuer;
        this.audience = audience;
        try {
            URL jwksUrl = new URL(issuer + ".well-known/jwks.json");
            JWKSource<SecurityContext> keySource = new RemoteJWKSet<>(jwksUrl);

            JWSKeySelector<SecurityContext> keySelector = new JWSVerificationKeySelector<>(JWSAlgorithm.RS256,
                    keySource);

            this.jwtProcessor = new DefaultJWTProcessor<>();
            this.jwtProcessor.setJWSKeySelector(keySelector);

        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Bad issuer URL: " + issuer, e);
        }
    }

    /** Returns the subject (Auth0 user id) if valid; throws if invalid. */
    public String verifyAndGetSub(String rawToken)
            throws ParseException, BadJOSEException, JOSEException {

        JWTClaimsSet claims = jwtProcessor.process(rawToken, null);

        // 1) issuer
        if (claims.getIssuer() == null || !claims.getIssuer().equals(issuer)) {
            throw new BadJOSEException("Invalid issuer");
        }

        // 2) audience
        List<String> aud = claims.getAudience();
        if (aud == null || !aud.contains(audience)) {
            throw new BadJOSEException("Invalid audience");
        }

        // 3) expiration
        Date exp = claims.getExpirationTime();
        if (exp == null || new Date().after(exp)) {
            throw new BadJOSEException("Token expired");
        }

        // 4) subject
        String sub = claims.getSubject();
        if (sub == null || sub.isBlank()) {
            throw new BadJOSEException("Missing sub");
        }

        return sub;
    }

}
