package com.prajwol.userservice;

import at.favre.lib.idmask.Config;
import at.favre.lib.idmask.IdMask;
import at.favre.lib.idmask.IdMasks;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class IdObfuscationService {
    @Value("${key.bytes}")
    private String keyString;

    public byte[] getKey() {
        String[] byteValues = keyString.split(",");
        byte[] key = new byte[byteValues.length];
        for (int i = 0; i < byteValues.length; i++) {
            key[i] = Byte.parseByte(byteValues[i].trim());
        }
        return key;
    }

    public IdMask<Long> idMask() {
        return IdMasks.forLongIds(Config.builder(getKey()).build());
    }
}
