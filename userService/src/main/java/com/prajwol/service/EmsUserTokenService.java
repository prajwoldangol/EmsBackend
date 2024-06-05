package com.prajwol.service;

import com.prajwol.entity.EmsUserToken;
import com.prajwol.exception.EmsCustomException;

import java.util.Optional;

public interface EmsUserTokenService {
    public Optional<EmsUserToken> getByToken(String token);
    public EmsUserToken getByTokenAndUserId(String token, String userId) throws EmsCustomException;
    public boolean checkExpiration(EmsUserToken token) throws EmsCustomException;
    public void deleteToken(String token) throws EmsCustomException;
    public EmsUserToken saveToken(EmsUserToken token);
    public EmsUserToken generateToken(String userId);
    public void deleteByTokenId(Long tokenId);
}
