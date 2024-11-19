package com.airbnb.bnb.service;

import com.airbnb.bnb.entity.AppUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.io.UnsupportedEncodingException;

@Service
public class JWTService {
    @Value("${jwt.algorithmKey}")
    private String algorithmKey;


    @Value("${jwt.issuer}")

    private String issuer;

    @Value("${jwt.expiry.duration}")

    private String expiryTime;


    private Algorithm algorithm;




    @PostConstruct
    private void postConstruct() throws UnsupportedEncodingException {


//        System.out.println(algorithmKey);
//        System.out.println(issuer);
//        System.out.println(expiryTime);
//


        algorithm.HMAC256(algorithmKey);

    }

    public String generateToken(AppUser user){

       return JWT.create()
                .withClaim("username",user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+expiryTime))
                .withIssuer(issuer)
                .sign(algorithm);



    }
}
