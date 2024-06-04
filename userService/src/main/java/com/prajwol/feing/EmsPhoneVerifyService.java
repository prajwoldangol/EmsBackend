//package com.prajwol.feing;
//
//import com.prajwol.dto.EmsPhoneVerifyDto;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class EmsPhoneVerifyService {
//
//    private final EmsPhoneVerify emsPhoneVerify;
//
//    @Autowired
//    public EmsPhoneVerifyService(EmsPhoneVerify emsPhoneVerify) {
//        this.emsPhoneVerify = emsPhoneVerify;
//    }
//
//    public EmsPhoneVerifyDto verifyPhone(String phone, List<String> countries) {
//        return emsPhoneVerify.phoneValidationResult(phone, countries);
//    }
//}
