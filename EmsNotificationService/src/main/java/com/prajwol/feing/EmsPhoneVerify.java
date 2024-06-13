package com.prajwol.feing;

import com.prajwol.dto.EmsPhoneVerifyDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "emsPhoneVerify", url = "https://www.ipqualityscore.com/api/json/phone/OULh19bYEmKPk0xCQQwcrdKqrRDaMAz7")
public interface EmsPhoneVerify {
    @GetMapping("/{phone}")
     EmsPhoneVerifyDto phoneValidationResult( @PathVariable("phone") String phone,
                                                   @RequestParam("country[]") List<String> countries);
}
