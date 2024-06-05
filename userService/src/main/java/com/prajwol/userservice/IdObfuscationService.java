package com.prajwol.userservice;

import at.favre.lib.idmask.Config;
import at.favre.lib.idmask.IdMask;
import at.favre.lib.idmask.IdMasks;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class IdObfuscationService {
//    @Value("${key.bytes}")
    private String keyStrings = "2,-1,-100,39,88,-56,96,75,2,85,86,26,28,-100,8,84";

    public byte[] getKey() {
        String[] byteValues = keyStrings.split(",");
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
