package com.example.shoppinglist.service;

import com.example.shoppinglist.entity.VerificationToken;
import com.example.shoppinglist.exception.TokenNotFoundException;
import com.example.shoppinglist.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class VerificationTokenService {

    private VerificationTokenRepository verificationTokenRepository;

    public void saveToken(VerificationToken token) {
        verificationTokenRepository.save(token);
    }

    public VerificationToken getToken(String tokenStr) {
        return verificationTokenRepository.findByToken(tokenStr).orElseThrow(
            () -> new TokenNotFoundException("This token was not found.")
        );
    }

    public void setConfirmedAt(String token) {
        verificationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }
}
