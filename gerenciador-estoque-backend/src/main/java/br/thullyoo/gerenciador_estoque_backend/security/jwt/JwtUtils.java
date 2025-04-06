package br.thullyoo.gerenciador_estoque_backend.security.jwt;

import br.thullyoo.gerenciador_estoque_backend.security.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.time.expiration}")
    private int jwtTimeExpiration;

    @Value("${jwt.private.key}")
    private String jwtPrivateKey;

    @Value("${jwt.public.key}")
    private String jwtPublicKey;

    public String generateTokenByUserDetails(UserDetailsImpl user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtTimeExpiration * 1000L))
                .signWith(getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();
    }

    private RSAPrivateKey getPrivateKey() {
        try {
            String privateKeyPEM = jwtPrivateKey
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] keyBytes = Base64.getDecoder().decode(privateKeyPEM);

            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load private key", e);
        }
    }

    private RSAPublicKey getPublicKey() {
        try {
            String publicKeyPEM = jwtPublicKey
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] keyBytes = Base64.getDecoder().decode(publicKeyPEM);

            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load public key", e);
        }
    }

    public boolean validateJwt(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getPublicKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            System.out.println("Error on validate token: " + e.getMessage());
            return false;
        }
    }

    public String getUsernameToken(String jwt) {
        return Jwts.parser()
                .verifyWith(getPublicKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();
    }
}