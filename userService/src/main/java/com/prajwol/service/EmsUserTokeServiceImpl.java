package com.prajwol.service;

import com.prajwol.entity.EmsUserToken;
import com.prajwol.exception.EmsCustomException;
import com.prajwol.repository.EmsUserTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;

@Service
public class EmsUserTokeServiceImpl implements EmsUserTokenService {
    private final EmsUserTokenRepo emsUserTokenRepo;

    @Autowired
    public EmsUserTokeServiceImpl(EmsUserTokenRepo emsUserTokenRepo) {
        this.emsUserTokenRepo = emsUserTokenRepo;
    }


    @Override
    public Optional<EmsUserToken> getByToken(String token) {
        return emsUserTokenRepo.findByToken(token);
    }

    @Override
    public EmsUserToken getByTokenAndUserId(String token, String userId) throws EmsCustomException {
        return emsUserTokenRepo.findByTokenAndUserId(token, userId).orElseThrow(() -> new EmsCustomException("Token not found", "404"));
    }

    @Override
    public boolean checkExpiration(EmsUserToken token) throws EmsCustomException {
//        Optional<EmsUserToken> optionalToken = emsUserTokenRepo.findByToken(token);
//        if (optionalToken.isEmpty()) {
//            throw new EmsCustomException("Token not found", "TOKEN_NOT_FOUND");
//        }
//        EmsUserToken emsUserToken = optionalToken.get();
//        if (Instant.now().isAfter(emsUserToken.getExpiresAt())) {
//            throw new EmsCustomException("Token has expired", "TOKEN_EXPIRED");
//        }
//        return false;
//        EmsUserToken emsUserToken = emsUserTokenRepo.findByToken(token)
//                .orElseThrow(() -> new EmsCustomException("Token not found", "TOKEN_NOT_FOUND"));
        return Instant.now().isAfter(token.getExpiresAt());
    }

    @Override
    public void deleteToken(String token) throws EmsCustomException {
        Optional<EmsUserToken> optionalToken = emsUserTokenRepo.findByToken(token);
        if (optionalToken.isEmpty()) {
            throw new EmsCustomException("Token not found", "TOKEN_NOT_FOUND");
        }
        emsUserTokenRepo.delete(optionalToken.get());
    }

    @Override
    public EmsUserToken saveToken(EmsUserToken token) {
        return emsUserTokenRepo.save(token);
    }

    @Override
    public EmsUserToken generateToken(String userId) {
        String token = generateRandomToken();
        Instant expiresAt = Instant.now().plus(30, ChronoUnit.MINUTES);
        EmsUserToken emsUserToken = EmsUserToken.builder()
                .token(token)
                .expiresAt(expiresAt)
                .userId(userId)
                .build();
        return emsUserTokenRepo.save(emsUserToken);
    }

    @Override
    public void deleteByTokenId(Long tokenId) {
        emsUserTokenRepo.deleteById(tokenId);
    }

    public String generateRandomToken() {
        Random random = new Random();
        int token = 100000 + random.nextInt(900000); // 100000 to 999999
        return String.valueOf(token);
    }
}
